use diesel::prelude::*;
use serde::{Deserialize, Serialize};
use validator::Validate;

#[derive(Debug, Queryable, Selectable, Serialize, Identifiable)]
#[diesel(table_name = crate::database::schema::todo)]
#[diesel(check_for_backend(diesel::pg::Pg))]
pub struct Todo {
    pub id: i32,
    pub title: String,
    pub description: String,
    pub checked: bool,
    pub created_at: chrono::NaiveDateTime,
    pub updated_at: Option<chrono::NaiveDateTime>,
}

#[derive(Insertable, Debug, Deserialize, Validate)]
#[diesel(table_name = crate::database::schema::todo)]
pub struct InsertTodo {
    pub title: String,
    pub description: String,
}

#[derive(AsChangeset, Debug, Deserialize, Default, Validate)]
#[diesel(table_name = crate::database::schema::todo)]
pub struct UpdateTodo {
    pub title: Option<String>,
    pub description: Option<String>,
    pub checked: Option<bool>,
    pub updated_at: Option<chrono::NaiveDateTime>,
}
