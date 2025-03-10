package com.assign.api.account_service.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerDoesNotExistException extends RuntimeException{
    public CustomerDoesNotExistException(String message){
        super(message);
    }
}
