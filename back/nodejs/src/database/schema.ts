import { pgTable, varchar, serial, boolean, date, integer, timestamp } from "drizzle-orm/pg-core";

export const todo = pgTable('todo', {
  id: serial("id").primaryKey(),
  title: varchar('title').notNull(),
  description: varchar('description'),
  checked: boolean('checked'),
  created_at: timestamp('created_at').defaultNow(),
  updated_at: timestamp('updated_at'),
});

export type Todo = typeof todo.$inferSelect;