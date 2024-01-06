package com.stackunderflow.backend.Exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyException(AlreadyExistsException exception){
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRegisterOrLoginException.class)
    public ResponseEntity<Object> handleBadRegisterOrLogin(BadRegisterOrLoginException exception){
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ObjectNotFound.class)
    public ResponseEntity<Object> handleObjectNotFound(ObjectNotFound exception){
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> exceptions = exception.getConstraintViolations();
        return new ResponseEntity<>(new CustomErrorResponse(exceptions.iterator().next().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordValidationException.class)
    public ResponseEntity<Object> handlePasswordConstraintViolation(PasswordValidationException exception){
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException exception){
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwt(ExpiredJwtException exception){
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<Object> handleForbiddenAction(ForbiddenActionException exception){
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.FORBIDDEN);
    }
}
