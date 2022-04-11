package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    private Long id;

    @NotBlank
    @Size(min = 2, message = "post title should gave at least 2 characters.")
    private String title;

    @NotBlank
    @Size(min = 10, message = "post description should gave at least 10 characters.")
    private String description;

    @NotBlank
    private String content;

    private LocalDateTime updatedTime;
    private List<CommentDto> comments;
}
