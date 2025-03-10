package com.assign.api.account_service.clientconfig;
import com.assign.api.account_service.dto.request.TransactionRequest;
import com.assign.api.account_service.dto.response.TransactionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Set;

@FeignClient(name = "transaction-service", url = "${TRANSACTION_SERVICE_BASE_URL}")
public interface TransactionServiceClient {
    @PostMapping("/create")
    TransactionResponseDTO createTransaction(@RequestBody TransactionRequest request);

    @GetMapping("/{accountId}")
    Set<TransactionResponseDTO> getTransactionsByAccountId(@PathVariable("accountId") String accountId);
}