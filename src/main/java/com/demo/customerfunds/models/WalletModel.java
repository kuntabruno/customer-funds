package com.demo.customerfunds.models;

import com.demo.customerfunds.entities.Wallet;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class WalletModel extends BaseModel {
    private Long customerId;

    @Builder.Default
    private String balance = "0.0";

    public static WalletModel fromEntity(Wallet entity) {
        WalletModel model = WalletModel.builder()
                .customerId(entity.getCustomerId())
                .balance(entity.getBalance())
                .build();
        model.setCreatedAt(entity.getCreatedAt());
        model.setId(entity.getId());
        return model;
    }

    public Wallet toEntity() {
        Wallet wallet = new Wallet();
        wallet.setCustomerId(this.getCustomerId());
        wallet.setBalance(this.getBalance());
        return wallet;
    }
}
