package com.example.todo_app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoResponse {
    private String id;
    private String text;
    private String createdAt;
    private String updatedAt;
}
