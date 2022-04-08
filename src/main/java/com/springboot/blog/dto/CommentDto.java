package com.springboot.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Long id;
    private String name;
    private String email;
    private String content;
    private LocalDateTime updatedTime;
}
