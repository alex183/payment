package com.wirecard.payment.exception.handler;

import com.wirecard.payment.exception.handler.data.Error;
import com.wirecard.payment.http.data.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@ControllerAdvice
public class PaymentExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String UNKNOWN_ERROR = "Unknown error, please try again later.";

    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).reduce("", (s, s2) -> {
            return s.concat(s2).concat("; ");
        });

        return new ResponseEntity<>(ErrorResponse.builder()
                .userMessage(message)
                .developerMessage(ex.getLocalizedMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<Error> errors = bindingResult.getFieldErrors().stream().map((field) -> {
            return Error.builder().name(field.getField()).message(field.getDefaultMessage()).build();
        }).collect(Collectors.toList());
        String message = errors.stream().map((e) -> {
            return e.getName().concat(": ").concat(e.getMessage());
        }).reduce("", (s, s2) -> {
            return s.concat(s2).concat("; ");
        });
        return new ResponseEntity<>(ErrorResponse.builder()
                .userMessage("Validation failed")
                .developerMessage(message)
                .errors(errors)
                .build(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = null;
        if (nonNull(ex.getCause()) && nonNull(ex.getCause().getCause()) && ex.getCause().getCause().getClass().equals(ValidationException.class)) {
            message = ex.getCause().getCause().getMessage();
        } else {
            message = ex.getLocalizedMessage();
        }

        return new ResponseEntity<>(ErrorResponse.builder()
                .userMessage("Validation Failed")
                .developerMessage(message)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .userMessage(UNKNOWN_ERROR)
                .developerMessage(nonNull(ex.getLocalizedMessage())?ex.getLocalizedMessage():ex.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

