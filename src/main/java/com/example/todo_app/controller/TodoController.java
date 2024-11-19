package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public Todo getTodoById(@PathVariable UUID id) {
        return todoService.getTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    @PostMapping
    public Todo createTodo(@RequestBody String text) {
        // Убираем лишние кавычки и пробелы
        String cleanedText = text.trim().replaceAll("^\"|\"$", ""); // Удаляем кавычки в начале и конце
        return todoService.createTodo(cleanedText);
    }


    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable UUID id, @RequestBody String text) {
        return todoService.updateTodo(id, text);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable UUID id) {
        todoService.deleteTodo(id);
    }
}
