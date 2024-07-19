import { Body, Controller, Delete, Get, Param, Patch, Post } from '@nestjs/common';
import { Todo } from 'src/database/schema';
import { TodoService } from './todo.service';
import { createTodoValidator, updateTodoValidator } from './todo.validator';

@Controller('todo')
export class TodoController {
  constructor(
    private readonly todoService: TodoService,
  ) { }

  @Get()
  async findAll() {
    return this.todoService.findAll();
  }

  @Get(':id')
  async findById(@Param('id') id: string) {
    return this.todoService.findById(Number(id));
  }

  @Post()
  async create(@Body() todo: Todo) {
    try {
      createTodoValidator.parse(todo)
      return this.todoService.create(todo);
    } catch (e) {
      console.log(e)
      return e
    }
  }

  @Patch(':id')
  async update(@Param('id') id: string, @Body() todo: Todo) {
    try {
      updateTodoValidator.parse(todo)
      return this.todoService.update(Number(id), todo);
    } catch (e) {
      console.log(e)
      return e
    }
  }

  @Delete(':id')
  async delete(@Param('id') id: string) {
    return this.todoService.delete(Number(id));
  }
}
