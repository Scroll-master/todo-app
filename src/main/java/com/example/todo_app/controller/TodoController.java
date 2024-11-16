package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodo(todo.getText());
    }


    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody String text) {
        return todoService.updateTodo(id, text);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}
