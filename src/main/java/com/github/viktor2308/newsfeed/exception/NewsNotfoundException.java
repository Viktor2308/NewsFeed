package com.github.viktor2308.newsfeed.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@Slf4j
public class NewsNotfoundException extends RuntimeException{

    public NewsNotfoundException(String message) {
        super(message);
        log.error("News Not found Exception: " + message);
    }
}
