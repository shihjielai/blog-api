package com.springboot.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private String name;
    private String email;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
