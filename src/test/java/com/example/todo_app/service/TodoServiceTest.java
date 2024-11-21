package com.example.todo_app.service;

import com.example.todo_app.dto.TodoCreateRequest;
import com.example.todo_app.dto.TodoUpdateRequest;
import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    // Constants for testing
    private static final UUID TEST_ID = UUID.randomUUID();
    private static final String TEST_TITLE = "Test Title";
    private static final String TEST_DESCRIPTION = "Test Description";
    private static final String NEW_TITLE = "New Title";
    private static final String NEW_DESCRIPTION = "New Description";


    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTodo() {
        // Test data
        TodoCreateRequest request = new TodoCreateRequest();
        request.setTitle(NEW_TITLE); // Use constant for title
        request.setDescription(NEW_DESCRIPTION); // Use constant for description

        Todo todo = Todo.builder()
                .id(TEST_ID)
                .title(request.getTitle()) // Use request data
                .description(request.getDescription()) // Use request data
                .completed(false) // New tasks are not completed by default
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // Mock the save operation
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Call the task creation method
        Todo createdTodo = todoService.create(request);

        // Check the results
        assertNotNull(createdTodo); // Make sure the task is created
        assertEquals(NEW_TITLE, createdTodo.getTitle()); // Title should match the request
        assertEquals(NEW_DESCRIPTION, createdTodo.getDescription()); // Description should match the request
        assertFalse(createdTodo.isCompleted()); // New tasks should not be completed by default
        assertNotNull(createdTodo.getCreatedAt()); // Check the creation date
        assertNotNull(createdTodo.getUpdatedAt()); // Check the update date
}



    @Test
    void testGetAllTodos() {
        // Mock repository response
        when(todoRepository.findAll()).thenReturn(List.of(
                Todo.builder()
                    .id(TEST_ID)
                    .title(TEST_TITLE) // Use constants
                    .description(TEST_DESCRIPTION) // Use constants
                    .completed(false) // Default completed status
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build(),
                Todo.builder()
                    .id(UUID.randomUUID()) // Different ID for the second todo
                    .title(NEW_TITLE) // Use constant for a new title
                    .description(NEW_DESCRIPTION) // Use constant for a new description
                    .completed(true) // This task is completed
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build()
        ));

        // Call the service method
        List<Todo> todos = todoService.getAll();

        // Assertions
        assertEquals(2, todos.size()); // Check the size of the list
        // Check the first todo
        assertEquals(TEST_TITLE, todos.get(0).getTitle());
        assertEquals(TEST_DESCRIPTION, todos.get(0).getDescription());
        assertFalse(todos.get(0).isCompleted());
        // Check the second todo
        assertEquals(NEW_TITLE, todos.get(1).getTitle());
        assertEquals(NEW_DESCRIPTION, todos.get(1).getDescription());
        assertTrue(todos.get(1).isCompleted());
    }


    @Test
    void testGetTodoById() {
        // Create a Todo object with a specific ID
        Todo todo = Todo.builder()
                .id(TEST_ID) // Use constant for ID
                .title(TEST_TITLE) // Use constant for title
                .description(TEST_DESCRIPTION) // Use constant for description
                .completed(false) // Default completed status
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // Mock repository response
        when(todoRepository.findById(TEST_ID)).thenReturn(Optional.of(todo));

        // Call the method
        Todo foundTodo = todoService.getTodo(TEST_ID); // Pass UUID directly
        // Assertions
        assertNotNull(foundTodo); // Check that the task was found
        assertEquals(TEST_TITLE, foundTodo.getTitle()); // Title should match
        assertEquals(TEST_DESCRIPTION, foundTodo.getDescription()); // Description should match
        assertFalse(foundTodo.isCompleted()); // Task should not be completed
        assertEquals(TEST_ID, foundTodo.getId()); // ID should match
    }


    @Test
    void testUpdateTodo() {
        // Create an existing Todo object
        Todo existingTodo = Todo.builder()
                .id(TEST_ID) // Use constant for ID
                .title(TEST_TITLE) // Use constant for title
                .description(TEST_DESCRIPTION) // Use constant for description
                .completed(false) // Default status
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // Create a TodoUpdateRequest object
        TodoUpdateRequest request = new TodoUpdateRequest();
        request.setTitle(NEW_TITLE); // Use constant for new title
        request.setDescription(NEW_DESCRIPTION); // Use constant for new description
        request.setCompleted(true); // Mark as completed

        // Mock repository responses
        when(todoRepository.findById(TEST_ID)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(existingTodo);

        // Call the update method
        Todo updatedTodo = todoService.update(TEST_ID, request);

        // Assertions
        assertEquals(NEW_TITLE, updatedTodo.getTitle()); // Title should be updated
        assertEquals(NEW_DESCRIPTION, updatedTodo.getDescription()); // Description should be updated
        assertTrue(updatedTodo.isCompleted()); // Status should be updated
        assertNotNull(updatedTodo.getUpdatedAt()); // Update date should not be null
    }



    @Test
    void testDeleteTodo() {
        // Create a Todo object to delete
        Todo todo = Todo.builder()
                .id(TEST_ID) // Use constant for ID
                .title(TEST_TITLE) // Use constant for title
                .description(TEST_DESCRIPTION) // Use constant for description
                .completed(false) // Default status
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // Mock repository responses
        when(todoRepository.findById(TEST_ID)).thenReturn(Optional.of(todo));
        doNothing().when(todoRepository).delete(todo);

        // Call the delete method
        todoService.delete(TEST_ID);

        // Check that the delete method was called
        verify(todoRepository, times(1)).delete(todo);
    }


}
