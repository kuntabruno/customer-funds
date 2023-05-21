package com.demo.customerfunds.dtos;

import lombok.Data;

@Data
public class CreateWalletDto {
    private Long customerId;

    public CreateWalletDto(Long customerId) {
      this.customerId = customerId;
    }
}
