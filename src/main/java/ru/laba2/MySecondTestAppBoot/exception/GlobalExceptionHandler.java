package ru.laba2.MySecondTestAppBoot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.laba2.MySecondTestAppBoot.model.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationException(MethodArgumentNotValidException ex) {
        Response response = Response.builder()
                .code(Codes.FAILED)
                .errorCode(ErrorCodes.VALIDATION_EXCEPTION)
                .errorMessage(ErrorMessages.VALIDATION)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<Response> handleCustomValidationException(ValidationFailedException ex) {
        Response response = Response.builder()
                .code(Codes.FAILED)
                .errorCode(ErrorCodes.VALIDATION_EXCEPTION)
                .errorMessage(ErrorMessages.VALIDATION)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleUnknownException(Exception ex) {
        Response response = Response.builder()
                .code(Codes.FAILED)
                .errorCode(ErrorCodes.UNKNOWN_EXCEPTION)
                .errorMessage(ErrorMessages.UNKNOWN)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}