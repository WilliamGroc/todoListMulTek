use crate::todo::models::{InsertTodo, Todo, UpdateTodo};
use diesel::query_dsl::methods::FilterDsl;
use diesel::{ExpressionMethods, PgConnection, RunQueryDsl, SelectableHelper};

use super::models;

pub fn create_todo(conn: &mut PgConnection, data: InsertTodo) -> models::Todo {
    use crate::database::schema::*;

    diesel::insert_into(todo::table)
        .values(&data)
        .returning(Todo::as_returning())
        .get_result(conn)
        .expect("Error saving new todo")
}

pub fn get_todos(conn: &mut PgConnection) -> Vec<models::Todo> {
    use crate::database::schema::todo::dsl::*;

    todo.load::<Todo>(conn).expect("Error loading todos")
}

pub fn get_todo_by_id(conn: &mut PgConnection, id: i32) -> models::Todo {
    use crate::database::schema::*;

    todo::dsl::todo
        .filter(todo::id.eq(id))
        .first(conn)
        .expect("Error loading todo by id")
}

pub fn update_todo(conn: &mut PgConnection, data: UpdateTodo, id: i32) -> models::Todo {
    use crate::database::schema::*;

    let updated_data = UpdateTodo {
        updated_at: Some(chrono::Local::now().naive_local()),
        ..data
    };

    diesel::update(todo::table)
        .filter(todo::id.eq(id))
        .set(updated_data)
        .returning(Todo::as_returning())
        .get_result(conn)
        .expect("Error saving new todo")
}

pub fn delete_todo(conn: &mut PgConnection, id: i32) -> usize {
    use crate::database::schema::*;

    diesel::delete(todo::table)
        .filter(todo::id.eq(id))
        .execute(conn)
        .expect("Error deleting todo")
}
