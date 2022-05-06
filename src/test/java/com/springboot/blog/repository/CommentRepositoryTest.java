package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;

    private Post post;

    @BeforeEach
    public void setup() {

        post = new Post();
        post.setId(1L);
        post.setTitle("test post title");
        post.setDescription("test post description");
        post.setContent("test post content");

        comment = new Comment();
        comment.setName("test comment name");
        comment.setEmail("test comment email");
        comment.setContent("test comment content");
        comment.setPost(post);
    }

    @Test
    public void givenComment_whenSave_thenReturnSavedComment() {

        Comment savedComment = commentRepository.save(comment);
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getContent()).isEqualTo(comment.getContent());
    }

    @Test
    public void givenComments_whenFindAll_thenReturnComments() {

        Comment comment1 = new Comment();
        comment1.setName("test comment1 name");
        comment1.setEmail("test comment1 email");
        comment1.setContent("test comment1 content");
        comment1.setPost(post);

        commentRepository.saveAll(List.of(comment, comment1));

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments).isNotNull();
        assertThat(comments).isNotEmpty();
        assertThat(comments.size()).isNotEqualTo(0);
    }

    @Test
    public void givenCommentObject_whenUpdateComment_thenReturnUpdatedComment() {

        commentRepository.save(comment);
        Comment savedComment = commentRepository.findById(this.comment.getId()).get();

        savedComment.setName("updated name");
        savedComment.setEmail("updated email");
        savedComment.setContent("updated content");

        Comment updatedComment = commentRepository.save(savedComment);

        assertThat(updatedComment.getName()).isEqualTo("updated name");
        assertThat(updatedComment.getEmail()).isEqualTo("updated email");
        assertThat(updatedComment.getContent()).isEqualTo("updated content");
    }

    @Test
    public void givenCommentObject_whenDelete_thenRemoveComment() {

        commentRepository.save(comment);

        commentRepository.delete(comment);

        Optional<Comment> commentOptional = commentRepository.findById(comment.getId());

        assertThat(commentOptional).isEmpty();
    }

    @Test
    public void givenPostId_whenFindByPostId_thenReturnCommentsOfThePost() {

        Comment comment1 = new Comment();
        comment1.setName("test comment1 name");
        comment1.setEmail("test comment1 email");
        comment1.setContent("test comment1 content");
        comment1.setPost(post);

        List<Comment> comments = commentRepository.findByPostId(post.getId());

        assertThat(comments).isNotNull();
        assertThat(comments).isNotEmpty();
    }
}