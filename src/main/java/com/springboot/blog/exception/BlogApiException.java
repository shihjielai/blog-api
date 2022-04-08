package com.springboot.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogApiException extends RuntimeException {

    private final String message;

    public BlogApiException(String message) {
        this.message = message;
    }
}
