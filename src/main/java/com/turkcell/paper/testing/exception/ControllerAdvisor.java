package com.turkcell.paper.testing.exception;

import com.turkcell.paper.testing.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception, WebRequest req) {
        Map<String, Object> body = ResponseUtil.getObjectMap(exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleInvalidCustomerRequestException(InvalidCustomerRequestException ex, WebRequest req) {
        Map<String, Object> body = ResponseUtil.getObjectMap(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
