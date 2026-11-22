package ru.laba2.MyThirdTestAppBoot.service;

import org.springframework.stereotype.Service;
import ru.laba2.MyThirdTestAppBoot.model.Response;

@Service
public interface ModifyResponseService {

    Response modify (Response response);

}
