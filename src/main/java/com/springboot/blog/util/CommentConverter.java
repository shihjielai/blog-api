package com.springboot.blog.util;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    private final ModelMapper mapper;

    public CommentConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CommentDto mapToDto(Comment comment) {

        CommentDto commentDto = mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setContent(comment.getContent());
//        commentDto.setUpdatedTime(comment.getUpdatedTime());
        return commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto) {

        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setContent(commentDto.getContent());
        return comment;
    }
}
