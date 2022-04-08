package com.springboot.blog.util;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    private final ModelMapper mapper;

    public PostConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public PostDto mapToDto(Post post) {

        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());
//        postDto.setUpdatedTime(post.getUpdatedTime());
        return postDto;
    }

    public Post mapToEntity(PostDto postDto) {

        Post post = mapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        return post;
    }
}

