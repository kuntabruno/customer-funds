package com.demo.customerfunds.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusMessageEnum {
    Success("Success"),
    Error("An error occurred");

    private final String msg; 
}
