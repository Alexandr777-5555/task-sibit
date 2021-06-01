package com.fxclub.account.service.impl;

import com.fxclub.account.exception.AccountNotFoundException;
import com.fxclub.account.exception.LimitedBalanceException;
import com.fxclub.account.model.Account;
import com.fxclub.account.repository.AccountRepository;
import com.fxclub.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Override
    @Transactional
    public Account create(Account account) {
        return repository.save(account);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = AccountNotFoundException.class)
    public Account getById(Long id) throws AccountNotFoundException {
        log.info("find by id");
        Account account = repository.getOne(id);
        if (account == null) {
            throw new AccountNotFoundException();
        }
        return account;
    }

    @Override
    @Transactional
    public void deposit(Long id, BigDecimal amount) {
        Account account = repository.getOne(id);
        if (account != null) {
            account.deposit(amount.doubleValue());
            repository.save(account);
        }
    }

    @Override
    @Transactional
    public void withdraw(Long id, double amount) throws LimitedBalanceException {
        Account account = repository.getOne(id);
        if (account != null) {
            account.withdraw(amount);
            repository.save(account);
        }
    }
}


