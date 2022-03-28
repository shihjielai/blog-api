package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.PostNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.util.PostConverter;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(post -> postConverter.mapToDto(post))
                .collect(Collectors.toList());
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

//        if (Objects.nonNull(postDto.getContent())) {
//            post.setContent(postDto.getContent());
//        }

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
