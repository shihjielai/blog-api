package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.util.PostConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostConverter postConverter;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
    private PostDto postDto;

    @BeforeEach
    public void setup() {

        postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("test title");
        postDto.setDescription("test description");
        postDto.setContent("test content");

        post = new Post();
        post.setId(1L);
        post.setTitle("test title");
        post.setDescription("test description");
        post.setContent("test content");
    }

    @Test
    void givenPostDtoObject_whenSave_thenReturnPostDtoResponse() {

        BDDMockito.given(postConverter.mapToEntity(postDto))
                .willReturn(post);

        BDDMockito.given(postRepository.save(post))
                .willReturn(post);

        BDDMockito.given(postConverter.mapToDto(post))
                .willReturn(postDto);

        PostDto postResponse = postService.createPost(postDto);
        Assertions.assertThat(postResponse).isNotNull();
    }

    @Test
    void givenPostList_whenGetAllPosts_thenReturnPostResponse() {

        int pageNo = 1;
        int pageSize = 3;
        String sortBy = "title";
        String sortDirection = "ASC";

        Post post1 = new Post();
        post1.setId(2L);
        post1.setTitle("test title2");
        post1.setDescription("test description2");
        post1.setContent("test content2");

        PostDto postDto1 = new PostDto();
        postDto1.setId(2L);
        postDto1.setTitle("test title2");
        postDto1.setDescription("test description2");
        postDto1.setContent("test content2");

        List<Post> postList = List.of(post, post1);

        Sort sort = Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Post> postPage = new PageImpl<>(postList);
        BDDMockito.given(postRepository.findAll(pageable)).willReturn(postPage);

        BDDMockito.given(postConverter.mapToDto(post))
                .willReturn(postDto);

        BDDMockito.given(postConverter.mapToDto(post1))
                .willReturn(postDto1);

        PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDirection);

        Assertions.assertThat(postResponse).isNotNull();
    }

    @Test
    void givenPostId_whenGetById_thenReturnPostDto() {

        Mockito.when(postConverter.mapToDto(post))
                .thenReturn(postDto);

        Mockito.when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        PostDto postResponse = postService.getPostById(post.getId());
        Assertions.assertThat(postResponse).isNotNull();
    }

    @Test
    void GivenPostDtoObject_whenUpdatePost_thenReturnUpdatedPostDtoObject() {

        postDto.setTitle("updated title");
        postDto.setDescription("updated description");
        postDto.setContent("updated content");

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        BDDMockito.given(postRepository.findById(1L))
                .willReturn(Optional.of(post));

        BDDMockito.given(postRepository.save(post))
                .willReturn(post);

        BDDMockito.given(postConverter.mapToDto(post))
                .willReturn(postDto);

        PostDto updatedPostDto = postService.updatePost(post.getId(), postDto);

        Assertions.assertThat(updatedPostDto.getTitle()).isEqualTo("updated title");
        Assertions.assertThat(updatedPostDto.getDescription()).isEqualTo("updated description");
        Assertions.assertThat(updatedPostDto.getContent()).isEqualTo("updated content");
    }

    @Test
    void givenPostId_whenDeletePost_thenNothing() {

        Long postId = 1L;

        BDDMockito.given(postRepository.findById(postId))
                .willReturn(Optional.of(post));

        BDDMockito.willDoNothing().given(postRepository).delete(post);

        postService.deletePostById(postId);

        Mockito.verify(postRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(postRepository, Mockito.times(1)).delete(post);
    }
}