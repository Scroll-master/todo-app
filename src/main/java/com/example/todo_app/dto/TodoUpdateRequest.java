package com.example.todo_app.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TodoUpdateRequest {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 50, message = "Title cannot exceed 50 characters")

    public TodoUpdateRequest() {
        // Default constructor for deserialization
    }

    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private boolean completed;

}
