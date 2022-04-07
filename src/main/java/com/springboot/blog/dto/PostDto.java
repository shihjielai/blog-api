package com.springboot.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private String title;
    private String description;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
