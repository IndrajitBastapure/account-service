package com.assign.api.account_service.dto.response;

import com.assign.api.account_service.dto.CustomerDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO {
    private String accountId;
    private String customerId;
    private BigDecimal balance;
    private CustomerDTO customer;
    private Set<TransactionResponseDTO> transactions;
}
