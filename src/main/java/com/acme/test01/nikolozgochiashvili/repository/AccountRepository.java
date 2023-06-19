package com.acme.test01.nikolozgochiashvili.repository;

import com.acme.test01.nikolozgochiashvili.model.Account;

import java.util.List;

public interface AccountRepository {


    void save(Account account);

    Account find(Long accountId);

    List<Account> getCustomerAccounts(String customerNumber);
}
