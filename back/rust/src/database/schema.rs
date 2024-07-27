// @generated automatically by Diesel CLI.

diesel::table! {
  todo (id) {
      id -> Int4,
      title -> Varchar,
      description -> Text,
      checked -> Bool,
      created_at -> Timestamp,
      updated_at -> Nullable<Timestamp>,
  }
}