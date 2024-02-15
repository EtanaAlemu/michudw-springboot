package com.dxvalley.michudw.michudw.dto;

import lombok.Data;

import java.util.Date;
@Data
public class TransactionRequest {
    private String startDate;
    private String endDate;
    private Long accountNumber;
}
