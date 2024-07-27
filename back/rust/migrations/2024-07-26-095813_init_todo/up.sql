-- Your SQL goes here
CREATE TABLE "todo"(
	"id" SERIAL NOT NULL PRIMARY KEY,
	"title" VARCHAR NOT NULL,
	"description" TEXT NOT NULL,
	"checked" BOOL NOT NULL DEFAULT false,
	"created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	"updated_at" TIMESTAMP
);

