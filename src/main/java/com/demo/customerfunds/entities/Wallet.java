package com.demo.customerfunds.entities;

import javax.persistence.Entity;

import com.demo.customerfunds.models.WalletModel;

@Entity
public class Wallet extends BaseEntity {

    private Long customerId;

    private String balance;

    public Wallet() {
        // Default constructor required by JPA
    }

    public Wallet(Long customerId, String balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", balance='" + balance + '\'' +
                '}';
    }

    public static Wallet fromModel(WalletModel model) {
        Wallet wallet = new Wallet();
        wallet.setCustomerId(model.getCustomerId());
        wallet.setBalance(model.getBalance());
        return wallet;
    }
}