package com.paulsan.appbank.service;

import com.paulsan.appbank.dto.AccountSampleDto;
import com.paulsan.appbank.entity.Account;
import com.paulsan.appbank.entity.Movement;
import com.paulsan.appbank.repository.AccountRepository;
import com.paulsan.appbank.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("AccountService")
public class AccountService {
    private AccountRepository accountRepository;
    private MovementRepository movementRepository;

    @Autowired
    public AccountService(@Qualifier("AccountRepository") AccountRepository accountRepository, @Qualifier("MovementRepository") MovementRepository movementRepository) {
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
    }

    public ResponseEntity create(Account account) {
        Map<String, Object> result = new HashMap<>();
        Account resultAccount = accountRepository.save(account);
        resultAccount.createNumber();
        accountRepository.save(account);
        result.put("id", resultAccount.getId());

        if (account.getBalance() > 0) {
            Movement movement = Movement.createCredit(account);
            movement = movementRepository.save(movement);
            result.put("movementId", movement.getId());
        }

        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    public List<AccountSampleDto> listAll() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountSampleDto> accountSampleDtos = new ArrayList<>();
        for (Account account : accounts) {
            accountSampleDtos.add(new AccountSampleDto(account.getId(), account.getNumber()));
        }
        return accountSampleDtos;
    }
}
