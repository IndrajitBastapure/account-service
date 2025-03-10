package com.assign.api.account_service.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseListDTO {
    private List<AccountResponseDTO> transactions;
}
