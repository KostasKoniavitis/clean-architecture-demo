package com.finartix.cleanarchitecture.demo.shared.models;

import static com.finartix.cleanarchitecture.demo.shared.exceptions.ApplicationException.buildInternalError;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.Data;

import org.hibernate.validator.internal.engine.path.PathImpl;

@Data
public class ApiError {

    private HttpStatus status;

    private String internalStatus;

    private ZonedDateTime timestamp;

    private String message;

    private String debugMessage;

    private List<ApiValidationError> validationErrors;

    private Object payload;

    private ApiError() {
        timestamp = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.internalStatus = buildInternalError("Unknown");
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.internalStatus = "Unknown";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String internalStatus, String message, Throwable ex) {
        this();
        this.status = status;
        this.internalStatus = internalStatus;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(
            HttpStatus status,
            Object payload,
            String internalStatus,
            String message,
            Throwable ex) {
        this();
        this.status = status;
        this.payload = payload;
        this.internalStatus = internalStatus;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addSubError(ApiValidationError validationError) {
        if (validationErrors == null) {
            validationErrors = new ArrayList<>();
        }
        validationErrors.add(validationError);
    }

    private void addValidationError(
            String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation
     * fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }
}
