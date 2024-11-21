package com.example.todo_app.dto;

import lombok.Data;

@Data
public class TodoUpdateRequest {

    public TodoUpdateRequest() {
        // Default constructor for deserialization
    }

    private String text;
}
