package com.dxvalley.michudw.michudw.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    private String bookingDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reference;
    private String description;
    private String narative;
    private String valueDate;
    private String debit;
    private String credit;
    private String debitAcctNo;
    private String to;
    private String bbf;
    private String closingBalance;

}