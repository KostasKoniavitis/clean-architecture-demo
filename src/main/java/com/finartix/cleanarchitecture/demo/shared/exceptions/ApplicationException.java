package com.finartix.cleanarchitecture.demo.shared.exceptions;

import lombok.Getter;

public abstract class ApplicationException extends RuntimeException {

    @Getter protected final String internalStatus;

    protected ApplicationException(String message, String internalStatus) {
        super(message);
        this.internalStatus = internalStatus;
    }

    public static String buildInternalError(String errorIdentification) {
        return String.format("%s", errorIdentification.toLowerCase());
    }
}
