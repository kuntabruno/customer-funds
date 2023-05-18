package com.demo.customerfunds.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    @JsonProperty(required = true)
    @NotBlank(message = "Name is required")
    private String name;

    public CreateCustomerRequest() {
        // Default constructor is required for deserialization
    }

    public CreateCustomerRequest(String name) {
        this.name = name;
    }
}
