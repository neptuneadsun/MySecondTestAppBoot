package ru.laba2.MySecondTestAppBoot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.laba2.MySecondTestAppBoot.exception.UnsupportedCodeException;
import ru.laba2.MySecondTestAppBoot.exception.ValidationFailedException;
import ru.laba2.MySecondTestAppBoot.model.Request;
import ru.laba2.MySecondTestAppBoot.model.Response;
import ru.laba2.MySecondTestAppBoot.service.ValidationService;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {
                                         
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    Response response = Response.builder()
            .uid(request.getUid())
            .operationUid(request.getOperationUid())
            .systemTime(simpleDateFormat.format(new Date()))
            .code("success")
            .errorCode("")
            .errorMessage("")
            .build();

    try {
        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException("Значение uid=123 не поддерживается");
        }

        validationService.isValid(bindingResult);

    } catch (ValidationFailedException e) {
        response.setCode("failed");
        response.setErrorCode("ValidationException");
        response.setErrorMessage("Ошибка валидации");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        
    } catch (UnsupportedCodeException e) { 
        response.setCode("failed");
        response.setErrorCode("UnsupportedCodeException");
        response.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        
    } catch (Exception e) {
        response.setCode("failed");
        response.setErrorCode("UnknownException");
        response.setErrorMessage("Произошла непредвиденная ошибка");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
}
}