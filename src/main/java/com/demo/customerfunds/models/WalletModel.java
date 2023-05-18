package com.demo.customerfunds.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletModel {
    @Builder.Default
    private int id = 1;

    private int customerId;

    @Builder.Default
    private String balance = "0.0";
}
