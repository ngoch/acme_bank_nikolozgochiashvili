package com.acme.test01.nikolozgochiashvili.service;

import com.acme.test01.nikolozgochiashvili.exception.AccountNotFoundException;
import com.acme.test01.nikolozgochiashvili.exception.WithdrawalAmountTooLargeException;
import com.acme.test01.nikolozgochiashvili.model.Account;
import com.acme.test01.nikolozgochiashvili.model.CurrentAccount;
import com.acme.test01.nikolozgochiashvili.model.SavingsAccount;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepository;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepositoryImpl;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository = new AccountRepositoryImpl();

    private final String customerNumber;

    public AccountServiceImpl(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public void openSavingsAccount(Long accountId, Long amountToDeposit) {
        if (amountToDeposit < 1000) {
            throw new RuntimeException("Deposit amount must be more then 1000");
        }

        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setCustomerNumber(this.customerNumber);
        savingsAccount.setAccountId(accountId);
        savingsAccount.setBalance(amountToDeposit.intValue());

        accountRepository.save(savingsAccount);
    }

    @Override
    public void openCurrentAccount(Long accountId) {
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccountId(accountId);
        currentAccount.setCustomerNumber(this.customerNumber);
        accountRepository.save(currentAccount);

    }

    @Override
    public void withdraw(Long accountId, int amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException {

    }

    @Override
    public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {
        Account account = accountRepository.find(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account with Id:" + account + " Not found.");
        }
        if (account instanceof SavingsAccount) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            savingsAccount.setBalance(amountToDeposit + savingsAccount.getBalance());
        }
    }
}
