package com.demo.customerfunds.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.demo.customerfunds.models.CustomerModel;

@Entity
public class Customer extends BaseEntity {

    @NotNull(message = "Name is required")
    private String name;

    public Customer() {
        // Default constructor required by JPA
    }

    public Customer(String name) {
       this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Customer fromModel(CustomerModel model) {
        Customer customer = new Customer();
        customer.setName(model.getName());
        return customer;
    }

    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
