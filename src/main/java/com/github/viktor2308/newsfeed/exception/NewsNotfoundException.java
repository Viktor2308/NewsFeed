package com.github.viktor2308.newsfeed.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NewsNotfoundException extends RuntimeException{

    public NewsNotfoundException(String message) {
        super(message);
    }
}
