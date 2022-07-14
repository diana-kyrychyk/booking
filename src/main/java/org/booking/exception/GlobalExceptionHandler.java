package org.booking.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String ERROR_MESSAGE_TEMPLATE = "Exception: >%s<, exception message: >%s< %n requested uri: %s";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        final String path = request.getDescription(false);
        logger.error(String.format(ERROR_MESSAGE_TEMPLATE,
                exception.getClass().getSimpleName(), exception.getMessage(), path), exception);
        return ResponseEntity.internalServerError().body("Something was wrong");
    }
}
