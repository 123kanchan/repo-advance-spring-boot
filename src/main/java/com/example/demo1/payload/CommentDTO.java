package com.example.demo1.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDTO {
    private long id;
    @NotEmpty(message="name not empty")
    private String name;
    @Email
    @NotEmpty
    private String email;
    private String body;
}
