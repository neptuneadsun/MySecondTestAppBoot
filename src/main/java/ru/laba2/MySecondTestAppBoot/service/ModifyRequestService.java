package ru.laba2.MySecondTestAppBoot.service;

import org.springframework.stereotype.Service;
import ru.laba2.MySecondTestAppBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}