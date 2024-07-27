use actix_web::{delete, get, post, put, web, Responder, Result};

use crate::database::lib::establish_connection;

use super::{
    models::{InsertTodo, UpdateTodo},
    service,
};

#[get("")]
pub async fn get_all_todo() -> Result<impl Responder> {
    let mut conn = establish_connection();
    let result = service::get_todos(&mut conn);

    Ok(web::Json(result))
}

#[post("")]
pub async fn post_todo(body: web::Json<InsertTodo>) -> Result<impl Responder> {
    let mut conn = establish_connection();

    let result = service::create_todo(&mut conn, body.into_inner());

    Ok(web::Json(result))
}

#[put("{id}")]
pub async fn put_todo(path: web::Path<i32>, body: web::Json<UpdateTodo>) -> Result<impl Responder> {
    let mut conn = establish_connection();

    let id = path.into_inner();
    let result = service::update_todo(&mut conn, body.into_inner(), id);

    Ok(web::Json(result))
}

#[delete("{id}")]
pub async fn delete_todo(path: web::Path<i32>) -> Result<impl Responder> {
    let mut conn = establish_connection();

    let id = path.into_inner();
    service::delete_todo(&mut conn, id);

    Ok(web::Json("deleted"))
}

#[get("{id}")]
pub async fn get_todo_by_id(path: web::Path<i32>) -> Result<impl Responder> {
    let mut conn = establish_connection();

    let id = path.into_inner();
    let result = service::get_todo_by_id(&mut conn, id);

    Ok(web::Json(result))
}
