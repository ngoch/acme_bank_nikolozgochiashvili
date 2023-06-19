package com.acme.test01.nikolozgochiashvili.service;

import com.acme.test01.nikolozgochiashvili.repository.AccountRepository;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepositoryImpl;
import org.junit.Test;

public class AccountServiceImplTest {

    @Test
    public void test() {
        AccountRepository accountRepository = new AccountRepositoryImpl();

        AccountService accountService = new AccountServiceImpl("123");
        accountService.openSavingsAccount(11L, 1111L);
        accountService.deposit(11L, 11);
        System.out.println("Saving Account = " + accountRepository.find(11L));

        accountService.openCurrentAccount(12L);
        System.out.println("Current Account = " + accountRepository.find(12L));


    }
}
