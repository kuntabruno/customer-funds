package com.demo.customerfunds.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.customerfunds.dtos.CreateTransactionDto;
import com.demo.customerfunds.entities.Transaction;
import com.demo.customerfunds.models.TransactionModel;
import com.demo.customerfunds.repositories.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionModel createTransaction(CreateTransactionDto dto) {
        Transaction transaction = Transaction.fromModel(dto.toModel());
        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionModel.fromEntity(savedTransaction);
    }
}