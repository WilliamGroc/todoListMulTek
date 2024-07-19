CREATE TABLE IF NOT EXISTS "todo" (
	"id" serial PRIMARY KEY NOT NULL,
	"title" varchar NOT NULL,
	"description" varchar,
	"checked" boolean,
	"created_at" timestamp DEFAULT now(),
	"updated_at" timestamp
);
