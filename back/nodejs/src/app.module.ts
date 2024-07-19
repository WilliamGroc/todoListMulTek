import { Module } from '@nestjs/common';
import { TodoModule } from './todo/todo.module';
import { DatabaseModule } from './database/database.module';
import { ConfigModule } from '@nestjs/config';

@Module({
  imports: [
    TodoModule,
    DatabaseModule,
    ConfigModule.forRoot()
  ],
})
export class AppModule { }
