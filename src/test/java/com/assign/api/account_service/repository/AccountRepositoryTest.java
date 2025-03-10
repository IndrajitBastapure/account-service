package com.assign.api.account_service.repository;

import com.assign.api.account_service.entity.AccountEntity;
import com.assign.api.account_service.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryTest {
    @Mock
    private AccountRepository accountRepository;
    @Test
    void testFindByCustomer_CustId() {

        CustomerEntity mockCustomer = new CustomerEntity();
        mockCustomer.setCustId("customer123");
        mockCustomer.setAccounts(new ArrayList());

        AccountEntity account1 = new AccountEntity();
        account1.setAccId("account1");
        account1.setBalance(new BigDecimal("1000.00"));
        account1.setCustomer(mockCustomer);

        AccountEntity account2 = new AccountEntity();
        account2.setAccId("account2");
        account2.setBalance(new BigDecimal("500.00"));
        account2.setCustomer(mockCustomer);

        when(accountRepository.findByCustomer_CustId(anyString()))
                .thenReturn(List.of(account1, account2));

        List<AccountEntity> accounts = accountRepository.findByCustomer_CustId("customer123");

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals("account1", accounts.get(0).getAccId());
        assertEquals("account2", accounts.get(1).getAccId());
        assertEquals("customer123", accounts.get(0).getCustomer().getCustId());
        assertEquals("customer123", accounts.get(1).getCustomer().getCustId());

        verify(accountRepository, times(1)).findByCustomer_CustId("customer123");
    }

    @Test
    void testFindByCustomer_CustId_NoAccountsFound() {
        when(accountRepository.findByCustomer_CustId(anyString())).thenReturn(List.of());

        List<AccountEntity> accounts = accountRepository.findByCustomer_CustId("customer999");

        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());

        verify(accountRepository, times(1)).findByCustomer_CustId("customer999");
    }
}
