package com.example.todo_app.service;

import com.example.todo_app.dto.TodoCreateRequest;
import com.example.todo_app.dto.TodoUpdateRequest;
import com.example.todo_app.exception.TodoNotFoundException;
import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Todo getTodo(UUID id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }


    public Todo create(TodoCreateRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle().trim()) // Use title from DTO
                .description(request.getDescription().trim()) // Use description from DTO
                .completed(false) // New tasks are not completed by default
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        return todoRepository.save(todo);
    }
    
    public Todo update(UUID id, TodoUpdateRequest request) {
        return todoRepository.findById(id)
                .map(existing -> {
                    existing.applyUpdate(
                            request.getTitle().trim(), 
                            request.getDescription().trim(), 
                            request.isCompleted() 
                    );
                    return todoRepository.save(existing);
                })
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }
    


    public void delete(UUID id) {
        Todo todo = getTodo(id);
        todoRepository.delete(todo);
    }
}

