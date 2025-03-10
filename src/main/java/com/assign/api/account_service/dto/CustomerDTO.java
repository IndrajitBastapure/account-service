package com.assign.api.account_service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private String custId;

    private String name;

    private String surname;
}
