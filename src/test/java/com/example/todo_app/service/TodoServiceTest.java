package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        String text = "New Todo";
        Todo todo = new Todo(text);
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo createdTodo = todoService.createTodo(text);
        assertNotNull(createdTodo);
        assertEquals(text, createdTodo.getText());
    }

    @Test
    void testGetAllTodos() {
        when(todoRepository.findAll()).thenReturn(List.of(new Todo("Todo 1"), new Todo("Todo 2")));

        List<Todo> todos = todoService.getAllTodos();
        assertEquals(2, todos.size());
    }

    @Test
    void testGetTodoById() {
        Long id = 1L;
        Todo todo = new Todo("Sample Todo");
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        Optional<Todo> foundTodo = todoService.getTodoById(id);
        assertTrue(foundTodo.isPresent());
        assertEquals("Sample Todo", foundTodo.get().getText());
    }

    @Test
    void testUpdateTodo() {
        Long id = 1L;
        String updatedText = "Updated Todo";
        Todo existingTodo = new Todo("Old Todo");
        when(todoRepository.findById(id)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(existingTodo);

        Todo updatedTodo = todoService.updateTodo(id, updatedText);
        assertEquals(updatedText, updatedTodo.getText());
    }

    @Test
    void testDeleteTodo() {
        Long id = 1L;
        doNothing().when(todoRepository).deleteById(id);

        todoService.deleteTodo(id);
        verify(todoRepository, times(1)).deleteById(id);
    }
}
