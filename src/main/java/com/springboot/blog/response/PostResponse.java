package com.springboot.blog.response;

import com.springboot.blog.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private List<PostDto> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;
}
