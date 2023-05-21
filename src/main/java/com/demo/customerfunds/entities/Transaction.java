package com.demo.customerfunds.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.demo.customerfunds.enums.TransactionTypeEnum;
import com.demo.customerfunds.models.TransactionModel;

@Entity
public class Transaction extends BaseEntity {

    @NotNull
    private Long walletId;

    @NotNull
    private Long customerId;

    @NotBlank
    private String amount;

    @NotBlank
    private String reference;

    @NotNull
    private TransactionTypeEnum transactionType;

    public Transaction() {
        // Default constructor required by JPA
    }

    public Transaction(Long walletId, Long customerId, String amount, TransactionTypeEnum transactionType) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.reference = Transaction.generateRef(customerId, walletId);
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", walletId=" + walletId +
                ", customerId=" + customerId +
                ", amount='" + amount + '\'' +
                ", transactionType=" + transactionType +
                ", createdAt=" + createdAt +
                '}';
    }

    public static Transaction fromModel(TransactionModel model) {
        Transaction transaction = new Transaction();
        transaction.setWalletId(model.getWalletId());
        transaction.setCustomerId(model.getCustomerId());
        transaction.setAmount(model.getAmount());
        transaction.setTransactionType(model.getTransactionType());
        return transaction;
    }

    public static String generateRef(Long customerId, Long walletId) {
        return customerId + "-" + walletId;
    }
    
}
