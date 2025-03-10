package com.assign.api.account_service.service;

import com.assign.api.account_service.dto.CustomerDTO;
import com.assign.api.account_service.dto.mapper.CustomerDTOMapper;
import com.assign.api.account_service.entity.CustomerEntity;
import com.assign.api.account_service.exception.CustomerDoesNotExistException;
import com.assign.api.account_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDTOMapper customerDTOMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerDTOMapper customerDTOMapper) {
        this.customerRepository = customerRepository;
        this.customerDTOMapper = customerDTOMapper;
    }
    public CustomerEntity findCustomerById(String custId){
        return customerRepository.findById(custId).orElseThrow(() -> new CustomerDoesNotExistException("Customer does not exist with custId : "+custId));
    }

    public CustomerDTO getCustomerById(String custId){
        return customerDTOMapper.mapToCustomerDTO(findCustomerById(custId));
    }
}
