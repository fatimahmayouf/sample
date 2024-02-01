package com.fatema.sample.web.api.v1.dto.response;

import lombok.Data;

@Data
public class AccountResponse {

    private Integer id;
    private String accountType;
    private String accountNumber;
}
