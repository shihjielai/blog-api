package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper mapper;

    private Post post;

    private CommentDto commentDto;

    private CommentDto commentDto1;

    private Comment comment;

    @BeforeEach
    public void setup() {

        post = new Post();
        post.setId(1L);
        post.setTitle("test post title");
        post.setDescription("test post description");
        post.setContent("test post content");

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setName("test commentDto name");
        commentDto.setEmail("test_commentDto@gmail.com");
        commentDto.setContent("test commentDto content");

        comment = new Comment();
        comment.setId(1L);
        comment.setName("test comment name");
        comment.setEmail("test_comment@gmail.com");
        comment.setContent("test comment content");
    }

    @Test
    public void givenCommentDtoObject_whenSave_thenReturnSavedCommentDto() throws Exception {

        BDDMockito.given(commentService.createComment(post.getId(), commentDto))
                .willReturn(commentDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/{postId}/comments", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commentDto)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(commentDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(commentDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(commentDto.getContent())));
    }

    @Test
    public void givenPostIdAndComment_whenGetCommentByPostIdAndCommentId_thenReturnCommentDto() throws Exception {

        BDDMockito.given(commentService.getCommentById(post.getId(), comment.getId()))
                .willReturn(commentDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}/comments/{commentId}", post.getId(), commentDto.getId()));

        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(commentDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(commentDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(commentDto.getContent())));
    }

    @Test
    public void givenPostId_whenGetCommentsByPostId_thenReturnListOfCommentDto() throws Exception {

        commentDto1 = new CommentDto();
        commentDto1.setId(1L);
        commentDto1.setName("test commentDto1 name");
        commentDto1.setEmail("test_commentDto1@gmail.com");
        commentDto1.setContent("test commentDto1 content");

        BDDMockito.given(commentService.getCommentsByPostId(post.getId()))
                .willReturn(List.of(commentDto, commentDto1));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}/comments", post.getId()));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(2)));
    }

    @Test
    public void givenPostIdAndCommentId_whenUpdateComment_thenReturnUpdatedCommentDto() throws Exception {

        CommentDto updatedCommentDto = new CommentDto();
        updatedCommentDto.setName("test updatedCommentDto name");
        updatedCommentDto.setEmail("test_updatedCommentDto@gmail.com");
        updatedCommentDto.setContent("test updatedCommentDto content");

        BDDMockito.given(commentService.updateComment(post.getId(), comment.getId(), updatedCommentDto))
                .willReturn(updatedCommentDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/{postId}/comments/{commentId}", post.getId(), commentDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedCommentDto)))
                .andDo(MockMvcResultHandlers.print());

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(updatedCommentDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedCommentDto.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(updatedCommentDto.getContent())));
    }

    @Test
    public void givenPostIdAndCommentId_whenDeleteComment_thenReturnString() throws Exception {

        BDDMockito.willDoNothing().given(commentService).deleteComment(post.getId(), comment.getId());

        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/{postId}/comments/{commentId}", post.getId(), comment.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(response, "the comment is deleted successfully.");
    }
}