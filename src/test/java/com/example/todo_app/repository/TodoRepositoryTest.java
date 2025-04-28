package com.example.todo_app.repository;

import com.example.todo_app.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {

    // Constants for the test
    private static final String TEST_TITLE = "Integration Test Title";
    private static final String TEST_DESCRIPTION = "Integration Test Description";

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testSaveAndFind() {
        // Create a task using constants
        Todo todo = Todo.builder()
                .title(TEST_TITLE) // Use a constant
                .description(TEST_DESCRIPTION) // Use a constant
                .completed(false) // Task is not executed by default
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // Save the task
        todoRepository.save(todo);

        // Get all tasks from the repository
        List<Todo> todos = todoRepository.findAll();

        // Check that tasks are found
        assertFalse(todos.isEmpty());
        assertEquals(TEST_TITLE, todos.get(0).getTitle()); 
        assertEquals(TEST_DESCRIPTION, todos.get(0).getDescription()); 
        assertFalse(todos.get(0).isCompleted()); 
    }

}
