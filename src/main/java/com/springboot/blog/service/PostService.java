package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.response.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(Integer pageNo, Integer pageSize);

    PostDto getPostById(Long id);

    PostDto updatePost(Long id, PostDto postDto);

    void deletePostById(Long id);
}
