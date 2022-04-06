package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts(Integer pageNo, Integer pageSize);

    PostDto getPostById(Long id);

    PostDto updatePost(Long id, PostDto postDto);

    void deletePostById(Long id);
}
