use actix_web::{web, Scope};

use super::controller::{delete_todo, get_all_todo, get_todo_by_id, post_todo, put_todo};

pub fn routes() -> Scope {
    web::scope("/todo")
        .service(get_all_todo)
        .service(get_todo_by_id)
        .service(post_todo)
        .service(put_todo)
        .service(delete_todo)
}
