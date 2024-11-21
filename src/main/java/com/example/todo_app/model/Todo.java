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

    private String title;

    private String description;

    private boolean completed;


    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Constructor for creating a new Todo with text.
     */
    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    /**
     * Method for explicitly updating the text of a task.
     * @param newText new text for the task
     */
    public void applyUpdate(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.updatedAt = Instant.now();
    }
}
