package com.demo.customerfunds.requests;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerTransferRequest {
    @JsonProperty(required = true)
    @Min(value = 1, message = "From CustomerId is required")
    private int fromCustomerId;

    @JsonProperty(required = true)
    @Min(value = 1, message = "To CustomerId is required")
    private int toCustomerId;

    @JsonProperty(required = true)
    @NotNull(message = "Amount is required")
    @Min(value = 10, message = "Amount must be at least 10")
    private BigDecimal amount;

    public CustomerTransferRequest () {
        // Default constructor is required for deserialization
    }
}
