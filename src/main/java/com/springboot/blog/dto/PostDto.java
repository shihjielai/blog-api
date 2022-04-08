package com.springboot.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime updatedTime;
    private List<CommentDto> comments;
}
