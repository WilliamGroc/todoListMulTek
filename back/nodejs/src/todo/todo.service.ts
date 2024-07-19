import { Injectable } from '@nestjs/common';
import { DatabaseService } from 'src/database';
import { Todo, todo } from 'src/database/schema';
import { eq } from 'drizzle-orm/pg-core/expressions';


@Injectable()
export class TodoService {
  constructor(
    private readonly databaseService: DatabaseService
  ) { }

  async findAll() {
    return this.databaseService.client.select().from(todo);
  }

  async create(data: Omit<Todo, 'created_at' | 'updated_at' | 'checked'>) {
    return this.databaseService.client.insert(todo).values(data).returning()
  }

  async update(id: number, data: Omit<Todo, 'created_at' | 'updated_at'>) {
    const currentTodo = await this.findById(id);

    currentTodo.updated_at = new Date();

    if(data.title !== undefined)
      currentTodo.title = data.title;

    if(data.description !== undefined)
      currentTodo.description = data.description;

    if(data.checked !== undefined)
      currentTodo.checked = data.checked;

    return this.databaseService.client.update(todo).set(currentTodo).where(eq(todo.id, id)).returning();
  }

  async delete(id: number) {
    return this.databaseService.client.delete(todo).where(eq(todo.id, id));
  }

  async findById(id: number): Promise<Todo> {
    const result = await this.databaseService.client.select().from(todo).where(eq(todo.id, id));

    if (!result.length)
      throw new Error('Todo not found');

    return result[0];
  }

}
