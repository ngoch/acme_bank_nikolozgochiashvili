package com.acme.test01.nikolozgochiashvili.service;

import com.acme.test01.nikolozgochiashvili.exception.WithdrawalAmountTooLargeException;
import com.acme.test01.nikolozgochiashvili.model.CurrentAccount;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepository;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

public class AccountServiceImplTest {

    @Test
    public void test() throws WithdrawalAmountTooLargeException {
        AccountRepository accountRepository = new AccountRepositoryImpl();

        AccountService accountService = new AccountServiceImpl("1");
        accountService.openSavingsAccount(11L, 1111L);
        accountService.deposit(11L, 11);
        System.out.println("Saving Account = " + accountRepository.find(11L));

        accountService.openCurrentAccount(12L);
        System.out.println("Current Account = " + accountRepository.find(12L));
    }

    @Test
    public void testWithdraw() throws WithdrawalAmountTooLargeException {
        AccountRepository accountRepository = new AccountRepositoryImpl();
        AccountService accountService = new AccountServiceImpl("4");
        accountService.withdraw(4L, 100);
        CurrentAccount account = (CurrentAccount) accountRepository.find(4L);
        System.out.println("after withdraw = " + account);
        Assert.assertEquals(20100, account.getOverdraft());
    }
}
