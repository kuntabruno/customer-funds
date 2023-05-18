package com.demo.customerfunds.services;

import org.springframework.stereotype.Service;

import com.demo.customerfunds.dtos.CreateTransactionDto;
import com.demo.customerfunds.models.TransactionModel;

@Service
public class TransactionService {
    
    public TransactionModel createTransaction(CreateTransactionDto dto) {
        TransactionModel transactionModel = TransactionModel.getDefault();
        Integer id = dto.getId();
        if (id != null) {
            transactionModel.setId(id);
        }
        transactionModel.setAmount(dto.getAmount());
        transactionModel.setCustomerId(dto.getCustomerId());
        transactionModel.setWalletId(dto.getWalletId());
        return transactionModel;
    }
}
