package com.multek.java.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multek.java.back.models.Todo;

/**
 *
 * @author wgroc
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
