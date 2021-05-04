package com.fxclub.account.model;

import com.fxclub.account.exception.LimitedBalanceException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountTest {

    private Account account;

    @Before
    public void setup() {
        account = new Account();
    }


    @Test
    public void testA_deposit() throws Exception {
        account.deposit(13);
        assertEquals(13, account.getBalance(), 0);
    }

    @Test
    public void testB_withdraw() throws Exception {
        account.deposit(13);
        account.withdraw(12);
        assertEquals(1, account.getBalance(), 0);
    }


    @Test(expected = LimitedBalanceException.class)
    public void testC_limitedBalance() throws Exception {
        account.withdraw(1);
    }

}