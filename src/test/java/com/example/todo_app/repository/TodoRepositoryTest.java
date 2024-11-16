package com.example.todo_app.repository;

import com.example.todo_app.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testSaveAndFind() {
        Todo todo = new Todo("Integration Test Todo");
        todoRepository.save(todo);

        List<Todo> todos = todoRepository.findAll();
        assertFalse(todos.isEmpty());
        assertEquals("Integration Test Todo", todos.get(0).getText());
    }
}
