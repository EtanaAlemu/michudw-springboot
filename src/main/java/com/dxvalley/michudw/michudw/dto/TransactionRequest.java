package com.dxvalley.michudw.michudw.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
public class TransactionRequest {
    private String startDate;
    private String endDate;
    private Long accountNumber;
}
