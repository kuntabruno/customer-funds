package com.demo.customerfunds.models;

import com.demo.customerfunds.entities.Transaction;
import com.demo.customerfunds.enums.TransactionTypeEnum;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class TransactionModel extends BaseModel {
    private Long walletId;
    private Long customerId;
    private String amount;
    private TransactionTypeEnum transactionType;

    public TransactionModel(Long walletId, Long customerId, String amount, TransactionTypeEnum transactionType) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public static TransactionModel fromEntity(Transaction entity) {
        TransactionModel model = TransactionModel.builder()
                .walletId(entity.getWalletId())
                .customerId(entity.getCustomerId())
                .amount(entity.getAmount())
                .transactionType(entity.getTransactionType())
                .build();
        model.setCreatedAt(entity.getCreatedAt());
        model.setId(entity.getId());
        return model;
    }

    public Transaction toEntity() {
        Transaction transaction = new Transaction();
        transaction.setWalletId(this.getWalletId());
        transaction.setCustomerId(this.getCustomerId());
        transaction.setAmount(this.getAmount());
        transaction.setTransactionType(this.getTransactionType());
        return transaction;
    }

    // Default values
    public static TransactionModel getDefault() {
        return TransactionModel.builder()
                .amount("0.0")
                .build();
    }
}
