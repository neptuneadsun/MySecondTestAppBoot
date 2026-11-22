package ru.laba2.MySecondTestAppBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    /**
     * Уникальный идентификатор сообщения
     */
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    private String operationUid;

    /**
     * Имя системы отправителя
     */
    private String systemTime;

    /**
     * Время создания сообщения
     */
    private Codes code;

    /**
     * Наименование ресурса
     */
    private ErrorCodes errorCode;

    /**
     * Сообщение об ошибке
     */
    private ErrorMessages errorMessage;


    private Double annualBonus;
}