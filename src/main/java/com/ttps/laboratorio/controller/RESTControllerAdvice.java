package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.ExceptionDTO;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RESTControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> handleException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String,String> errors = new HashMap<>();
        for (ObjectError e : methodArgumentNotValidException.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<?> accessDeniedException(AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ExceptionDTO() {{
                    this.setName(e.getClass().getSimpleName());
                    this.setMessage(e.getMessage());
                }});
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO() {{
                    this.setName(e.getClass().getSimpleName());
                    this.setMessage(e.getMessage());
                }});
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<?> generalExceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(
                new ExceptionDTO() {{
                    this.setName(e.getClass().getSimpleName());
                    this.setMessage(e.getMessage());
                }});
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<?> generalNotFoundExceptionHandler(NotFoundException e) {
        e.printStackTrace();
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO() {{
            this.setName(e.getClass().getSimpleName());
            this.setMessage(e.getMessage());
        }});
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<?> generalBadRequestExceptionHandler(BadRequestException e) {
      e.printStackTrace();
      return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO() {{
        this.setName(e.getClass().getSimpleName());
        this.setMessage(e.getMessage());
      }});
    }

}
