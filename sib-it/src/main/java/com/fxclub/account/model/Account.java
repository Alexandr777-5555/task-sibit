package com.fxclub.account.model;


import com.fxclub.account.exception.LimitedBalanceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;

@Slf4j
@Entity
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Account {

    @Id
    private Long id;

    private double balance;

    public Account() {
    }

    public void deposit(double amount){
        this.balance=+amount;
    }


    public void withdraw(double amount) throws LimitedBalanceException {
        if(this.balance<amount){
            throw new LimitedBalanceException();
        }else {
            this.balance=balance-amount;
        }

    }
}

