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
import java.time.Instant;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    // Constants for testing
    private static final String TEST_TITLE = "Test Title";
    private static final String TEST_DESCRIPTION = "Test Description";
    private static final UUID TEST_ID = UUID.randomUUID();


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    void testGetAllTodos() throws Exception {
        // Тестовые данные
        Todo todo = Todo.builder()
                .id(TEST_ID)
                .title(TEST_TITLE) // Используем константу
                .description(TEST_DESCRIPTION) // Используем константу
                .completed(false)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // Настройка мок-сервиса
        when(todoService.getAll()).thenReturn(List.of(todo));

        // Отправка запроса и проверка ответа
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(TEST_TITLE))
                .andExpect(jsonPath("$[0].description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("$[0].completed").value(false));
    }


    @Test
    void testCreateTodo() throws Exception {
        // Тестовые данные
        TodoCreateRequest request = new TodoCreateRequest();
        request.setTitle(TEST_TITLE);
        request.setDescription(TEST_DESCRIPTION);

        Todo newTodo = Todo.builder()
                .id(TEST_ID)
                .title(TEST_TITLE)
                .description(TEST_DESCRIPTION)
                .completed(false)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // Настройка мок-сервиса
        when(todoService.create(any(TodoCreateRequest.class))).thenReturn(newTodo);

        // Отправка запроса и проверка ответа
        mockMvc.perform(post("/api/v1/todos")
                .contentType(APPLICATION_JSON)
                .content("{\"title\": \"" + TEST_TITLE + "\", \"description\": \"" + TEST_DESCRIPTION + "\"}"))
                .andExpect(status().isCreated()) // Проверяем, что возвращается статус 201
                .andExpect(jsonPath("$.title").value(TEST_TITLE))
                .andExpect(jsonPath("$.description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("$.completed").value(false));
    }

}
