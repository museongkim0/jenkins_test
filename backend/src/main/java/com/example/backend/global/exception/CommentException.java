package com.example.backend.global.exception;

import com.example.backend.global.response.responseStatus.CommentResponseStatus;

public class CommentException extends BaseException {
    public CommentException(CommentResponseStatus status) {
        super(status);
    }
}
