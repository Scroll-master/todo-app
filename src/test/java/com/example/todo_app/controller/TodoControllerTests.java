package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.todo_app.dto.TodoCreateRequest;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    // Constants for testing
    private static final String TEST_TEXT = "Test Todo";
    private static final String NEW_TEXT = "New Todo";
    private static final UUID TEST_ID = UUID.randomUUID();


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    void testGetAllTodos() throws Exception {
        Todo todo = new Todo(TEST_TEXT);
        todo.setId(TEST_ID); // Use constant for UUID

        when(todoService.getAll()).thenReturn(List.of(todo));

        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value(TEST_TEXT));
    }

    @Test
    void testCreateTodo() throws Exception {
        // Test data
        TodoCreateRequest request = new TodoCreateRequest();
        request.setText(NEW_TEXT);

        Todo newTodo = new Todo(request.getText());
        newTodo.setId(TEST_ID); // Use constant for UUID

        // Set up a mock service
        when(todoService.create(request.getText())).thenReturn(newTodo);

        // Sending a request
        mockMvc.perform(post("/api/v1/todos")
                .contentType(APPLICATION_JSON)
                .content("{\"text\": \"" + NEW_TEXT + "\"}")) // Use constant for text
                .andExpect(status().isCreated()) // Check status 201 Created
                .andExpect(jsonPath("$.text").value(NEW_TEXT)); // Check that the task text matches
    }



}
