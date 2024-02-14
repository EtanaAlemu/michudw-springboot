package com.dxvalley.michudw.michudw.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    private String bookingDate;
    private String txnAccount;
    @EmbeddedId
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