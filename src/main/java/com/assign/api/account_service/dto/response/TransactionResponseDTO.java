package com.assign.api.account_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private String  id;
    private String  accountId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
}
