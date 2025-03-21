package com.example.backend.global.exception;

import com.example.backend.global.response.responseStatus.InstructorResponseStatus;

public class InstructorException extends BaseException {
    public InstructorException(InstructorResponseStatus status) {
        super(status);
    }
}