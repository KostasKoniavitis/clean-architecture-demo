package com.finartix.cleanarchitecture.demo.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApplicationHttpException {

    static final String INTERNAL_STATUS = buildInternalError("resource_could_not_be_found");
    static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    static final String MESSAGE = "generic.errors.notfound";

    public ResourceNotFoundException() {
        super(MESSAGE, INTERNAL_STATUS, HTTP_STATUS);
    }

    public ResourceNotFoundException(String message) {
        super(message, INTERNAL_STATUS, HTTP_STATUS);
    }

    public ResourceNotFoundException(Object payload) {
        super(MESSAGE, payload, INTERNAL_STATUS, HTTP_STATUS);
    }
}
