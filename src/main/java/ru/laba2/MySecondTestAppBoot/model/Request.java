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

    @NotBlank
    @NotNull
    @Size(max = 32)
    private String uid;

    @NotNull
    @Size(max = 32)
    private String operationUid;


    private String systemName;

    @NotNull
    private String systemTime;


    private String source;

    @Min(1)
    @Max(100000)
    private int communicationId;


    private int templateId;


    private int productCode;


    private int smsCode;

}