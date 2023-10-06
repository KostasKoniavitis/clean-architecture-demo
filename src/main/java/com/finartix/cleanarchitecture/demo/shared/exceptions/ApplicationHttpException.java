package com.finartix.cleanarchitecture.demo.shared.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public abstract class ApplicationHttpException extends ApplicationException {

    @Getter protected HttpStatus httpStatus;
    @Getter protected Object payload;

    protected ApplicationHttpException(
            String message, String internalStatus, HttpStatus httpStatus) {
        super(message, internalStatus);
        this.httpStatus = httpStatus;
    }

    protected ApplicationHttpException(
            String message, Object payload, String internalStatus, HttpStatus httpStatus) {
        super(message, internalStatus);
        this.httpStatus = httpStatus;
        this.payload = payload;
    }
}
