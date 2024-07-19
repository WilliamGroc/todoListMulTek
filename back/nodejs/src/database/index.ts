


import { Injectable, OnModuleInit } from '@nestjs/common';
import { drizzle, PostgresJsDatabase } from 'drizzle-orm/postgres-js';

import * as schema from './schema';
import * as postgres from 'postgres';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class DatabaseService implements OnModuleInit {
  private db: PostgresJsDatabase<typeof schema>;

  constructor(
    private readonly configService: ConfigService
  ) { }

  async onModuleInit() {
    try {
      const sql = postgres( {
        host: this.configService.get('DB_HOST'),            // Postgres ip address[s] or domain name[s]
        port: 5432,          // Postgres server port[s]
        database: this.configService.get('DB_NAME'),            // Name of database to connect to
        username: this.configService.get('DB_USER'),            // Username of database user
        password: this.configService.get('DB_PASSWORD'),            // Password of database user
        max: 1,
        ssl: false,         // True to use SSL, false to not use SSL
      })

      this.db = drizzle(sql, {
        schema,
        logger: true,
      });

      console.log('Database connected successfully');
    } catch (error) {
      console.error('Failed to connect to the database', error);
      throw error;
    }
  }

  get client() {
    return this.db;
  }
}