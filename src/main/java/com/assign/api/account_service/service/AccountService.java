package com.assign.api.account_service.service;

import com.assign.api.account_service.clientconfig.TransactionServiceClient;
import com.assign.api.account_service.dto.mapper.AccountResponseDTOMapper;
import com.assign.api.account_service.dto.request.AccountRequest;
import com.assign.api.account_service.dto.request.TransactionRequest;
import com.assign.api.account_service.dto.response.AccountResponseDTO;
import com.assign.api.account_service.dto.response.AccountResponseListDTO;
import com.assign.api.account_service.dto.response.TransactionResponseDTO;
import com.assign.api.account_service.entity.AccountEntity;
import com.assign.api.account_service.entity.CustomerEntity;
import com.assign.api.account_service.exception.CustomerDoesNotExistException;
import com.assign.api.account_service.repository.AccountRepository;
import com.assign.api.account_service.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionServiceClient transactionServiceClient;
    private final AccountResponseDTOMapper accountResponseDTOMapper;
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, TransactionServiceClient transactionServiceClient, AccountResponseDTOMapper accountResponseDTOMapper) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionServiceClient = transactionServiceClient;
        this.accountResponseDTOMapper = accountResponseDTOMapper;
    }

    public AccountResponseDTO createAccount(AccountRequest accountRequest) {
        CustomerEntity customer = customerRepository.findById(accountRequest.getCustomerId()).orElseThrow(() -> new CustomerDoesNotExistException("Customer does not exist with custId : "+accountRequest.getCustomerId()));
        logger.info("Fetched CustomerEntity before save: {}", customer);
        AccountEntity account = AccountEntity.builder()
                .customer(customer)
                .balance(accountRequest.getInitialCredit())
                .build();

        customer.getAccounts().add(account);

        account = accountRepository.save(account);

        if (accountRequest.getInitialCredit() != null && accountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            TransactionRequest transactionRequest = new TransactionRequest(account.getAccId(), accountRequest.getInitialCredit());
            TransactionResponseDTO transactionResponse = transactionServiceClient.createTransaction(transactionRequest);
        }
        return accountResponseDTOMapper.mapToAccountResponseDTO(account);
    }

    public AccountResponseListDTO getAccountsByCustomerId(String customerId) {
        logger.info("Fetching all accounts for custId {}", customerId);
        List<AccountEntity> accounts = accountRepository.findByCustomer_CustId(customerId);

        List<AccountResponseDTO> accountResponseDTOs = accounts.stream()
                .map(accountResponseDTOMapper::mapToAccountResponseDTO)
                .collect(Collectors.toList());

        return  AccountResponseListDTO.builder()
                .transactions(accountResponseDTOs)
                .build();
    }
}
