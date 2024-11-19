package com.example.todo_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // UUID requires AUTO to generate
    private UUID id;

    private String text;

    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Constructor for creating a new Todo with text.
     */
    public Todo(String text) {
        this.text = text;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    /**
     * Method for explicitly updating the text of a task.
     * @param newText new text for the task
     */
    public void applyUpdate(String newText) {
        this.text = newText;
        this.updatedAt = Instant.now();
    }
}
