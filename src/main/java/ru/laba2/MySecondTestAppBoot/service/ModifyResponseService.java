package ru.laba2.MySecondTestAppBoot.service;

import org.springframework.stereotype.Service;
import ru.laba2.MySecondTestAppBoot.model.Response;

@Service
public interface ModifyResponseService {

    Response modify (Response response);

}
