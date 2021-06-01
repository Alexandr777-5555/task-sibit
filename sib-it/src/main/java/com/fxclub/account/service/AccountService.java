package com.fxclub.account.service;


import com.fxclub.account.exception.AccountNotFoundException;
import com.fxclub.account.exception.LimitedBalanceException;
import com.fxclub.account.model.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account create(Account account);

    Account getById(Long id) throws AccountNotFoundException;

    void deposit(Long id, BigDecimal amount);

    void withdraw(Long id, double amount) throws LimitedBalanceException;

}
