package com.example.backend.global.exception;

import com.example.backend.global.response.responseStatus.StudentResponseStatus;

public class StudentException extends BaseException {
    public StudentException(StudentResponseStatus status) {
        super(status);
    }
}
