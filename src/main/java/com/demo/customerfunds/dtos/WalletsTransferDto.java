package com.demo.customerfunds.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WalletsTransferDto {
    private BigDecimal senderBalance;
    private BigDecimal recipientBalance;

    public WalletsTransferDto(BigDecimal senderBalance, BigDecimal recipientBalance) {
        this.senderBalance = senderBalance;
        this.recipientBalance = recipientBalance;
    }
}
