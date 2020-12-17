package com.api.order.exceptions;

import com.api.order.model.OrderManagementConstants;
import com.google.api.client.http.HttpStatusCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * ErrorHandler - Handle exceptions across the whole application.
 */
@RestControllerAdvice
public class ErrorHandler {

    /**
     * MethodArgumentNotValidException - Handle exceptions.
     * @param ex
     * @return map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgsExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String errorMessage = error.getDefaultMessage();
            errors.put(OrderManagementConstants.STATUS_CODE, String.valueOf(HttpStatusCodes.STATUS_CODE_BAD_REQUEST));
            errors.put(OrderManagementConstants.MESSAGE, errorMessage);
        });
        return errors;
    }

    /**
     * ValidationException - Handle exceptions.
     * @param ex
     * @return map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Map<String, String> handleValidationExceptions(
            ValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(OrderManagementConstants.STATUS_CODE,String.valueOf(HttpStatusCodes.STATUS_CODE_BAD_REQUEST));
        errors.put(OrderManagementConstants.MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * ServiceException - Handle exceptions.
     * @param ex
     * @return map
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public Map<String, String> handleServiceExceptions(
            Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(OrderManagementConstants.STATUS_CODE, String.valueOf(HttpStatusCodes.STATUS_CODE_SERVER_ERROR));
        errors.put(OrderManagementConstants.MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * ConstraintViolationException - Handle exceptions.
     * @param ex
     * @return map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleValidationExceptions(
            Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(OrderManagementConstants.STATUS_CODE, String.valueOf(HttpStatusCodes.STATUS_CODE_BAD_REQUEST));
        errors.put(OrderManagementConstants.MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Exception - Handle exceptions.
     * @param ex
     * @return map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleExceptions(
            Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(OrderManagementConstants.STATUS_CODE, String.valueOf(HttpStatusCodes.STATUS_CODE_BAD_REQUEST));
        errors.put(OrderManagementConstants.MESSAGE, ex.getMessage());
        return errors;
    }
}
