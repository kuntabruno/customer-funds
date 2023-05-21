package com.demo.customerfunds.dtos;

import com.demo.customerfunds.enums.TransactionTypeEnum;
import com.demo.customerfunds.models.TransactionModel;

import lombok.Data;

@Data
public class CreateTransactionDto {
    private Long id;
    private Long walletId;
    private Long customerId;
    private String amount;
    private TransactionTypeEnum transactionType;
    
    public CreateTransactionDto(Long id, Long walletId, Long customerId, String amount, TransactionTypeEnum transactionType) {
       this.id = id;
       this.walletId = walletId;
       this.customerId = customerId;
       this.amount = amount;
       this.transactionType = transactionType;
    }

    public TransactionModel toModel() {
        return TransactionModel.builder()
                .walletId(walletId)
                .customerId(customerId)
                .amount(amount)
                .transactionType(transactionType)
                .build();
    }
}
