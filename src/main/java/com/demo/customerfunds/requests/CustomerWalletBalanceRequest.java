package com.demo.customerfunds.requests;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerWalletBalanceRequest {
    @JsonProperty(required = true)
    @Min(value = 1, message = "CustomerId is required")
    private int customerId;

    public CustomerWalletBalanceRequest () {
        // Default constructor is required for deserialization
    }

    public CustomerWalletBalanceRequest(int customerId) {
        this.customerId = customerId;
    }
}
