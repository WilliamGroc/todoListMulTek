package todo

type CreateTodoRequest struct {
	Title       string `json:"title" validate:"required"`
	Description string `json:"description" validate:"required"`
	Date        string `json:"date" validate:"required"`
	Checked     bool   `json:"checked"`
}

type UpdateTodoRequest struct {
	Title       string `json:"title" validate:"required"`
	Description string `json:"description" validate:"required"`
	Checked     bool   `json:"checked"`
}

type NewTodoResponse struct {
	ID uint `json:"id"`
}

type TodoResponse struct {
	ID          uint   `json:"id"`
	Title       string `json:"title"`
	Description string `json:"description"`
	Date        string `json:"date"`
	Checked     bool   `json:"checked"`
}
