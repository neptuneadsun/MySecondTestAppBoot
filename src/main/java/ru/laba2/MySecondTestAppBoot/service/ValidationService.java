package ru.laba2.MySecondTestAppBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.laba2.MySecondTestAppBoot.exception.ValidationFailedException;

@Service
public interface ValidationService {

    void isValid(BindingResult bindingResult) throws ValidationFailedException;

}
