package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper mapper;

    private PostDto postDto;

    private Post post;

    @BeforeEach
    public void setup() {
        postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("test title");
        postDto.setDescription("test description");
        postDto.setContent("test content");

        post = new Post();
        post.setId(1L);
        post.setTitle("test post title");
        post.setDescription("test post description");
        post.setContent("test post content");
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenPostDtoObject_whenSave_thenReturnSavedPostDto() throws Exception {

        BDDMockito.given(postService.createPost(ArgumentMatchers.any(PostDto.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postDto)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(postDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(postDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(postDto.getContent())));
    }

    @Test
    public void givenPostId_whenGetPostById_thenReturnPostDto() throws Exception {

        Long postId = 1L;

        BDDMockito.given(postService.getPostById(postId))
                .willReturn(postDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}", postId));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(postDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(postDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(postDto.getContent())));
    }

    @Test
    public void givenPostList_whenGetAllPosts_thenReturnPostResponseList() throws Exception {

        Integer pageNo = 1;
        Integer pageSize = 2;
        String sortBy = "title";
        String sortDirection = "ASC";

        Post post1 = new Post();
        post1.setId(2L);
        post1.setTitle("test post1 title");
        post1.setDescription("test post1 description");
        post1.setContent("test post1 content");

        PostDto postDto1 = new PostDto();
        postDto1.setId(2L);
        postDto1.setTitle("test postDto1 title");
        postDto1.setDescription("test postDto1 description");
        postDto1.setContent("test postDto1 content");

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(List.of(postDto, postDto1));

        BDDMockito.given(postService.getAllPosts(pageNo, pageSize, sortBy, sortDirection))
                .willReturn(postResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .param("pageNo", String.valueOf(pageNo))
                .param("pageSize", String.valueOf(pageSize))
                .param("sortBy", sortBy)
                .param("sortDirection", sortDirection))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()", CoreMatchers.is(2)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenUpdatedPostDto_whenUpdate_thenReturnUpdatedPostDto() throws Exception {

        Long postId = 1L;

        PostDto updatedPostDto = new PostDto();
        updatedPostDto.setTitle("updated title");
        updatedPostDto.setDescription("updated description");
        updatedPostDto.setContent("updated content");

        BDDMockito.given(postService.updatePost(postId, updatedPostDto))
                .willReturn(updatedPostDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPostDto)))
                .andDo(MockMvcResultHandlers.print());

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(updatedPostDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(updatedPostDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(updatedPostDto.getContent())));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenPostId_whenDeletePost_thenReturnString() throws Exception {

        Long postId = 1L;

        BDDMockito.willDoNothing().given(postService).deletePostById(postId);

        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/{postId}", postId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(response, "post id: " + postId + " deleted successfully.");
    }
}