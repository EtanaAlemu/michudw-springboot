package com.dxvalley.michudw.michudw.dto;

import lombok.Data;

import java.util.List;
@Data
public class TransactionListResponse {
    private String startDate;
    private String endDate;
    private String accountNumber;
    private int transactionsCount;
    private List<TransactionResponse> transactions;
}
