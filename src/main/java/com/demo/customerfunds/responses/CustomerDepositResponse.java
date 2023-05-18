package com.demo.customerfunds.responses;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustomerDepositResponse {
    private BigDecimal balance;

    public CustomerDepositResponse(BigDecimal balance) {
        this.balance = balance;
    }
}
