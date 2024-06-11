package com.dxvalley.michudw.michudw.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    private String bookdate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reference;
    private String description;
    private String valuedate;
    private String obal;
    private String amount;
    private String cbal;
    private String date_time;
    private String curr;
    private String fcyamt;
    private String rate;
    private String ttime;
    private String recid;
    private String dtime;
    private String drAccNo;
    private String crAccNo;
    private String creditCompanyCode;
    private String debitCompanyCode;
    private String drAccountName;
    private String crAccountName;
    private String debitBrName;
    private String creditBrName;

}