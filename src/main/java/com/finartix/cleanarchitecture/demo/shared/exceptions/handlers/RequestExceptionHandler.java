package com.finartix.cleanarchitecture.demo.shared.exceptions.handlers;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import com.finartix.cleanarchitecture.demo.shared.exceptions.ApplicationException;
import com.finartix.cleanarchitecture.demo.shared.exceptions.ApplicationHttpException;
import com.finartix.cleanarchitecture.demo.shared.models.ApiErrorModel;

@Slf4j
public abstract class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle ApplicationException. Handle
     *
     * @param ex ResourceAlreadyExistsException
     * @return the ApiError object
     */
    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        return buildResponseEntity(
                new ApiErrorModel(
                        INTERNAL_SERVER_ERROR, ex.getInternalStatus(), ex.getMessage(), ex));
    }

    @ExceptionHandler(ApplicationHttpException.class)
    protected ResponseEntity<Object> handleApplicationException(ApplicationHttpException ex) {
        return buildResponseEntity(
                new ApiErrorModel(
                        ex.getHttpStatus(),
                        ex.getPayload(),
                        ex.getInternalStatus(),
                        ex.getMessage(),
                        ex));
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ApiErrorModel apiErrorModel = new ApiErrorModel(BAD_REQUEST);
        apiErrorModel.setMessage("Validation error");
        apiErrorModel.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiErrorModel);
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(
                    new ApiErrorModel(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        ApiErrorModel apiErrorModel = new ApiErrorModel(BAD_REQUEST);
        apiErrorModel.setMessage(
                String.format(
                        "The parameter '%s' of value '%s' could not be converted to type '%s'",
                        ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiErrorModel.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiErrorModel);
    }

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter
     * is missing.
     *
     * @param ex MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiErrorModel(BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(
                new ApiErrorModel(
                        UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiErrorModel apiErrorModel = new ApiErrorModel(BAD_REQUEST);
        apiErrorModel.setMessage("Validation error");
        apiErrorModel.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiErrorModel.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiErrorModel);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info(
                "{} to {}",
                servletWebRequest.getHttpMethod(),
                servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiErrorModel(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(new ApiErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiErrorModel apiErrorModel = new ApiErrorModel(BAD_REQUEST);
        apiErrorModel.setMessage(
                String.format(
                        "Could not find the '%s' method for URL '%s'",
                        ex.getHttpMethod(), ex.getRequestURL()));
        apiErrorModel.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiErrorModel);
    }

    /** Handle javax.persistence.EntityNotFoundException */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponseEntity(new ApiErrorModel(HttpStatus.NOT_FOUND, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorModel apiErrorModel) {
        return new ResponseEntity<>(apiErrorModel, apiErrorModel.getStatus());
    }
}
