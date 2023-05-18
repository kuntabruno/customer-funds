package com.demo.customerfunds.dtos;

import lombok.Data;

@Data
public class CreateWalletDto {
    private int customerId;

    public CreateWalletDto(Integer customerId) {
      this.customerId = customerId;
    }
}
