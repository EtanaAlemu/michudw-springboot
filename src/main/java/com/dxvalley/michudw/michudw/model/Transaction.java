package com.dxvalley.michudw.michudw.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    private String bookingDate;
    private String txnAccount;
    private String reference;
    private String description;
    private String narrative;
    private String valueDate;
    private String debit;
    private String credit;
    private String fromAcct;
    private String toAcct;
    private String bbf;
    private String closingBalance;

}