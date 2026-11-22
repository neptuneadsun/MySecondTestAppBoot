package ru.laba2.MyThirdTestAppBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.laba2.MyThirdTestAppBoot.exception.ValidationFailedException;

@Service
public interface ValidationService {

    void isValid(BindingResult bindingResult) throws ValidationFailedException;

}
