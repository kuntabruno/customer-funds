package com.demo.customerfunds.responses;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustomerTransferResponse {
    private BigDecimal senderBalance;
    private BigDecimal recipientBalance;

    public CustomerTransferResponse(BigDecimal senderBalance, BigDecimal recipientBalance) {
        this.senderBalance = senderBalance;
        this.recipientBalance = recipientBalance;
    }
}
