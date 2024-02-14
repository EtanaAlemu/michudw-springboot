package com.dxvalley.michudw.michudw.util;
import com.dxvalley.michudw.michudw.dto.TransactionResponse;
import com.dxvalley.michudw.michudw.model.Transaction;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

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

    public TransactionResponse mapTransactionToResponse(Transaction transaction) {
        return modelMapper.map(transaction, TransactionResponse.class);
    }
}
