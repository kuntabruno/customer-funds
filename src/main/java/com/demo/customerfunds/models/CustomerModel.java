package com.demo.customerfunds.models;

import com.demo.customerfunds.entities.Customer;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerModel extends BaseModel {
    private String name;

    public static CustomerModel fromEntity(Customer entity) {
        CustomerModel model = CustomerModel.builder()
                .name(entity.getName())
                .build();
        model.setCreatedAt(entity.getCreatedAt());
        model.setId(entity.getId());
        return model;
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setName(this.getName());
        return customer;
    }
}
