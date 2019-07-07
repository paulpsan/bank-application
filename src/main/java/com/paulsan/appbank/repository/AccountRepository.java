package com.paulsan.appbank.repository;

import com.paulsan.appbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("AccountRepository")
public interface AccountRepository extends JpaRepository<Account, Serializable> {
}
