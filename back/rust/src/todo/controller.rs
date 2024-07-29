use actix_web::{
    delete,
    error::{self, ErrorBadRequest},
    get, post, put, web, Responder, Result,
};
use validator::Validate;

use crate::database::lib::establish_connection;
use crate::models::todo::{InsertTodo, UpdateTodo};
use super::service;

#[get("")]
pub async fn get_all_todo() -> Result<impl Responder> {
    let mut conn = establish_connection();
    let result = service::get_todos(&mut conn);

    Ok(web::Json(result))
}

#[post("")]
pub async fn post_todo(body: web::Json<InsertTodo>) -> Result<impl Responder> {
    let mut conn = establish_connection();

    let is_valid = body.validate();

    match is_valid {
        Ok(_) => {
            let result = service::create_todo(&mut conn, body.into_inner());
            Ok(web::Json(result))
        }
        Err(e) => Err(ErrorBadRequest(error::ErrorBadRequest(e))),
    }
}

#[put("{id}")]
pub async fn put_todo(path: web::Path<i32>, body: web::Json<UpdateTodo>) -> Result<impl Responder> {
    let mut conn = establish_connection();

    let is_valid = body.validate();

    match is_valid {
        Ok(_) => {
            let id = path.into_inner();
            let result = service::update_todo(&mut conn, body.into_inner(), id);
            Ok(web::Json(result))
        }
        Err(e) => Err(ErrorBadRequest(error::ErrorBadRequest(e))),
    }
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
