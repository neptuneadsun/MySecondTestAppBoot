package ru.laba2.MySecondTestAppBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /**
     * Уникальный идентификатор сообщения
     */
    @NotBlank(message = "UID не может быть пустым")
    @Size(max = 32)
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    @NotBlank(message = "Operation UID не может быть пустым")
    @Size(max = 32)
    private String operationUid;

    /**
     * Имя системы отправителя
     */
    private Systems systemName;

    /**
     * Время создания сообщения
     */
    @NotBlank(message = "System Time не может быть пустым")
    private String systemTime;

    /**
     * Наименование ресурса
     */
    private String source;

    /**
     * Уникальный идентификатор коммуникации
     */
    @Min(1)
    @Max(100000)
    private Integer communicationId;

    /**
     * Уникальный идентификатор шаблона
     */
    private Integer templateId;

    /**
     * Код продукта
     */
    private Integer productCode;

    /**
     * Смс код
     */
    private Integer smsCode;
    
    
    private Positions position;
    private Double salary;
    private Double bonus;
    private Integer workDays;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName=" + systemName +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                ", position=" + position +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", workDays=" + workDays +
                '}';
    }

    private Long sendTime;
}