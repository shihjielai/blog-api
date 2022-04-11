package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.util.CommentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentConverter commentConverter, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment = commentConverter.mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        // set post the comment entity
        comment.setPost(post);

        // save comment entity to database
        commentRepository.save(comment);

        return commentConverter.mapToDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {

        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert list of comment entities to list of comment dto
        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> commentConverter.mapToDto(comment))
                .collect(Collectors.toList());

        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        Comment comment = validatePostAndComment(postId, commentId);

        return commentConverter.mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

        Comment comment = validatePostAndComment(postId, commentId);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setContent(commentDto.getContent());

        commentRepository.save(comment);

        return commentConverter.mapToDto(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Comment comment = validatePostAndComment(postId, commentId);

        commentRepository.delete(comment);
    }

    private Comment validatePostAndComment(Long postId, Long commentId) {

        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException("this comment does not belong to this post.");
        }

        return comment;
    }
}
