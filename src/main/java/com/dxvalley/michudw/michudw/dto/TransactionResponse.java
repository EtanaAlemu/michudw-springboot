package com.dxvalley.michudw.michudw.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate.format(DateTimeFormatter.ofPattern("dd MMM yy"));
    }
}