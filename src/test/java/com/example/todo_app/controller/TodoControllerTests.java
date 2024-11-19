package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    void testGetAllTodos() throws Exception {
        Todo todo = new Todo("Test Todo");
        todo.setId(UUID.randomUUID()); // Set UUID for task

        when(todoService.getAllTodos()).thenReturn(List.of(todo));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Test Todo"));
    }

    @Test
    void testCreateTodo() throws Exception {
        // Test data
        String newTodoText = "New Todo";
        Todo newTodo = new Todo(newTodoText);
        newTodo.setId(UUID.randomUUID()); // Unique identifier

        // Setting up a mock service
        when(todoService.createTodo(newTodoText)).thenReturn(newTodo);

        // Sending a request
        mockMvc.perform(post("/api/todos")
                .contentType(APPLICATION_JSON)
                .content("\"" + newTodoText + "\"")) // JSON string
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(newTodoText));
    }


}
