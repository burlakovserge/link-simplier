package ru.burlakov.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleException(ElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> illegal(IllegalStateException ex) {
//        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(Exception ex) {
        ElementException exception = new ElementException(ex.getMessage());
//        log.error(ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
