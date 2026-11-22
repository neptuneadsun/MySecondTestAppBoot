package ru.laba2.MySecondTestAppBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ru.laba2.MySecondTestAppBoot.exception.ValidationFailedException;
import ru.laba2.MySecondTestAppBoot.model.*;
import ru.laba2.MySecondTestAppBoot.service.*;
import ru.laba2.MySecondTestAppBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifySourceRequestService; 
    private final AnnualBonusService annualBonusService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        @Qualifier("ModifySourceRequestService") ModifyRequestService modifySourceRequestService,
                        AnnualBonusService annualBonusService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifySourceRequestService = modifySourceRequestService;
        this.annualBonusService = annualBonusService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) throws ValidationFailedException {
        
        log.info("request: {}", request);

        // Валидация вынесена в отдельный сервис, который кинет исключение при ошибке
        validationService.isValid(bindingResult);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        double bonus = annualBonusService.calculate(
                request.getPosition(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays()
        );
        response.setAnnualBonus(bonus);

        request.setSendTime(System.currentTimeMillis());
        modifySourceRequestService.modify(request); 
        modifyResponseService.modify(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}