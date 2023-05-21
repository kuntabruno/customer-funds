package com.demo.customerfunds.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.customerfunds.dtos.CreateWalletDto;
import com.demo.customerfunds.entities.Wallet;
import com.demo.customerfunds.models.WalletModel;
import com.demo.customerfunds.repositories.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletModel createCustomerWallet(CreateWalletDto dto) {
        Wallet wallet = new Wallet(dto.getCustomerId(), "0.0");
        Wallet savedWallet = walletRepository.save(wallet);
        return WalletModel.fromEntity(savedWallet);
    }

    public WalletModel getCustomerWallet(Long customerId) {
        return walletRepository.findByCustomerId(customerId)
                .map(WalletModel::fromEntity)
                .orElse(null);
    }
}
