package com.fatema.sample.web.api.v1.dto.request;

import lombok.Data;

@Data
public class GetStatementRequestDto {

    private Integer accountId;
    private String fromDate;
    private String toDate;

    private String fromAmount;
    private String toAmount;

}
