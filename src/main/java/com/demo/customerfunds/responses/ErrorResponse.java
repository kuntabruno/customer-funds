package com.demo.customerfunds.responses;

import java.util.Map;

import com.demo.customerfunds.enums.StatusCodeEnum;
import com.demo.customerfunds.enums.StatusMessageEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorResponse extends GeneralResponse<Object> {

    private boolean success = false;
    private String statusCode = StatusCodeEnum.Error.getCode();
    private String statusMessage;
    private Map<String, String> errors;

    public ErrorResponse(Map<String, String> errors) {
        super(false, StatusCodeEnum.Error, StatusMessageEnum.Error, null);
        setErrors(errors);
        this.statusMessage = StatusMessageEnum.Error.getMsg();
    }

    public ErrorResponse(String errorMessage) {
        super(false, StatusCodeEnum.Error, StatusMessageEnum.Error, null);
        setStatusMessage(errorMessage);
    }

    public ErrorResponse(StatusCodeEnum statusCode, String errorMessage) {
        super(false, statusCode, StatusMessageEnum.Error, null);
        this.statusMessage = StatusMessageEnum.Error.getMsg();
    }

    public ErrorResponse(StatusCodeEnum statusCode, StatusMessageEnum statusMessage, String errorMessage) {
        super(false, statusCode, statusMessage, null);
        this.statusMessage = statusMessage.getMsg();
    }
}