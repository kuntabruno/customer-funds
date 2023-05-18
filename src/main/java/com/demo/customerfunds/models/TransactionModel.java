package com.demo.customerfunds.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionModel {

    private int id;
    private int walletId;
    private int customerId;
    private String amount;
    private String transactionType;

    public TransactionModel(Integer id, Integer walletId, Integer customerId, String amount, String transactionType) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    // Default values
    public static TransactionModel getDefault() {
        return TransactionModel.builder()
                .id(1)
                .amount("0.0")
                .build();
    }
}
