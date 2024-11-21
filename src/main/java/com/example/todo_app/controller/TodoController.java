package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;

import jakarta.validation.Valid;

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
    public TodoResponse create(@Valid @RequestBody TodoCreateRequest request) {
        // Pass the object to the service
        Todo createdTodo = todoService.create(request);
        // Convert the created object to TodoResponse
        return mapToResponse(createdTodo);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID id, @RequestBody TodoUpdateRequest request) {
        todoService.update(id, request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        todoService.delete(id);
    }

    private TodoResponse mapToResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId().toString())
                .title(todo.getTitle()) 
                .description(todo.getDescription()) 
                .completed(todo.isCompleted()) 
                .createdAt(todo.getCreatedAt().toString())
                .updatedAt(todo.getUpdatedAt().toString())
                .build();
    }


}
