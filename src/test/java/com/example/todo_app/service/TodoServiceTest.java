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


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    // Constants for testing
    private static final UUID TEST_ID = UUID.randomUUID();
    private static final String OLD_TEXT = "Old Todo";
    private static final String NEW_TEXT = "New Todo";
    private static final String SAMPLE_TEXT = "Sample Todo";

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
        request.setText(NEW_TEXT);

        Todo todo = new Todo(request.getText());
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Call the task creation method
        Todo createdTodo = todoService.create(request.getText());

        // Check the results
        assertNotNull(createdTodo); // Make sure the task is created
        assertEquals(NEW_TEXT, createdTodo.getText()); // Text should match the request
        assertNotNull(createdTodo.getCreatedAt()); // Check the creation date
        assertNotNull(createdTodo.getUpdatedAt()); // Check the update date
    }


    @Test
    void testGetAllTodos() {
        when(todoRepository.findAll()).thenReturn(List.of(
                new Todo(OLD_TEXT),
                new Todo(NEW_TEXT)
        ));

        List<Todo> todos = todoService.getAll();
        assertEquals(2, todos.size());
        assertEquals(OLD_TEXT, todos.get(0).getText());
        assertEquals(NEW_TEXT, todos.get(1).getText());
    }

    @Test
    void testGetTodoById() {
        // Create a Todo object with a specific ID

        Todo todo = new Todo(SAMPLE_TEXT);
        todo.setId(TEST_ID);

        // Mock repository response
        when(todoRepository.findById(TEST_ID)).thenReturn(Optional.of(todo));

        // Call the method
        Todo foundTodo = todoService.getTodo(TEST_ID);

        // Check the results
        assertNotNull(foundTodo); // Check that the task was found
        assertEquals(SAMPLE_TEXT, foundTodo.getText()); // Text should match
        assertEquals(TEST_ID, foundTodo.getId()); // ID should match
}


    @Test
    void testUpdateTodo() {
        // Create an existing Todo object
        Todo existingTodo = new Todo(OLD_TEXT);
        existingTodo.setId(TEST_ID);

        // Create a TodoUpdateRequest object
        TodoUpdateRequest request = new TodoUpdateRequest();
        request.setText(NEW_TEXT);

        // Mock repository responses
        when(todoRepository.findById(TEST_ID)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(existingTodo);

        // Call the update method
        Todo updatedTodo = todoService.update(TEST_ID, request);

        // Assertions
        assertEquals(NEW_TEXT, updatedTodo.getText()); // Text should be updated
        assertNotNull(updatedTodo.getUpdatedAt()); // Update date should not be null
}


    @Test
    void testDeleteTodo() {
        // Create a Todo object to delete
        Todo todo = new Todo(SAMPLE_TEXT);
        todo.setId(TEST_ID);

        // Mock repository responses
        when(todoRepository.findById(TEST_ID)).thenReturn(Optional.of(todo));
        doNothing().when(todoRepository).delete(todo);

        // Call the delete method
        todoService.delete(TEST_ID);

        // Check that the delete method was called
        verify(todoRepository, times(1)).delete(todo);
}

}
