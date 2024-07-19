package server

import (
	"golang_back/server/api/todo"
	"golang_back/server/models"
	"log"
	"net/http"
	"os"
	"time"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
	"github.com/go-chi/render"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

type App struct {
	DB     *gorm.DB
	Router *chi.Mux
}

type AppInterface interface {
	Run()
}

func (a *App) Run() {
	// Database connection
	newLogger := logger.New(
		log.New(os.Stdout, "\r\n", log.LstdFlags), // io writer
		logger.Config{
			SlowThreshold:             time.Second, // Slow SQL threshold
			LogLevel:                  logger.Info, // Log level
			IgnoreRecordNotFoundError: true,        // Ignore ErrRecordNotFound error for logger
			ParameterizedQueries:      true,        // Don't include params in the SQL log
			Colorful:                  false,       // Disable color
		},
	)

	uri := os.Getenv("DATABASE_URI")
	db, err := gorm.Open(postgres.Open(uri), &gorm.Config{
		Logger: newLogger,
	})

	if err != nil {
		panic("failed to connect database")
	}

	a.DB = db

	// Migrate the schema
	db.AutoMigrate(&models.Todo{})

	// Router
	a.Router = chi.NewRouter()

	a.Router.Use(middleware.Logger)
	a.Router.Use(middleware.Recoverer)
	a.Router.Use(render.SetContentType(render.ContentTypeJSON))

	a.Router.Mount("/todo", todo.NewTodoRoutes(a.DB).Router)

	log.Println("Server running on port 8080")
	err = http.ListenAndServe(":8080", a.Router)

	if err != nil {
		log.Fatal(err)
	}
}

func NewApp() *App {
	return &App{}
}
