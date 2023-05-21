package com.demo.customerfunds.services;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.customerfunds.dtos.CreateWalletDto;
import com.demo.customerfunds.dtos.WalletsTransferDto;
import com.demo.customerfunds.entities.Customer;
import com.demo.customerfunds.entities.Transaction;
import com.demo.customerfunds.entities.Wallet;
import com.demo.customerfunds.enums.TransactionTypeEnum;
import com.demo.customerfunds.exceptions.InsufficientBalanceException;
import com.demo.customerfunds.models.CustomerModel;
import com.demo.customerfunds.repositories.CustomerRepository;
import com.demo.customerfunds.repositories.TransactionRepository;
import com.demo.customerfunds.repositories.WalletRepository;
import com.demo.customerfunds.requests.CreateCustomerRequest;
import com.demo.customerfunds.requests.CustomerDepositRequest;
import com.demo.customerfunds.requests.CustomerTransferRequest;
import com.demo.customerfunds.requests.CustomerWalletBalanceRequest;
import com.demo.customerfunds.responses.CreateCustomerResponse;
import com.demo.customerfunds.responses.CustomerDepositResponse;
import com.demo.customerfunds.responses.CustomerTransferResponse;
import com.demo.customerfunds.responses.CustomerWalletBalanceResponse;
import com.demo.customerfunds.utils.BalanceUtils;

@Service
public class CustomerService {

        private final CustomerRepository customerRepository;
        private final WalletRepository walletRepository;
        private final TransactionRepository transactionRepository;

        @Autowired
        WalletService walletSvc;

        @Autowired
        TransactionService transactionSvc;

        @Autowired
        BalanceUtils amountUtils;

        @Autowired
        public CustomerService(CustomerRepository customerRepository, WalletRepository walletRepository,
                        TransactionRepository transactionRepository) {
                this.customerRepository = customerRepository;
                this.walletRepository = walletRepository;
                this.transactionRepository = transactionRepository;
        }

        public CreateCustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
                Customer customer = new Customer(customerRequest.getName());
                customer = customerRepository.save(customer);

                // Create Customer Wallet
                walletSvc.createCustomerWallet(new CreateWalletDto(customer.getId()));

                CustomerModel customerModel = CustomerModel.fromEntity(customer);
                return CreateCustomerResponse.fromModel(customerModel);
        }

        public CustomerModel getCustomer(Integer customerId) {
                Customer customer = customerRepository.findById(customerId.longValue())
                                .orElseThrow(() -> new RuntimeException("Customer not found"));

                return CustomerModel.fromEntity(customer);
        }

        public CustomerDepositResponse depositCustomerFunds(CustomerDepositRequest depositReq) {
                Customer customer = customerRepository.findById(depositReq.getCustomerId().longValue())
                                .orElseThrow(() -> new RuntimeException("Customer not found"));

                Wallet wallet = walletRepository.findById(customer.getId())
                                .orElseThrow(() -> new RuntimeException("Wallet not found"));

                Wallet updatedWallet = atomicallyAddAmountToWallet(wallet, depositReq.getAmount());

                return new CustomerDepositResponse(BalanceUtils.stringToBigDecimal(updatedWallet.getBalance()));
        }

        public CustomerTransferResponse transferCustomerFunds(CustomerTransferRequest transferReq)
                        throws InsufficientBalanceException {
                Wallet senderWallet = walletRepository.findById(transferReq.getFromCustomerId())
                                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

                Wallet recipientWallet = walletRepository.findById(transferReq.getToCustomerId())
                                .orElseThrow(() -> new RuntimeException("Recipient wallet not found"));

                BigDecimal amountToTransfer = transferReq.getAmount();

                // Check if sender has enough balance in wallet
                BigDecimal senderBalance = BalanceUtils.stringToBigDecimal(senderWallet.getBalance());
                if (senderBalance.compareTo(amountToTransfer) < 0) {
                        // Sender doesn't have enough balance
                        throw new InsufficientBalanceException(
                                        "Sender wallet does not have enough balance to complete the transaction");
                }

                WalletsTransferDto transferDto = atomicallyTransferBetweenTwoAccounts(senderWallet, recipientWallet,
                                amountToTransfer);

                return new CustomerTransferResponse(transferDto.getSenderBalance(), transferDto.getRecipientBalance());
        }

        public CustomerWalletBalanceResponse getCustomerWalletBalance(CustomerWalletBalanceRequest req) {
                Long customerId = req.getCustomerId();
                Wallet wallet = walletRepository.findById(customerId)
                                .orElseThrow(() -> new RuntimeException("Wallet not found"));

                return new CustomerWalletBalanceResponse(BalanceUtils.stringToBigDecimal(wallet.getBalance()));
        }

        @Transactional
        private Wallet atomicallyAddAmountToWallet(Wallet wallet, BigDecimal amountToAdd) {
                BigDecimal amount = BalanceUtils.stringToBigDecimal(wallet.getBalance());
                BigDecimal total = amount.add(amountToAdd);
                wallet.setBalance(BalanceUtils.bigDecimalToString(total));
                Transaction transaction = new Transaction(wallet.getId(), wallet.getCustomerId(),
                                BalanceUtils.bigDecimalToString(amountToAdd), TransactionTypeEnum.DEBITED);
                transactionRepository.save(transaction);
                walletRepository.save(wallet);
                return wallet;
        }

        @Transactional
        private Wallet atomicallyReduceAmountFromWallet(Wallet wallet, BigDecimal amountToReduce) {
                BigDecimal amount = BalanceUtils.stringToBigDecimal(wallet.getBalance());
                BigDecimal total = amount.subtract(amountToReduce);
                wallet.setBalance(BalanceUtils.bigDecimalToString(total));
                Transaction transaction = new Transaction(wallet.getId(), wallet.getCustomerId(),
                                BalanceUtils.bigDecimalToString(amountToReduce), TransactionTypeEnum.CREDITED);
                transactionRepository.save(transaction);
                walletRepository.save(wallet);
                return wallet;
        }

        @Transactional
        private WalletsTransferDto atomicallyTransferBetweenTwoAccounts(Wallet senderWallet,
                        Wallet recipientWallet, BigDecimal transferredAmount) {
                // Sender reduce wallet balance
                BigDecimal senderBalance = BalanceUtils.stringToBigDecimal(senderWallet.getBalance());
                senderWallet.setBalance(BalanceUtils.bigDecimalToString(senderBalance.subtract(transferredAmount)));
                Transaction senderTransaction = new Transaction(senderWallet.getId(), senderWallet.getCustomerId(),
                                BalanceUtils.bigDecimalToString(transferredAmount), TransactionTypeEnum.CREDITED);

                // Recipient add to wallet balance
                BigDecimal recipientBalance = BalanceUtils.stringToBigDecimal(recipientWallet.getBalance());
                recipientWallet.setBalance(BalanceUtils.bigDecimalToString(recipientBalance.add(transferredAmount)));
                Transaction recipientTransaction = new Transaction(recipientWallet.getId(),
                                recipientWallet.getCustomerId(),
                                BalanceUtils.bigDecimalToString(transferredAmount), TransactionTypeEnum.DEBITED);

                // Save all to db
                // Sender
                walletRepository.save(senderWallet);
                transactionRepository.save(senderTransaction);

                // Recipient
                walletRepository.save(recipientWallet);
                transactionRepository.save(recipientTransaction);
                return new WalletsTransferDto(BalanceUtils.stringToBigDecimal(senderWallet.getBalance()),
                                BalanceUtils.stringToBigDecimal(recipientWallet.getBalance()));
        }
}
