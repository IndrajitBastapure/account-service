package com.assign.api.account_service.repository;

import com.assign.api.account_service.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    List<AccountEntity> findByCustomer_CustId(String customerId);
}
