package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.PostNotFoundException;
import com.springboot.blog.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new PostNotFoundException("post id: " + postId + " is not found."));

        // set post the comment entity
        comment.setPost(post);

        // save comment entity to database
        Comment newComment = commentRepository.save(comment);

        return commentConverter.mapToDto(newComment);
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

        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("post id: " + postId + " is not found."));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException("this comment (id: " + commentId + ") does not belong to post(id: " + postId + ").");
        }

        return commentConverter.mapToDto(comment);
    }
}
