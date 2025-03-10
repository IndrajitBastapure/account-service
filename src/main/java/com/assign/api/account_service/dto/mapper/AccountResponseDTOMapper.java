package com.assign.api.account_service.dto.mapper;

import com.assign.api.account_service.clientconfig.TransactionServiceClient;
import com.assign.api.account_service.dto.CustomerDTO;
import com.assign.api.account_service.dto.response.AccountResponseDTO;
import com.assign.api.account_service.dto.response.TransactionResponseDTO;
import com.assign.api.account_service.entity.AccountEntity;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class AccountResponseDTOMapper {
    private final TransactionServiceClient transactionServiceClient;

    public AccountResponseDTOMapper(TransactionServiceClient transactionServiceClient) {
        this.transactionServiceClient = transactionServiceClient;
    }
    public AccountResponseDTO mapToAccountResponseDTO(AccountEntity account){

        Set<TransactionResponseDTO> transactions = transactionServiceClient.getTransactionsByAccountId(account.getAccId());

        CustomerDTO customerDTO = CustomerDTO.builder()
                .custId(account.getCustomer().getCustId())
                .name(account.getCustomer().getName())
                .surname(account.getCustomer().getSurname())
                .build();

        return AccountResponseDTO.builder()
                .accountId(account.getAccId())
                .customerId(account.getCustomer().getCustId())
                .balance(account.getBalance())
                .customer(customerDTO)
                .transactions(transactions)
                .build();
    }
}
