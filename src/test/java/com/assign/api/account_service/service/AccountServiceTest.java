package com.assign.api.account_service.service;

import com.assign.api.account_service.clientconfig.TransactionServiceClient;
import com.assign.api.account_service.dto.CustomerDTO;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionServiceClient transactionServiceClient;
    private AccountResponseDTOMapper accountResponseDTOMapper;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        customerRepository = mock(CustomerRepository.class);
        transactionServiceClient = mock(TransactionServiceClient.class);
        accountResponseDTOMapper = mock(AccountResponseDTOMapper.class);
        accountService = new AccountService(accountRepository, customerRepository, transactionServiceClient, accountResponseDTOMapper);
    }

    @Test
    void testCreateAccount_Success() {
        AccountRequest accountRequest = new AccountRequest("customer123", new BigDecimal("1000.00"));
        CustomerEntity mockCustomer = new CustomerEntity();
        mockCustomer.setCustId("customer123");
        mockCustomer.setAccounts(new ArrayList<>());

        AccountEntity mockAccount = AccountEntity.builder()
                .accId("account123")
                .customer(mockCustomer)
                .balance(new BigDecimal("1000.00"))
                .build();

        CustomerDTO customerDTO = new CustomerDTO("customer123", "Robert", "Keil");
        AccountResponseDTO mockResponseDTO = new AccountResponseDTO("account123", "customer123", new BigDecimal("1000.00"), customerDTO, Set.of());
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO("txn1","acc1",BigDecimal.valueOf(100.00), LocalDateTime.now());
        Set<TransactionResponseDTO> mockTransactions = Set.of(new TransactionResponseDTO("txn1","acc1",BigDecimal.valueOf(100.00), LocalDateTime.now()));
        mockResponseDTO.setTransactions(mockTransactions);
        when(customerRepository.findById("customer123")).thenReturn(Optional.of(mockCustomer));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(mockAccount);
        when(transactionServiceClient.createTransaction(any(TransactionRequest.class)))
                .thenReturn(transactionResponseDTO);
        when(accountResponseDTOMapper.mapToAccountResponseDTO(mockAccount)).thenReturn(mockResponseDTO);

        AccountResponseDTO result = accountService.createAccount(accountRequest);

        verify(customerRepository, times(1)).findById("customer123");
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
        verify(transactionServiceClient, times(1)).createTransaction(any(TransactionRequest.class));
        verify(accountResponseDTOMapper, times(1)).mapToAccountResponseDTO(mockAccount);

        assertNotNull(result);
        assertNotNull(result.getTransactions());
        assertEquals(1, result.getTransactions().size());
        assertEquals("account123", result.getAccountId());
        assertEquals("customer123", result.getCustomerId());
        assertEquals(new BigDecimal("1000.00"), result.getBalance());
    }

    @Test
    void testCreateAccount_CustomerDoesNotExist() {
        AccountRequest accountRequest = new AccountRequest("customer123", new BigDecimal("1000.00"));

        when(customerRepository.findById("customer123")).thenReturn(Optional.empty());

        assertThrows(CustomerDoesNotExistException.class, () -> accountService.createAccount(accountRequest));

        verify(customerRepository, times(1)).findById("customer123");
        verify(accountRepository, never()).save(any(AccountEntity.class));
        verify(transactionServiceClient, never()).createTransaction(any(TransactionRequest.class));
    }

    @Test
    void testGetAccountsByCustomerId() {
        String customerId = "customer123";

        AccountEntity account1 = AccountEntity.builder()
                .accId("account1")
                .balance(new BigDecimal("1000.00"))
                .build();
        AccountEntity account2 = AccountEntity.builder()
                .accId("account2")
                .balance(new BigDecimal("2000.00"))
                .build();

        account1.setCustomer(new CustomerEntity());
        account2.setCustomer(new CustomerEntity());

        CustomerDTO customerDTO = new CustomerDTO("customer123", "Robert", "Keil");

        AccountResponseDTO dto1 = new AccountResponseDTO("account1", "customer123", new BigDecimal("1000.00"), customerDTO, Set.of());
        AccountResponseDTO dto2 = new AccountResponseDTO("account2", "customer123", new BigDecimal("2000.00"), customerDTO, Set.of());

        when(accountRepository.findByCustomer_CustId(customerId)).thenReturn(List.of(account1, account2));
        when(accountResponseDTOMapper.mapToAccountResponseDTO(account1)).thenReturn(dto1);
        when(accountResponseDTOMapper.mapToAccountResponseDTO(account2)).thenReturn(dto2);

        AccountResponseListDTO accountResponseListDTO = accountService.getAccountsByCustomerId(customerId);
        List<AccountResponseDTO> accounts = accountResponseListDTO.getTransactions();

        verify(accountRepository, times(1)).findByCustomer_CustId(customerId);
        verify(accountResponseDTOMapper, times(1)).mapToAccountResponseDTO(account1);
        verify(accountResponseDTOMapper, times(1)).mapToAccountResponseDTO(account2);

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals("account1", accounts.get(0).getAccountId());
        assertEquals("account2", accounts.get(1).getAccountId());
    }
}
