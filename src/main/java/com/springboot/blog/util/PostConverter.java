package com.springboot.blog.util;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;
    }

    public Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }
}

