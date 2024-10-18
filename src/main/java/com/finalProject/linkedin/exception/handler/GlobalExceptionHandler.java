package com.finalProject.linkedin.exception.handler;


import com.finalProject.linkedin.dto.error.ErrorResp;
import com.finalProject.linkedin.exception.ExceptionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResp> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.debug("Route not found: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResp(ExceptionConstants.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
