package todo

import (
	"golang_back/server/api"
	"golang_back/server/models"
	"net/http"
	"strconv"
	"time"

	"github.com/go-chi/chi/v5"
)

func (ur *TodoRoutes) GetAllTodos(w http.ResponseWriter, r *http.Request) {
	var todos []models.Todo

	ur.DB.Model(&models.Todo{}).Not("deleted_at IS NOT NULL").Find(&todos)

	var response = []TodoResponse{}

	for _, todo := range todos {
		response = append(response, TodoResponse{
			ID:          todo.ID,
			Title:       todo.Title,
			Description: todo.Description,
			Date:        todo.CreatedAt.String(),
		})
	}

	api.EncodeBody(w, response)
}

func (ur *TodoRoutes) CreateTodo(w http.ResponseWriter, r *http.Request) {
	var body CreateTodoRequest
	var err = api.DecodeBody(r, &body)

	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte(err.Error()))
		return
	}

	todo := models.Todo{
		Title:       body.Title,
		Description: body.Description,
	}

	ur.DB.Create(&todo)

	api.EncodeBody(w, NewTodoResponse{ID: todo.ID})
}

func (ur *TodoRoutes) GetTodo(w http.ResponseWriter, r *http.Request) {
	id := chi.URLParam(r, "id")

	var todo models.Todo

	ur.DB.Model(&models.Todo{}).First(&todo, id)

	if todo.ID == 0 {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte("Todo not found"))
		return
	}

	api.EncodeBody(w, TodoResponse{
		ID:          todo.ID,
		Title:       todo.Title,
		Description: todo.Description,
		Date:        todo.CreatedAt.String(),
	})
}

func (ur *TodoRoutes) UpdateTodo(w http.ResponseWriter, r *http.Request) {
	id := chi.URLParam(r, "id")
	todoId, _ := strconv.Atoi(id)

	var todo models.Todo
	ur.DB.Where(map[string]interface{}{"id": todoId}).First(&todo)

	if todo.ID == 0 {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte("Todo not found"))
		return
	}

	var body UpdateTodoRequest
	api.DecodeBody(r, &body)

	if body.Title != "" {
		todo.Title = body.Title
	}
	if body.Description != "" {
		todo.Description = body.Description
	}
	if body.Checked {
		todo.Checked = body.Checked
	}

	ur.DB.Save(&todo)

	api.EncodeBody(w, TodoResponse{
		ID:          todo.ID,
		Title:       todo.Title,
		Description: todo.Description,
		Date:        todo.CreatedAt.String(),
	})
}

func (ur *TodoRoutes) DeleteTodo(w http.ResponseWriter, r *http.Request) {
	id := chi.URLParam(r, "id")
	todoId, _ := strconv.Atoi(id)

	var todo models.Todo
	todo.ID = uint(todoId)

	ur.DB.First(&todo)

	if todo.ID == 0 {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte("Todo not found"))
		return
	}

	ur.DB.Model(&models.Todo{}).Where("id  = ?", todoId).Update("deleted_at", time.Now())

	w.WriteHeader(http.StatusOK)
	w.Write([]byte("Todo deleted"))
}
