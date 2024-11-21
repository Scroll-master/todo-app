package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import com.example.todo_app.dto.TodoCreateRequest;
import com.example.todo_app.dto.TodoResponse;
import com.example.todo_app.dto.TodoUpdateRequest;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public TodoResponse getTodo(@PathVariable UUID id) {
        return mapToResponse(todoService.getTodo(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse create(@RequestBody TodoCreateRequest request) {
        // Передаём объект в сервис
        Todo createdTodo = todoService.create(request.getText());
        // Преобразуем созданный объект в TodoResponse
        return mapToResponse(createdTodo);
    }


    @PatchMapping("/{id}")
    public TodoResponse update(@PathVariable UUID id, @RequestBody TodoUpdateRequest request) {
        Todo updatedTodo = todoService.update(id, request.getText());
        return mapToResponse(updatedTodo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        todoService.delete(id);
    }

    private TodoResponse mapToResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId().toString())
                .text(todo.getText())
                .createdAt(todo.getCreatedAt().toString())
                .updatedAt(todo.getUpdatedAt().toString())
                .build();
    }


}
