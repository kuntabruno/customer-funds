package com.demo.customerfunds.responses;

import com.demo.customerfunds.models.CustomerModel;

import lombok.Data;

@Data
public class CreateCustomerResponse {
    private Long id;
    private String name;

    public static CreateCustomerResponse fromModel(CustomerModel model) {
        CreateCustomerResponse response = new CreateCustomerResponse();
        response.setId(model.getId());
        response.setName(model.getName());
        return response;
    }
}
