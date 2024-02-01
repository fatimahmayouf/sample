package com.fatema.sample.web.api.v1.dto.response;

import lombok.Data;

@Data
public class StatementResponse {

    private Integer id;
    private String datefield;
    private String amount;
    private AccountResponse account;
}
