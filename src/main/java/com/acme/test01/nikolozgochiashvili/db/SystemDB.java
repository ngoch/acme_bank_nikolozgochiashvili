package com.acme.test01.nikolozgochiashvili.db;

import com.acme.test01.nikolozgochiashvili.model.Account;
import com.acme.test01.nikolozgochiashvili.model.CurrentAccount;
import com.acme.test01.nikolozgochiashvili.model.SavingsAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SystemDB {

    private static SystemDB instance;

    /**
     * < Customer Number, List of Accounts>
     */
    private final Map<String, List<Account>> cache = new ConcurrentHashMap<>();


    private SystemDB() {
        this.initData();
    }

    public static SystemDB getInstance() {
        if (instance == null) {
            instance = new SystemDB();
        }
        return instance;
    }

    public void initData() {

        SavingsAccount sa1 = new SavingsAccount();
        sa1.setAccountId(1L);
        sa1.setCustomerNumber("1");
        sa1.setBalance(2000);

        createAccount(sa1);

        SavingsAccount sa2 = new SavingsAccount();
        sa2.setAccountId(2L);
        sa2.setCustomerNumber("2");
        sa2.setBalance(5000);

        createAccount(sa2);

        CurrentAccount ca1 = new CurrentAccount();
        ca1.setAccountId(3L);
        ca1.setCustomerNumber("3");
        ca1.setPositiveBalance(1000);
        ca1.setOverdraft(10000);
        createAccount(ca1);

        CurrentAccount ca2 = new CurrentAccount();
        ca2.setAccountId(4L);
        ca2.setCustomerNumber("4");
        ca2.setNegativeBalance(5000);
        ca2.setOverdraft(20000);
        createAccount(ca2);
    }

    private void createAccount(Account account) {
        List<Account> accounts = cache.computeIfAbsent(account.getCustomerNumber(), k -> new ArrayList<>());
        accounts.add(account);
    }

    public Map<String, List<Account>> getCache() {
        return cache;
    }
}
