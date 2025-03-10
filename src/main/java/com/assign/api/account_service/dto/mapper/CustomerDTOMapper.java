package com.assign.api.account_service.dto.mapper;

import com.assign.api.account_service.dto.CustomerDTO;
import com.assign.api.account_service.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOMapper {

    public CustomerDTO mapToCustomerDTO(CustomerEntity customerEntity){
        return CustomerDTO.builder()
                .custId(customerEntity.getCustId())
                .name(customerEntity.getName())
                .surname(customerEntity.getSurname())
                .build();
    }
}
