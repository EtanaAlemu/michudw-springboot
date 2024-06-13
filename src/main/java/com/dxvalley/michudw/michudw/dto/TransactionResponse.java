package com.dxvalley.michudw.michudw.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String bookDate;
    private String reference;
    private String descript;
    private String narrative;
    private String valueDate;
    private String debit;
    private String credit;
    private String from;
    private String to;
    private String closingBalance;
}