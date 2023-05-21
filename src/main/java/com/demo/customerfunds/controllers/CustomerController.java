package com.demo.customerfunds.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.customerfunds.exceptions.InsufficientBalanceException;
import com.demo.customerfunds.models.CustomerModel;
import com.demo.customerfunds.requests.CreateCustomerRequest;
import com.demo.customerfunds.requests.CustomerDepositRequest;
import com.demo.customerfunds.requests.CustomerTransferRequest;
import com.demo.customerfunds.requests.CustomerWalletBalanceRequest;
import com.demo.customerfunds.responses.CreateCustomerResponse;
import com.demo.customerfunds.responses.CustomerDepositResponse;
import com.demo.customerfunds.responses.CustomerTransferResponse;
import com.demo.customerfunds.responses.CustomerWalletBalanceResponse;
import com.demo.customerfunds.responses.ErrorResponse;
import com.demo.customerfunds.responses.GeneralResponse;
import com.demo.customerfunds.responses.SuccessResponse;
import com.demo.customerfunds.services.CustomerService;

@RestController
@RequestMapping(path = "${apiPrefix}/customers/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * @param request CreateCustomerRequest
     * @return SuccessResponse<CustomerModel>
     */
    @PostMapping(value = "createCustomer")
    public SuccessResponse<CreateCustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CreateCustomerResponse data = customerService.createCustomer(request);
        return new SuccessResponse<CreateCustomerResponse>(data);
    }

    /**
     * @param request CustomerDepositRequest
     * @return SuccessResponse<CustomerDepositResponse>
     */
    @PostMapping(value = "customerDepositFunds")
    public SuccessResponse<CustomerDepositResponse> customerDeposit(
            @Valid @RequestBody CustomerDepositRequest request) {
        CustomerDepositResponse data = customerService.depositCustomerFunds(request);
        return new SuccessResponse<CustomerDepositResponse>(data);
    }

    /**
     * @param request CustomerTransferRequest
     * @return SuccessResponse<CustomerTransferResponse>
     */
    @PostMapping(value = "transferFunds")
    public GeneralResponse<?> customerTransfer(@Valid @RequestBody CustomerTransferRequest request)
            throws InsufficientBalanceException {
        try {
            CustomerTransferResponse data = customerService.transferCustomerFunds(request);
            return new SuccessResponse<CustomerTransferResponse>(data);
        } catch (InsufficientBalanceException e) {
            String errorMessage = e.getMessage();
            return new ErrorResponse(errorMessage);
        } catch (Exception e) {
            String errorMessage = "An error occurred";
            return new ErrorResponse(errorMessage);
        }
    }

    /**
     * @param customerId String
     * @return SuccessResponse<CustomerWalletBalanceResponse>
     */
    @GetMapping(value = "customerWalletBalance")
    public SuccessResponse<CustomerWalletBalanceResponse> customerWalletBalance(
          @Valid @RequestParam("customerId") @Min(value = 1, message = "CustomerId is required") Long customerId) {
        CustomerWalletBalanceRequest request = new CustomerWalletBalanceRequest(customerId);
        CustomerWalletBalanceResponse data = customerService.getCustomerWalletBalance(request);
        return new SuccessResponse<CustomerWalletBalanceResponse>(data);
    }

}
