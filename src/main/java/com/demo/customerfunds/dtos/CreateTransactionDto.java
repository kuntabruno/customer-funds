package com.demo.customerfunds.dtos;

import java.util.concurrent.ThreadLocalRandom;

import lombok.Data;

@Data
public class CreateTransactionDto {
    private int id;
    private int walletId;
    private int customerId;
    private String amount;
    private String transactionType;
    
    public CreateTransactionDto(Integer id, Integer walletId, Integer customerId, String amount, String transactionType) {
       this.id = id != null ? id : ThreadLocalRandom.current().nextInt();
       this.walletId = walletId;
       this.customerId = customerId;
       this.amount = amount;
       this.transactionType = transactionType;
    }
}
