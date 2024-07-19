package todo

import (
	"github.com/go-chi/chi/v5"
	"gorm.io/gorm"
)

type TodoRoutes struct {
	Router *chi.Mux
	DB     *gorm.DB
}

type TodoRoutesInterface interface {
	SetupRoutes()

	GetAllTodos()
	CreateTodo()
	GetTodo()
	UpdateTodo()
	DeleteTodo()
}

func NewTodoRoutes(db *gorm.DB) *TodoRoutes {
	router := chi.NewRouter()

	routes := &TodoRoutes{
		Router: router,
		DB:     db,
	}

	routes.SetupRoutes()

	return routes
}

func (ur *TodoRoutes) SetupRoutes() {

	ur.Router.Get("/", ur.GetAllTodos)
	ur.Router.Post("/", ur.CreateTodo)
	ur.Router.Get("/{id}", ur.GetTodo)
	ur.Router.Put("/{id}", ur.UpdateTodo)
	ur.Router.Delete("/{id}", ur.DeleteTodo)
}
