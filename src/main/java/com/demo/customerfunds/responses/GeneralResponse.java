package com.demo.customerfunds.responses;

import com.demo.customerfunds.enums.StatusCodeEnum;
import com.demo.customerfunds.enums.StatusMessageEnum;

import lombok.Data;

@Data
public class GeneralResponse<T> {
    private Boolean success;
    private String statusCode;
    private String statusMessage;
    private T data;

    public GeneralResponse(Boolean success, StatusCodeEnum statusCode, StatusMessageEnum statusMessage, T data) {
        this.success = success;
        this.statusCode = statusCode.getCode();
        this.statusMessage = statusMessage.getMsg();
        this.data = data;
    }
}
