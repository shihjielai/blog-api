package com.springboot.blog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ApiModel(description = "Comment model information")
@Data
public class CommentDto {

    @ApiModelProperty(value = "Comment id")
    private Long id;

    @ApiModelProperty(value = "Comment name")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "Comment email")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(value = "Comment content")
    @NotBlank
    @Size(min = 10)
    private String content;

    private LocalDateTime updatedTime;
}
