package com.assign.api.account_service.clientconfig.init;
import com.assign.api.account_service.entity.CustomerEntity;
import com.assign.api.account_service.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {
    private final CustomerRepository customerRepository;

    public DataInitializer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void init() {
        if (customerRepository.count() == 0) {
            List<CustomerEntity> customers = List.of(
                    new CustomerEntity("cust-123", "Robert", "Keil",new ArrayList()),
                    new CustomerEntity("cust-456", "Alice", "Johnson",new ArrayList()),
                    new CustomerEntity("cust-789", "Peter", "Weeda",new ArrayList())
            );
            customerRepository.saveAll(customers);
        }
    }
}