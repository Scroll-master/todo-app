package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo createTodo(String text) {
        Todo todo = new Todo(text);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, String text) {
        return todoRepository.findById(id)
                .map(existingTodo -> {
                    existingTodo.setText(text);
                    return todoRepository.save(existingTodo);
                })
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
