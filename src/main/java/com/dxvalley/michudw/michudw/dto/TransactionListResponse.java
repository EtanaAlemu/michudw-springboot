package com.dxvalley.michudw.michudw.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionListResponse {
    private String startDate;
    private String endDate;
    private Long accountNumber;
    private int transactionsCount;
    private List<TransactionResponse> transactions;
}
