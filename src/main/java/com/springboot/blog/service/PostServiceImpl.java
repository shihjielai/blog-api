package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.PostNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.response.PostResponse;
import com.springboot.blog.util.PostConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = postConverter.mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = postConverter.mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(Integer pageNo, Integer pageSize) {
        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Post> page = postRepository.findAll(pageable);

        // get content from page object
        List<Post> posts = page.getContent();

        List<PostDto> content = posts.stream()
                .map(post -> postConverter.mapToDto(post))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(page.getNumber() + 1);
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setLast(page.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> postConverter.mapToDto(post))
                .orElseThrow(() -> new PostNotFoundException("post id: " + id + " is not found."));
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("post id: " + id + " is not found."));

        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());

        postRepository.save(post);

        return postConverter.mapToDto(post);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("post id: " + id + " is not found."));

        postRepository.delete(post);
    }
}
