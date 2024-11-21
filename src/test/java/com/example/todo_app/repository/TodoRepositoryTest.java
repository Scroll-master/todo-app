package com.example.todo_app.repository;

import com.example.todo_app.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {

    // Constants for the test
    private static final String TEST_TEXT = "Integration Test Todo";

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testSaveAndFind() {
        Todo todo = new Todo(TEST_TEXT); // Use a constant
        todoRepository.save(todo);

        List<Todo> todos = todoRepository.findAll();
        assertFalse(todos.isEmpty());
        assertEquals(TEST_TEXT, todos.get(0).getText()); // Check text using constant
    }
}
