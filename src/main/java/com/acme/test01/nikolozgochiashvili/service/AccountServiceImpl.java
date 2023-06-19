package com.acme.test01.nikolozgochiashvili.service;

import com.acme.test01.nikolozgochiashvili.exception.AccountNotFoundException;
import com.acme.test01.nikolozgochiashvili.exception.WithdrawalAmountTooLargeException;
import com.acme.test01.nikolozgochiashvili.model.Account;
import com.acme.test01.nikolozgochiashvili.model.CurrentAccount;
import com.acme.test01.nikolozgochiashvili.model.SavingsAccount;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepository;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepositoryImpl;

import java.util.Objects;

public class AccountServiceImpl implements AccountService {

    private static final int OVERDRAFT_LIMIT = 100000;
    private static final int MIN_DEPOSIT = 1000;

    private final AccountRepository accountRepository = new AccountRepositoryImpl();

    private final String customerNumber;

    public AccountServiceImpl(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public void openSavingsAccount(Long accountId, Long amountToDeposit) {
        if (amountToDeposit < MIN_DEPOSIT) {
            throw new RuntimeException("Deposit amount must be more then " + MIN_DEPOSIT);
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

        Account account = accountRepository.find(accountId);

        checkAccount(account, accountId);

        if (account instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) account;

            if ((currentAccount.getPositiveBalance() - currentAccount.getNegativeBalance()) + OVERDRAFT_LIMIT < amountToWithdraw) {
                throw new WithdrawalAmountTooLargeException("Withdrawal amount too large, Limit is:" + OVERDRAFT_LIMIT);
            }

            currentAccount.setOverdraft(amountToWithdraw + currentAccount.getOverdraft());

            int balance = currentAccount.getPositiveBalance() - amountToWithdraw;
            currentAccount.setPositiveBalance(Math.max(balance, 0));
            currentAccount.setNegativeBalance(currentAccount.getNegativeBalance() + Math.abs(balance));
        } else {
            System.out.println("Account is not current type");
        }
    }

    @Override
    public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {
        Account account = accountRepository.find(accountId);

        checkAccount(account, accountId);

        if (account instanceof SavingsAccount) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            savingsAccount.setBalance(amountToDeposit + savingsAccount.getBalance());
        } else {
            System.out.println("Account is not saving type");
        }
    }

    private void checkAccount(Account account, long accountId) throws AccountNotFoundException {
        if (account == null) {
            throw new AccountNotFoundException("Account with Id:" + accountId + " Not found.");
        }

        if (!Objects.equals(account.getCustomerNumber(), this.customerNumber)) {
            throw new AccountNotFoundException("Account with Id:" + accountId + " doesn't have customer: " + customerNumber);
        }
    }
}
