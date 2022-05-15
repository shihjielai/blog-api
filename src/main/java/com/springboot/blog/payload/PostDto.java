package com.springboot.blog.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(description = "Post model information")
@Data
public class PostDto {

    @ApiModelProperty(value = "Blog post id")
    private Long id;

    @ApiModelProperty(value = "Blog post title")
    @NotBlank
    @Size(min = 2, message = "post title should gave at least 2 characters.")
    private String title;

    @ApiModelProperty(value = "Blog post description")
    @NotBlank
    @Size(min = 10, message = "post description should gave at least 10 characters.")
    private String description;

    @ApiModelProperty(value = "Blog post content")
    @NotBlank
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    
    private List<CommentDto> comments;
}
