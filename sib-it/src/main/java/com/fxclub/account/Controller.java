package com.fxclub.account;

import com.fxclub.account.exception.AccountNotFoundException;
import com.fxclub.account.exception.LimitedBalanceException;
import com.fxclub.account.model.Account;
import com.fxclub.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class Controller {

    private Number balance = BigDecimal.ZERO;

    private static final AtomicLong ATOMIC_LONG = new AtomicLong();

    @Autowired
    private AccountService service;

    @PostMapping("create")
    public CreateAccountResponse createAccount() {
        log.info("creating account");
        final Long id = ATOMIC_LONG.incrementAndGet();
        Account account = new Account();
        account.setId(id);
        account.setBalance(0);
        service.create(account);
        return new CreateAccountResponse(id);
    }

    @PostMapping("info")
    public AccountInfoResponse getAccountInfo(@RequestBody AccountInfoRequest request) throws AccountNotFoundException {
        log.info("info Account ");
        Account account = service.getById(request.getId().longValue());
        balance = account.getBalance();
        return new AccountInfoResponse(request.getId(), balance);
    }


    @PostMapping("deposit")
    public void deposit(@RequestBody AccountDepositRequest request) {
        log.info("in method deposit");
        Long id = request.getId().longValue();
        balance = request.getAmount();
        BigDecimal deposit= BigDecimal.valueOf(balance.doubleValue());
        service.deposit(id, deposit);
    }

    @PostMapping("withdraw")
    public void withdraw(@RequestBody AccountDepositRequest request) throws LimitedBalanceException {
        log.info("in method withdraw");
        Long id =  request.getId().longValue();
        balance = request.getAmount(); // сумма для снятия
        BigDecimal withdraw = BigDecimal.valueOf(balance.doubleValue());
        service.withdraw( id ,withdraw);
    }

}
