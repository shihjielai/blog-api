package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.util.CommentConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentConverter commentConverter;

    private CommentDto commentDto;

    private Comment comment;

    private Post post;

    @BeforeEach
    public void setup() {

        post = new Post();
        post.setId(1L);
        post.setTitle("test post title");
        post.setDescription("test post description");
        post.setContent("test post content");

        commentDto = new CommentDto();
        commentDto.setName("test commentDto name");
        commentDto.setEmail("test commentDto email");
        commentDto.setContent("test commentDto content");

        comment = new Comment();
        comment.setId(1L);
        comment.setName("test comment name");
        comment.setEmail("test comment email");
        comment.setContent("test comment content");
        comment.setPost(post);
    }

    @Test
    public void givenPostIdAndCommentDtoObject_whenSave_thenReturnCommentDtoResponse() {

        BDDMockito.given(commentConverter.mapToEntity(commentDto))
                .willReturn(comment);

        BDDMockito.given(postRepository.findById(post.getId()))
                .willReturn(Optional.of(post));

        BDDMockito.given(commentConverter.mapToDto(comment))
                .willReturn(commentDto);

        CommentDto commentDtoResponse = commentService.createComment(post.getId(), commentDto);
        Assertions.assertThat(commentDtoResponse).isNotNull();
    }

    @Test
    public void givenCommentList_whenGetAllCommentsById_thenReturnCommentDtoListsOfPost() {

        Comment comment1 = new Comment();
        comment1.setName("test comment1 name");
        comment1.setEmail("test comment1 email");
        comment1.setContent("test comment1 content");
        comment1.setPost(post);

        CommentDto commentDto1 = new CommentDto();
        commentDto1.setName("test commentDto1 name");
        commentDto1.setEmail("test commentDto1 email");
        commentDto1.setContent("test commentDto1 content");

        BDDMockito.given(commentRepository.findByPostId(post.getId()))
                .willReturn(List.of(comment, comment1));

        BDDMockito.given(commentConverter.mapToDto(comment))
                .willReturn(commentDto);

        BDDMockito.given(commentConverter.mapToDto(comment1))
                .willReturn(commentDto1);

        List<CommentDto> commentDtoListOfPost = commentService.getCommentsByPostId(post.getId());

        Assertions.assertThat(commentDtoListOfPost).isNotEmpty();
        Assertions.assertThat(commentDtoListOfPost).isNotNull();
        Assertions.assertThat(commentDtoListOfPost.size()).isGreaterThan(0);
    }

    @Test
    public void givenCommentId_whenGetById_thenReturnCommentDto() {

        BDDMockito.given(postRepository.findById(post.getId()))
                .willReturn(Optional.of(post));

        BDDMockito.given(commentRepository.findById(comment.getId()))
                .willReturn(Optional.of(comment));

        BDDMockito.given(commentConverter.mapToDto(comment))
                .willReturn(commentDto);

        CommentDto commentDto = commentService.getCommentById(post.getId(), comment.getId());

        Assertions.assertThat(commentDto).isNotNull();
    }

    @Test
    public void givenCommentDtoObject_whenUpdateComment_thenReturnUpdatedCommentDtoObject() {

        BDDMockito.given(postRepository.findById(post.getId()))
                .willReturn(Optional.of(post));

        BDDMockito.given(commentRepository.findById(comment.getId()))
                .willReturn(Optional.of(comment));

        commentDto.setName("updated name");
        commentDto.setEmail("updated email");
        commentDto.setContent("updated content");

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setContent(commentDto.getContent());

        BDDMockito.given(commentRepository.save(comment))
                .willReturn(comment);

        BDDMockito.given(commentConverter.mapToDto(comment))
                .willReturn(commentDto);

        CommentDto updatedCommentDto = commentService.updateComment(post.getId(), comment.getId(), this.commentDto);

        Assertions.assertThat(updatedCommentDto.getName()).isEqualTo("updated name");
        Assertions.assertThat(updatedCommentDto.getEmail()).isEqualTo("updated email");
        Assertions.assertThat(updatedCommentDto.getContent()).isEqualTo("updated content");
    }

    @Test
    public void givenPostIdAndCommentId_whenDeleteComment_thenNothing() {

        BDDMockito.given(postRepository.findById(post.getId()))
                .willReturn(Optional.of(post));

        BDDMockito.given(commentRepository.findById(comment.getId()))
                .willReturn(Optional.of(comment));

        BDDMockito.willDoNothing().given(commentRepository).delete(comment);

        commentService.deleteComment(post.getId(), comment.getId());

        Mockito.verify(postRepository, Mockito.times(1)).findById(post.getId());
        Mockito.verify(commentRepository, Mockito.times(1)).findById(comment.getId());
        Mockito.verify(commentRepository, Mockito.times(1)).delete(comment);
    }
}