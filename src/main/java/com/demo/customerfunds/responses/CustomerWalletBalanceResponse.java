package com.demo.customerfunds.responses;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustomerWalletBalanceResponse {
    private BigDecimal balance;

    public CustomerWalletBalanceResponse(BigDecimal balance) {
      this.balance = balance;
    }
}
