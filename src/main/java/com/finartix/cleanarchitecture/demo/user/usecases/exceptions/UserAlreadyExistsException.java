package com.finartix.cleanarchitecture.demo.user.usecases.exceptions;

import org.springframework.http.HttpStatus;

import com.finartix.cleanarchitecture.demo.shared.exceptions.ApplicationHttpException;

public class UserAlreadyExistsException extends ApplicationHttpException {

    static final String MESSAGE = "user.errors";
    static final String INTERNAL_STATUS = buildInternalError("user_already_exists");
    static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public UserAlreadyExistsException() {
        super(MESSAGE, INTERNAL_STATUS, HTTP_STATUS);
    }

    public UserAlreadyExistsException(String message) {
        super(message, INTERNAL_STATUS, HTTP_STATUS);
    }

    public UserAlreadyExistsException(Object payload) {
        super(MESSAGE, payload, INTERNAL_STATUS, HTTP_STATUS);
    }
}
