package com.demo.customerfunds.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerModel {
    @Builder.Default
    private int id = 1;
    private String name;

    public String getName() {
        return (this.name != null && !this.name.isEmpty()) ? this.name : "Customer " + this.id;
    }
}
