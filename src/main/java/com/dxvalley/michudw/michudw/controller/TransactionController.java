package com.dxvalley.michudw.michudw.controller;

import com.dxvalley.michudw.michudw.dto.TransactionRequest;
import com.dxvalley.michudw.michudw.dto.TransactionResponse;
import com.dxvalley.michudw.michudw.model.Transaction;
import com.dxvalley.michudw.michudw.repository.TransactionRepository;
import com.dxvalley.michudw.michudw.util.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @PostMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@RequestBody TransactionRequest request) {
        List<Transaction> transactions = transactionRepository.findTransactions(
                request.getStartDate(), request.getEndDate(), request.getAccountNumber());
        System.out.println(transactions.get(0));
        List<TransactionResponse> response = transactionMapper.mapTransactionsToResponse(transactions);
        System.out.println(response.get(0));

        return ResponseEntity.ok(response);
    }

}