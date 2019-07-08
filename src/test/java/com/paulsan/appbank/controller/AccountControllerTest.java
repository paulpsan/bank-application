package com.paulsan.appbank.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulsan.appbank.dto.AccountInsertDTO;
import com.paulsan.appbank.dto.AccountSampleDto;
import com.paulsan.appbank.dto.DTOModelMapper;
import com.paulsan.appbank.entity.Account;
import com.paulsan.appbank.entity.Currency;
import com.paulsan.appbank.entity.Department;
import com.paulsan.appbank.service.AccountService;
import junit.framework.TestCase;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends TestCase {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AccountService accountService;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AccountController(accountService))
                .setCustomArgumentResolvers(new DTOModelMapper(objectMapper))
                .build();
    }

    @Test
    public void createAccount() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO();
        accountInsertDTO.setHolder("Daenerys Targaryen");
        accountInsertDTO.setBalance(20.00);
        accountInsertDTO.setDepartment(Department.BENI);
        accountInsertDTO.setCurrency(Currency.BOLIVIANOS);

        Account accountExpected = new Account(null, 20.00, Currency.BOLIVIANOS, "Daenerys Targaryen",
                Department.BENI
        );

        when(accountService.create(any(Account.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/")
                .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Mockito.verify(accountService).create(eq(accountExpected));
    }

    @Test
    public void createAccountWrongHolder() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO();
        accountInsertDTO.setBalance(20.00);
        accountInsertDTO.setDepartment(Department.BENI);
        accountInsertDTO.setCurrency(Currency.BOLIVIANOS);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/")
                .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createAccountWrongBalance() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO();
        accountInsertDTO.setHolder("John Snow");
        accountInsertDTO.setBalance(-20.00);
        accountInsertDTO.setDepartment(Department.BENI);
        accountInsertDTO.setCurrency(Currency.BOLIVIANOS);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/")
                .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getSimpleList() throws Exception {
        List<AccountSampleDto> accounts = Arrays.asList(
                new AccountSampleDto(1, "201-02-000001"),
                new AccountSampleDto(2, "202-02-000002"),
                new AccountSampleDto(3, "201-09-000003")
        );

        when(accountService.listAll()).thenReturn(accounts);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/simple/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].number", Matchers.is("201-02-000001")))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].number", Matchers.is("202-02-000002")))

                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].number", Matchers.is("201-09-000003")));
        Mockito.verify(accountService).listAll();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(accountService);
    }
}