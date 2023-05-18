package com.demo.customerfunds.responses;

import com.demo.customerfunds.enums.StatusCodeEnum;
import com.demo.customerfunds.enums.StatusMessageEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SuccessResponse<T> extends GeneralResponse<T> {
   
   private boolean success = true;
   private String statusCode = StatusCodeEnum.Success.getCode();
   private String statusMessage;

   public SuccessResponse(T data) {
       super(true, StatusCodeEnum.Success, StatusMessageEnum.Success, data);
       this.statusMessage = StatusMessageEnum.Success.getMsg();
   }

   public SuccessResponse(String statusMessage, T data) {
    super(true, StatusCodeEnum.Success, StatusMessageEnum.Success, data);
    if (statusMessage != null && !statusMessage.isEmpty()) {
        this.statusMessage = statusMessage;
    }
}
   
   public SuccessResponse(StatusCodeEnum statusCode, StatusMessageEnum statusMessage, T data) {
       super(true, statusCode, statusMessage, data);
       this.statusMessage = StatusMessageEnum.Success.getMsg();
   }
   
   public SuccessResponse(boolean success, StatusCodeEnum statusCode, StatusMessageEnum statusMessage, T data) {
       super(success, statusCode, statusMessage, data);
       this.statusMessage = StatusMessageEnum.Success.getMsg();
   }
}