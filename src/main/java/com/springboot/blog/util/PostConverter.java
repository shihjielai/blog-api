package com.springboot.blog.util;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    private final ModelMapper mapper;

    @Autowired
    public PostConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PostDto mapToDto(Post post) {

        PostDto postDto = mapper.map(post, PostDto.class);

        return postDto;
    }

    public Post mapToEntity(PostDto postDto) {

        Post post = mapper.map(postDto, Post.class);

        return post;
    }
}

