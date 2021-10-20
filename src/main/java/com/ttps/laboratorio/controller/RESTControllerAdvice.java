package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.ExceptionDTO;
import com.ttps.laboratorio.exception.ApiError;
import com.ttps.laboratorio.exception.ApiException;
import com.ttps.laboratorio.exception.ForbiddenException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.exception.ProductListEmptyException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RESTControllerAdvice.class);

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

    @ExceptionHandler(value = { ApiException.class })
    protected ResponseEntity<ApiError> handleApiException(ApiException e) {
        Integer statusCode = e.getStatusCode();
        boolean expected = HttpStatus.INTERNAL_SERVER_ERROR.value() > statusCode;
        if (expected) {
            LOGGER.warn("Internal Api warn. Status Code: " + statusCode, e);
        } else {
            LOGGER.error("Internal Api error. Status Code: " + statusCode, e);
        }

        ApiError apiError = new ApiError(e.getCode(), e.getDescription(), statusCode);
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
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

    @ExceptionHandler(ProductListEmptyException.class)
    private ResponseEntity<?> productListEmptyExceptionHandler(ProductListEmptyException productListEmptyException) {
        return ResponseEntity.status(404).body(new ExceptionDTO() {{
            this.setName("Product list empty");
            this.setMessage(productListEmptyException.getMessage());
        }});
    }

    @ExceptionHandler(ForbiddenException.class)
    private ResponseEntity<?> forbiddenExceptionHandler(ForbiddenException forbiddenException) {
        return ResponseEntity.status(403).body(new ExceptionDTO() {{
            this.setName("Forbidden access");
            this.setMessage(forbiddenException.getMessage());
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


}
