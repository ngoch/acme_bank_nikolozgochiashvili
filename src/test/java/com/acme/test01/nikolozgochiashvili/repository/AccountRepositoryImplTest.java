package com.acme.test01.nikolozgochiashvili.repository;

import com.acme.test01.nikolozgochiashvili.model.Account;
import com.acme.test01.nikolozgochiashvili.model.SavingsAccount;
import org.junit.Assert;
import org.junit.Test;

public class AccountRepositoryImplTest {

    @Test
    public void test() {
        AccountRepository repository = new AccountRepositoryImpl();
        Account account = repository.find(1L);
        System.out.println("account = " + account);
        Assert.assertEquals("1", account.getCustomerNumber());

        SavingsAccount sa = new SavingsAccount();
        sa.setAccountId(222L);
        sa.setCustomerNumber("1");
        sa.setBalance(50000);
        repository.save(sa);

        account = repository.find(222L);
        System.out.println("savedAccount = " + account);
        Assert.assertEquals("1", account.getCustomerNumber());


        SavingsAccount temp = (SavingsAccount) account;
        temp.setBalance(10);

        System.out.println("temp = " + repository.find(222L));

        account = repository.find(4L);
        System.out.println("savedAccount = " + account);
        Assert.assertEquals("4", account.getCustomerNumber());
    }
}
