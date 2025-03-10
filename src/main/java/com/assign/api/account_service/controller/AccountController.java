package com.assign.api.account_service.controller;
import com.assign.api.account_service.dto.response.AccountResponseDTO;
import com.assign.api.account_service.dto.request.AccountRequest;
import com.assign.api.account_service.dto.response.AccountResponseListDTO;
import com.assign.api.account_service.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account-service/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create a new account connected to provided customerID", description = "A new account is created connected to provided customerID.", tags ="create")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/create")
    public AccountResponseDTO createAccount(@Validated @RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }
    @Operation(summary = "Fetches Account related information like Name, Surname, balance, and transactions of the accounts", description = "Returns Account information like Name, Surname, balance, and transactions of the accounts.", tags ="fetch")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/user/{customerId}")
    public AccountResponseListDTO getUserAccounts(@PathVariable String customerId) {
        return accountService.getAccountsByCustomerId(customerId);
    }
}
