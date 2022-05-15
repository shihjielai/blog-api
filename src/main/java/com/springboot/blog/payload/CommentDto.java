package com.springboot.blog.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
