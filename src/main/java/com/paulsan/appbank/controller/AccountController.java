package com.paulsan.appbank.controller;

import com.paulsan.appbank.dto.AccountInsertDTO;
import com.paulsan.appbank.dto.AccountSampleDto;
import com.paulsan.appbank.entity.Account;
import com.paulsan.appbank.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@Api(value = "account module", description = "Account Operations")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(@Qualifier("AccountService") AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Create a account", response = ResponseEntity.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity createAccount(@RequestBody @Valid AccountInsertDTO accountDto) {
        return accountService.create(new Account(accountDto));
    }

    @ApiOperation(value = "View a list of accounts", response = List.class)
    @RequestMapping(method = RequestMethod.GET, value = "/simple/list", produces = "application/json")
    public List<AccountSampleDto> getSimpleList() {
        return accountService.listAll();
    }
}
