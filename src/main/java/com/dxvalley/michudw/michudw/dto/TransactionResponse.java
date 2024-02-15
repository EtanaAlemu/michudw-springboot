package com.dxvalley.michudw.michudw.dto;

import lombok.Data;

@Data
public class TransactionResponse {
    private String bookingDate;
    private String txnAccount;
    private String reference;
    private String description;
    private String narative;
    private String valueDate;
    private String debit;
    private String credit;
    private String fromacct;
    private String toacct;
    private String bbf;
    private String closingBalance;
}