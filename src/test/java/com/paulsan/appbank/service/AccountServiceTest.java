package com.paulsan.appbank.service;

import com.paulsan.appbank.dto.AccountSampleDto;
import com.paulsan.appbank.entity.*;
import com.paulsan.appbank.repository.AccountRepository;
import com.paulsan.appbank.repository.MovementRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void create_CaseAmountZero() {
        Account account = new Account();
        //TODO:WE NEED TO MODIFY THIS LINE AUTO CREATING THE ACCOUNT, NUMBER. ANGEL CHAMBI 25/05/19
        //account.setNumber("201-01-000001");
        account.setDepartment(Department.LA_PAZ);
        account.setBalance(0.00);
        account.setCurrency(Currency.BOLIVIANOS);
        account.setHolder("Jhon Snow");
        account.setId(1);

        when(accountRepository.save(eq(account))).thenReturn(account);
        ResponseEntity responseEntity = accountService.create(account);

        assertEquals("201-01-000001", account.getNumber());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Map result = (HashMap) responseEntity.getBody();
        assertEquals(account.getId(), result.get("id"));
        verify(accountRepository, times(2)).save(eq(account));
    }

    @Test
    public void create_CaseAmountNotZero() {

        double amount = 10.00;
        Currency bolivianos = Currency.BOLIVIANOS;

        Account account = new Account();
        account.setNumber("201-01-000001");
        account.setDepartment(Department.LA_PAZ);
        account.setBalance(amount);
        account.setCurrency(bolivianos);
        account.setHolder("Jhon Snow");
        account.setId(1);

        Movement movement = new Movement();
        movement.setAmount(amount);
        movement.setCurrency(bolivianos);
        movement.setType(MovementType.CREDIT);

        when(accountRepository.save(eq(account))).thenReturn(account);
        when(movementRepository.save(eq(movement))).thenReturn(movement);

        ResponseEntity responseEntity = accountService.create(account);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Map result = (HashMap) responseEntity.getBody();
        assertEquals(account.getId(), result.get("id"));
        assertEquals(movement.getId(), result.get("movementId"));

        verify(accountRepository, times(2)).save(eq(account));
        verify(movementRepository, times(1)).save(eq(movement));
    }

    @Test
    public void list() {
        Account account1 = new Account();
        account1.setNumber("201-01-000001");
        account1.setId(1);

        Account account2 = new Account();
        account2.setNumber("201-01-000002");
        account2.setId(2);

        List<Account> accountsExpected = new ArrayList<>();
        accountsExpected.add(account1);
        accountsExpected.add(account2);
        Mockito.when(accountRepository.findAll()).thenReturn(accountsExpected);

        List<AccountSampleDto> accounts = accountService.listAll();
        assertEquals(accountsExpected.size(), accounts.size());
        for (int i = 0; i < accounts.size(); i++) {
            assertEquals(accountsExpected.get(i).getId(), accounts.get(i).getId());
            assertEquals(accountsExpected.get(i).getNumber(), accounts.get(i).getNumber());
        }
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(accountRepository);
    }
}