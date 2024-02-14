package com.dxvalley.michudw.michudw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    // Getters and setters
}