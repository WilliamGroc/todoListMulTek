use actix_web::{middleware::Logger, App, HttpServer};
use env_logger::Env;

mod database;
mod todo;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    env_logger::init_from_env(Env::default().default_filter_or("info"));

    HttpServer::new(move || {
        App::new()
            .wrap(Logger::default())
            .service(todo::router::routes())
    })
    .bind(("127.0.0.1", 8080))?
    .run()
    .await
}
