package com.assign.api.account_service.controller;

import com.assign.api.account_service.dto.CustomerDTO;
import com.assign.api.account_service.dto.request.AccountRequest;
import com.assign.api.account_service.dto.response.AccountResponseDTO;
import com.assign.api.account_service.dto.response.AccountResponseListDTO;
import com.assign.api.account_service.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;

    @Test
    void testCreateAccount() throws Exception {
        AccountRequest mockRequest = new AccountRequest("customer123", new BigDecimal("1000.00"));
        CustomerDTO customerDTO = new CustomerDTO("customer123","Robert","Keil");
        AccountResponseDTO mockResponse = new AccountResponseDTO("account123", "customer123", new BigDecimal("1000.00"),customerDTO, Set.of());

        Mockito.when(accountService.createAccount(any(AccountRequest.class))).thenReturn(mockResponse);
        mockMvc.perform(post("/api/account-service/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerId": "customer123",
                                    "initialCredit": 1000.00
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value("account123"))
                .andExpect(jsonPath("$.customerId").value("customer123"))
                .andExpect(jsonPath("$.balance").value(1000.00));
    }

    @Test
    void testGetUserAccounts() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("customer123","Robert","Keil");
        AccountResponseDTO account1 = new AccountResponseDTO("account123", "customer123", new BigDecimal("1000.00"),customerDTO,Set.of());
        AccountResponseDTO account2 = new AccountResponseDTO("account124", "customer123", new BigDecimal("500.00"),customerDTO,Set.of());
        AccountResponseListDTO mockResponse = AccountResponseListDTO.builder()
                .transactions(Arrays.asList(account1, account2))
                .build();

        Mockito.when(accountService.getAccountsByCustomerId(eq("customer123"))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/account-service/accounts/user/customer123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.transactions[0].accountId").value("account123"))
                .andExpect(jsonPath("$.transactions[0].customerId").value("customer123"))
                .andExpect(jsonPath("$.transactions[0].balance").value(1000.00))
                .andExpect(jsonPath("$.transactions[1].accountId").value("account124"))
                .andExpect(jsonPath("$.transactions[1].customerId").value("customer123"))
                .andExpect(jsonPath("$.transactions[1].balance").value(500.00));
    }
}