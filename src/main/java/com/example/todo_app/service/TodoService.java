package com.example.todo_app.service;

import com.example.todo_app.exception.TodoNotFoundException;
import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Todo create(String text) {
        Todo todo = new Todo(text);
        return todoRepository.save(todo);
    }

    public Todo update(UUID id, String text) {
        Todo todo = getTodo(id);
        todo.applyUpdate(text);
        return todoRepository.save(todo);
    }

    public void delete(UUID id) {
        Todo todo = getTodo(id);
        todoRepository.delete(todo);
    }
}

