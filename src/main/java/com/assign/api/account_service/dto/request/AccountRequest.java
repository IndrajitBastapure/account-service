package com.assign.api.account_service.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    @NotBlank
    private String customerId;
    @Min(value = 0)
    private BigDecimal initialCredit;
}
