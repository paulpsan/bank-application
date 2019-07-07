package com.paulsan.appbank.entity;


import com.paulsan.appbank.dto.AccountInsertDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    public Account() {
    }

    public Account(AccountInsertDTO accountDto) {
        this.balance = accountDto.getBalance();
        this.currency = accountDto.getCurrency();
        this.department = accountDto.getDepartment();
        this.holder = accountDto.getHolder();
    }

    public Account(String number, Double balance, Currency currency, String holder, Department department) {
        this.number = number;
        this.balance = balance;
        this.currency = currency;
        this.holder = holder;
        this.department = department;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "number", length = 13, unique = true)
    private String number;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private Double balance;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "holder", length = 30, nullable = false)
    private String holder;

    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void createNumber() {
        this.number = currency.getValue() + "-" + department.getValue() + "-" + String.format("%06d", id);
    }

    @Override
    public boolean equals(Object obj) {
        Account account = (Account) obj;
        return this.id == account.id &&
                Objects.equals(account.number, this.number) &&
                Objects.equals(account.balance, account.balance) &&
                Objects.equals(account.currency, account.currency) &&
                Objects.equals(account.holder, account.holder) &&
                Objects.equals(account.department, account.department);
    }
}
