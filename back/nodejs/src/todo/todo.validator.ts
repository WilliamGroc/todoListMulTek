import * as z from 'zod';

export const createTodoValidator = z.object({
  title: z.string(),
  description: z.string(),
});

export const updateTodoValidator = z.object({
  title: z.string().optional(),
  description: z.string().optional(),
  checked: z.boolean().optional()
});