package com.springboot.blog.util;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    private final ModelMapper mapper;

    @Autowired
    public CommentConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CommentDto mapToDto(Comment comment) {

        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        return commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto) {

        Comment comment = mapper.map(commentDto, Comment.class);

        return comment;
    }
}
