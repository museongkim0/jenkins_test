package com.example.backend.global.exception;

import com.example.backend.global.response.responseStatus.ManagerResponseStatus;

public class ManagerException extends BaseException {
    public ManagerException(ManagerResponseStatus status) {
        super(status);
    }
}
