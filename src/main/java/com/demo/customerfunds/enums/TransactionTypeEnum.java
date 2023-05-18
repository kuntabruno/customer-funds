package com.demo.customerfunds.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionTypeEnum {
    Debited("Debited"),
    Credited("Credited");

    private final String type; 
}
