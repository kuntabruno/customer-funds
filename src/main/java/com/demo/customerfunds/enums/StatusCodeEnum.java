package com.demo.customerfunds.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCodeEnum {
    Success("00"),
    Error("99");

    private final String code;
}
