package com.demo.customerfunds.services;

import org.springframework.stereotype.Service;

import com.demo.customerfunds.dtos.CreateWalletDto;
import com.demo.customerfunds.models.WalletModel;

@Service
public class WalletService {

    public WalletModel createCustomerWallet(CreateWalletDto dto) {
        return WalletModel.builder()
        .customerId(dto.getCustomerId())
        .build();
    }

    public WalletModel getCustomerWallet(Integer customerId) {
        return this.createCustomerWallet(new CreateWalletDto(customerId));
    }
    
}
