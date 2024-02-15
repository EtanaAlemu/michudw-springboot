package com.dxvalley.michudw.michudw.util;

import com.dxvalley.michudw.michudw.dto.TransactionResponse;
import com.dxvalley.michudw.michudw.model.Transaction;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
@AllArgsConstructor
public class TransactionMapper {

    @Autowired
    private ModelMapper modelMapper;

    public List<TransactionResponse> mapTransactionsToResponse(List<Transaction> transactions) {
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionResponses.add(mapTransactionToResponse(transaction));
        }
        return transactionResponses;
    }

    //    public TransactionResponse mapTransactionToResponse(Transaction transaction) {
//        return modelMapper.map(transaction, TransactionResponse.class);
//    }
    public TransactionResponse mapTransactionToResponse(Transaction transaction) {
        TransactionResponse response = modelMapper.map(transaction, TransactionResponse.class);
        response.setBookingDate(formatDate(transaction.getBookingDate())); // Mapping bookingDate to bookDate
        return response;
    }

    private String formatDate(String sDate) {
        LocalDate localDate = LocalDate.parse(sDate);
        // Format LocalDate to desired format "28 APR 22"
        return localDate.format(DateTimeFormatter.ofPattern("dd MMM yy", Locale.ENGLISH));
    }
}
