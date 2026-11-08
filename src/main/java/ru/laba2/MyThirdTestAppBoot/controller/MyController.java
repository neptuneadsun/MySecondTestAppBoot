package ru.laba2.MyThirdTestAppBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.laba2.MyThirdTestAppBoot.exception.ValidationFailedException;
import ru.laba2.MyThirdTestAppBoot.model.Request;
import ru.laba2.MyThirdTestAppBoot.model.Response;
import ru.laba2.MyThirdTestAppBoot.model.Codes;
import ru.laba2.MyThirdTestAppBoot.model.ErrorCodes;
import ru.laba2.MyThirdTestAppBoot.model.ErrorMessages;
import ru.laba2.MyThirdTestAppBoot.service.ModifyResponseService;
import ru.laba2.MyThirdTestAppBoot.service.ValidationService;
import ru.laba2.MyThirdTestAppBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid  @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("request: {}", request);

        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - request.getSendTime();
        log.info("Время от нажатия Send в Postman до получения запроса Сервисом 2: {} мс", timeDifference);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        try {
            log.info("Начало валидации запроса");
            validationService.isValid(bindingResult);
            log.info("Валидация пройдена успешно");

        } catch (ValidationFailedException e) {
            log.error("Выброшено исключение ValidationFailedException: {}", e.getMessage());

            if (bindingResult.hasErrors()) {
                bindingResult.getFieldErrors().forEach(error -> 
                    log.error("Ошибка в поле '{}': отклоненное значение '{}', сообщение: '{}'", 
                            error.getField(), 
                            error.getRejectedValue(), 
                            error.getDefaultMessage())
                );
            }

            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("Выброшено непредвиденное исключение: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Ответ до модификации: {}", response);
        Response modifiedResponse = modifyResponseService.modify(response);
        log.info("Ответ после модификации: {}", modifiedResponse);

        return new ResponseEntity<>(modifiedResponse, HttpStatus.OK);
    }
}