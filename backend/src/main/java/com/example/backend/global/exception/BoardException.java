package com.example.backend.global.exception;

import com.example.backend.global.response.responseStatus.BoardResponseStatus;

public class BoardException extends BaseException {
    public BoardException(BoardResponseStatus status) {
        super(status);
    }
}
