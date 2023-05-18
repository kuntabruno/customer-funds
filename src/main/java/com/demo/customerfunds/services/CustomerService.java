package com.demo.customerfunds.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.customerfunds.dtos.CreateTransactionDto;
import com.demo.customerfunds.dtos.CreateWalletDto;
import com.demo.customerfunds.dtos.WalletsTransferDto;
import com.demo.customerfunds.enums.TransactionTypeEnum;
import com.demo.customerfunds.exceptions.InsufficientBalanceException;
import com.demo.customerfunds.models.CustomerModel;
import com.demo.customerfunds.models.WalletModel;
import com.demo.customerfunds.requests.CreateCustomerRequest;
import com.demo.customerfunds.requests.CustomerDepositRequest;
import com.demo.customerfunds.requests.CustomerTransferRequest;
import com.demo.customerfunds.requests.CustomerWalletBalanceRequest;
import com.demo.customerfunds.responses.CustomerDepositResponse;
import com.demo.customerfunds.responses.CustomerTransferResponse;
import com.demo.customerfunds.responses.CustomerWalletBalanceResponse;
import com.demo.customerfunds.utils.AmountUtils;

@Service
public class CustomerService {

    @Autowired
    WalletService walletSvc;

    @Autowired
    TransactionService transactionSvc;

    @Autowired
    AmountUtils amountUtils;

    public CustomerModel createCustomer(CreateCustomerRequest customerRequest) {
        CustomerModel customer = CustomerModel.builder()
                .name(customerRequest.getName())
                .build();
        // Create Customer Wallet
        walletSvc.createCustomerWallet(new CreateWalletDto(customer.getId()));
        return customer;
    }

    public CustomerModel getCustomer(Integer customerId) {
        CreateCustomerRequest request = new CreateCustomerRequest("Customer");
        CustomerModel customer = createCustomer(request);
        customer.setId(customerId);

        return customer;
    }

    public CustomerDepositResponse depositCustomerFunds(CustomerDepositRequest depositReq) {
        CustomerModel customer = this.getCustomer(depositReq.getCustomerId());
        WalletModel wallet = walletSvc.getCustomerWallet(customer.getId());
        WalletModel updatedWallet = atomicallyAddAmountToWallet(wallet, depositReq.getAmount());

        return new CustomerDepositResponse(AmountUtils.stringToBigDecimal(updatedWallet.getBalance()));
    }

    public CustomerTransferResponse transferCustomerFunds(CustomerTransferRequest transferReq) throws InsufficientBalanceException {
        WalletModel senderWallet = walletSvc.getCustomerWallet(transferReq.getFromCustomerId());
        senderWallet.setBalance(new BigDecimal(500).toString());
        WalletModel recipientWallet = walletSvc.getCustomerWallet(transferReq.getToCustomerId());
        BigDecimal amountToTransfer = transferReq.getAmount();

        // Check if sender has enough balance in wallet
        BigDecimal senderBalance = AmountUtils.stringToBigDecimal(senderWallet.getBalance());
        if (senderBalance.compareTo(amountToTransfer) < 0) {
            // Sender doesn't have enough balance
            throw new InsufficientBalanceException("Sender wallet does not have enough balance to complete the transaction");
        }

        WalletsTransferDto transferDto = this.atomicallyTransferBetweenTwoAccounts(senderWallet, recipientWallet,
                amountToTransfer);

        return new CustomerTransferResponse(transferDto.getSenderBalance(), transferDto.getRecipientBalance());
    }

    public CustomerWalletBalanceResponse getCustomerWalletBalance(CustomerWalletBalanceRequest req) {
        Integer customerId = req.getCustomerId();
        WalletModel wallet = walletSvc.getCustomerWallet(customerId);
        int setAmount = customerId == null ? 0 : customerId == 2 ? 200 : customerId > 2 ? 350 : 100;
        wallet.setBalance(new BigDecimal(setAmount).toString());
        return new CustomerWalletBalanceResponse(AmountUtils.stringToBigDecimal(wallet.getBalance()));
    }

    private WalletModel atomicallyAddAmountToWallet(WalletModel wallet, BigDecimal amountToAdd) {
        BigDecimal amount = AmountUtils.stringToBigDecimal(wallet.getBalance());
        BigDecimal total = amount.add(amountToAdd);
        // Enter db Atomic Mode
        wallet.setBalance(AmountUtils.bigDecimalToString(total));
        transactionSvc.createTransaction(new CreateTransactionDto(null, wallet.getId(), wallet.getCustomerId(),
                AmountUtils.bigDecimalToString(amountToAdd), TransactionTypeEnum.Debited.getType()));
        // Save all to db here
        // Exit db Atomic Mode
        return wallet;
    }

    private WalletModel atomicallyReduceAmountFromWallet(WalletModel wallet, BigDecimal amountToReduce) {
        BigDecimal amount = AmountUtils.stringToBigDecimal(wallet.getBalance());
        BigDecimal total = amount.subtract(amountToReduce);
        // Enter db Atomic Mode
        wallet.setBalance(AmountUtils.bigDecimalToString(total));
        transactionSvc.createTransaction(new CreateTransactionDto(null, wallet.getId(), wallet.getCustomerId(),
                AmountUtils.bigDecimalToString(amountToReduce), TransactionTypeEnum.Credited.getType()));
        // Save all to db here

        // Exit db Atomic Mode
        return wallet;
    }

    private WalletsTransferDto atomicallyTransferBetweenTwoAccounts(WalletModel senderWallet,
            WalletModel recipientWallet, BigDecimal transferredAmount) {
        // Sender reduce wallet balance
        BigDecimal senderBalance = AmountUtils.stringToBigDecimal(senderWallet.getBalance());
        senderWallet.setBalance(AmountUtils.bigDecimalToString(senderBalance.subtract(transferredAmount)));
        transactionSvc
                .createTransaction(new CreateTransactionDto(null, senderWallet.getId(), senderWallet.getCustomerId(),
                        AmountUtils.bigDecimalToString(transferredAmount), TransactionTypeEnum.Credited.getType()));

        // Recipient add to wallet balance
        BigDecimal recipientBalance = AmountUtils.stringToBigDecimal(recipientWallet.getBalance());
        recipientWallet.setBalance(AmountUtils.bigDecimalToString(recipientBalance.add(transferredAmount)));
        transactionSvc
                .createTransaction(new CreateTransactionDto(null, senderWallet.getId(), senderWallet.getCustomerId(),
                        AmountUtils.bigDecimalToString(transferredAmount), TransactionTypeEnum.Debited.getType()));

        // Enter db Atomic Mode
        // Save all to db here

        // Exit db Atomic Mode
        return new WalletsTransferDto(AmountUtils.stringToBigDecimal(senderWallet.getBalance()),
                AmountUtils.stringToBigDecimal(recipientWallet.getBalance()));

    }
}
