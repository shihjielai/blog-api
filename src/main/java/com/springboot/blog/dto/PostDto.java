package com.springboot.blog.dto;

import lombok.Data;

@Data
public class PostDto {

    private String title;
    private String description;
    private String content;
}
