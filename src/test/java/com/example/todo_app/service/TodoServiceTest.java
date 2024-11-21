package com.example.todo_app.service;

import com.example.todo_app.dto.TodoCreateRequest;
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
        // Тестовые данные
        TodoCreateRequest request = new TodoCreateRequest();
        request.setText("New Todo");

        Todo todo = new Todo(request.getText());
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Вызываем метод создания задачи
        Todo createdTodo = todoService.create(request.getText());

        // Проверяем результаты
        assertNotNull(createdTodo); // Убедимся, что задача создана
        assertEquals(request.getText(), createdTodo.getText()); // Проверяем текст задачи
        assertNotNull(createdTodo.getCreatedAt()); // Проверяем дату создания
        assertNotNull(createdTodo.getUpdatedAt()); // Проверяем дату обновления
    }


    @Test
    void testGetAllTodos() {
        when(todoRepository.findAll()).thenReturn(List.of(
                new Todo("Todo 1"),
                new Todo("Todo 2")
        ));

        List<Todo> todos = todoService.getAll();
        assertEquals(2, todos.size());
        assertEquals("Todo 1", todos.get(0).getText());
        assertEquals("Todo 2", todos.get(1).getText());
    }

    @Test
    void testGetTodoById() {
        UUID id = UUID.randomUUID();
        Todo todo = new Todo("Sample Todo");
        todo.setId(id); // Set UUID for verification

        // Set up a mock repository
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        // Call the method
        Todo foundTodo = todoService.getTodo(id);

        // Check the results
        assertNotNull(foundTodo); // Check that the task was found
        assertEquals("Sample Todo", foundTodo.getText()); // Check the task text
        assertEquals(id, foundTodo.getId()); // Check the task ID
}


    @Test
    void testUpdateTodo() {
        UUID id = UUID.randomUUID();
        String updatedText = "Updated Todo";
        Todo existingTodo = new Todo("Old Todo");
        existingTodo.setId(id); // Set UUID for verification

        when(todoRepository.findById(id)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(existingTodo);

        Todo updatedTodo = todoService.update(id, updatedText);
        assertEquals(updatedText, updatedTodo.getText());
        assertNotNull(updatedTodo.getUpdatedAt());
    }

    @Test
    void testDeleteTodo() {
        UUID id = UUID.randomUUID();
        Todo todo = new Todo("Sample Todo");
        todo.setId(id); // Set UUID for task

        // Set up a mock repository
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));
        doNothing().when(todoRepository).delete(todo);

        // Call the method
        todoService.delete(id);

        // Check that the delete method was called
        verify(todoRepository, times(1)).delete(todo);
}

}
