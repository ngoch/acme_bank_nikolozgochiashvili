package com.acme.test01.nikolozgochiashvili.repository;

import com.acme.test01.nikolozgochiashvili.db.SystemDB;
import com.acme.test01.nikolozgochiashvili.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {

    private final SystemDB systemDB = SystemDB.getInstance();

    @Override
    public void save(Account account) {
        List<Account> accounts = systemDB.getCache().computeIfAbsent(account.getCustomerNumber(), k -> new ArrayList<>());
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    @Override
    public Account find(Long accountId) {
        return systemDB.getCache().values().stream().flatMap(List::stream).collect(Collectors.toList()).stream().filter(a -> Objects.equals(a.getAccountId(), accountId)).findFirst().orElse(null);
    }

    @Override
    public List<Account> getCustomerAccounts(String customerNumber) {
        return systemDB.getCache().get(customerNumber);
    }
}
