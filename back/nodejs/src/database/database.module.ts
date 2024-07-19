import { Module } from "@nestjs/common";
import { DatabaseService } from ".";
import { ConfigModule } from "@nestjs/config";

@Module({
  imports: [
    ConfigModule
  ],
  providers: [DatabaseService],
  exports: [DatabaseService], // As we want to share an instance of the 'DatabaseService' between several other modules, we need to export the 'DatabaseService' provider.
})
export class DatabaseModule{}