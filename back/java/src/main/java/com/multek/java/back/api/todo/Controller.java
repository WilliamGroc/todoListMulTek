package com.multek.java.back.api.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multek.java.back.api.todo.dto.InsertDto;
import com.multek.java.back.api.todo.dto.UpdateDto;
import com.multek.java.back.models.Todo;
import com.multek.java.back.repository.TodoRepository;

/**
 *
 * @author wgroc
 */
@RestController
@RequestMapping("/todo")
public class Controller {

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("")
    public ResponseEntity<List<Todo>> getAll() {
        List<Todo> todos = todoRepository.findAll();

        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Todo> getById(@PathVariable("id") long id) {
        Todo todo = todoRepository.findById(id).get();
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Todo> create(@RequestBody InsertDto entity) {
        Todo insertedData = new Todo();
        insertedData.setTitle(entity.getTitle());
        insertedData.setDescription(entity.getDescription());

        Todo savedData = todoRepository.save(insertedData);
        return new ResponseEntity<>(savedData, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Todo> put(@PathVariable Long id, @RequestBody UpdateDto entity) {
        Todo todo = todoRepository.findById(id).get();

        if (entity.getTitle() != null) {
            todo.setTitle(entity.getTitle());
        }

        if (entity.getDescription() != null) {
            todo.setDescription(entity.getDescription());
        }

        todo.setChecked(entity.isChecked());

        todo = todoRepository.save(todo);

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
