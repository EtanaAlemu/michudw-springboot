package com.dxvalley.michudw.michudw.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class TransactionResponse {
//    private String bookDate;
//    private String reference;
//    private String descript;
//    private String narrative;
//    private String valueDate;
//    private String debit;
//    private String credit;
//    private String from;
//    private String to;
//    private String closingBalance;

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