package uk.co.joecastle.msscbeerservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getConstraintViolations()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList()));
    }

}