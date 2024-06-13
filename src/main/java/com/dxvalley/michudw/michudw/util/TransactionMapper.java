package com.dxvalley.michudw.michudw.util;

import com.dxvalley.michudw.michudw.dto.TransactionResponse;
import com.dxvalley.michudw.michudw.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class TransactionMapper {


    public List<TransactionResponse> mapTransactionsToResponse(List<Transaction> transactions) {
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionResponses.add(mapTransactionToResponse(transaction));
        }
        return transactionResponses;
    }

    public TransactionResponse mapTransactionToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setBookDate(formatDate(transaction.getBookdate()));
        response.setReference(transaction.getReference());
        response.setDescript(transaction.getDescription());
        response.setNarrative(transaction.getDescription());
        response.setValueDate(formatDate(transaction.getValuedate()));


        try {
            float amount = Float.parseFloat(transaction.getAmount());

            if (amount >= 0) {
                response.setDebit(null);
                response.setCredit(transaction.getAmount());
            } else {
                response.setDebit(transaction.getAmount());
                response.setCredit(null);
            }
        } catch (Exception e) {
            throw e;
        }
        response.setFrom(transaction.getDrAccNo());
        response.setTo(transaction.getCrAccNo());
        response.setClosingBalance(transaction.getCbal());
        return response;
    }

    private String formatDate(String sDate) {
        LocalDateTime dateTime = LocalDateTime.parse(sDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Convert LocalDateTime to LocalDate
        LocalDate localDate = dateTime.toLocalDate();
        // Format LocalDate to desired format "28 APR 22"
        return localDate.format(DateTimeFormatter.ofPattern("dd MMM yy", Locale.ENGLISH));
    }
}