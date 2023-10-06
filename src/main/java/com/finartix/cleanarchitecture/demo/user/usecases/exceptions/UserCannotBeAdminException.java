package com.finartix.cleanarchitecture.demo.user.usecases.exceptions;

import org.springframework.http.HttpStatus;

import com.finartix.cleanarchitecture.demo.shared.exceptions.ApplicationHttpException;

public class UserCannotBeAdminException extends ApplicationHttpException {

    static final String MESSAGE = "user.errors";
    static final String INTERNAL_STATUS = buildInternalError("user_cannot_be_admin");
    static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public UserCannotBeAdminException() {
        super(MESSAGE, INTERNAL_STATUS, HTTP_STATUS);
    }

    public UserCannotBeAdminException(String message) {
        super(message, INTERNAL_STATUS, HTTP_STATUS);
    }

    public UserCannotBeAdminException(Object payload) {
        super(MESSAGE, payload, INTERNAL_STATUS, HTTP_STATUS);
    }
}
