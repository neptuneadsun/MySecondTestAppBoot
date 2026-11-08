package ru.laba2.MySecondTestAppBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.laba2.MySecondTestAppBoot.exception.ValidationFailedException;

@Service
public class RequestValudationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            throw new
                    ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }
}
