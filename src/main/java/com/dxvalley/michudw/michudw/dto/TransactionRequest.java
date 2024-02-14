package com.dxvalley.michudw.michudw.dto;

import lombok.Data;

import java.util.Date;
@Data
public class TransactionRequest {
    private Date startDate;
    private Date endDate;
    private String accountNumber;
}
