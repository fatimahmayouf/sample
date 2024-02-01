package com.fatema.sample.web.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class StatementRequestDto {

    @DateTimeFormat
    @NotBlank(message = "Date can not be blank")
    private String dateField;

    @NotBlank(message = "Amount can not be blank")
    private String amount; // should be double

    @NotBlank(message = "Account id can not be blank")
    private Long accountId;

}
