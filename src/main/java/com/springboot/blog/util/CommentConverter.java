package com.springboot.blog.util;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setContent(comment.getContent());
        commentDto.setCreatedTime(comment.getCreatedTime());
        commentDto.setUpdatedTime(comment.getUpdatedTime());
        return commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setContent(commentDto.getContent());
        comment.setCreatedTime(commentDto.getCreatedTime());
        comment.setUpdatedTime(commentDto.getUpdatedTime());
        return comment;
    }
}
