package com.fatema.sample.web.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto implements Serializable {

    @NotBlank(message = "Account id can not be blank")
    private Integer id;

    @NotBlank(message = "Account Type can not be blank")
    private String accountType;

    @NotBlank(message = "Account Number can not be blank")
    @Length(min = 10, message = "Min Length is 10")
    private String accountNumber;
}
