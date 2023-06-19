package com.acme.test01.nikolozgochiashvili;


import com.acme.test01.nikolozgochiashvili.exception.WithdrawalAmountTooLargeException;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepository;
import com.acme.test01.nikolozgochiashvili.repository.AccountRepositoryImpl;
import com.acme.test01.nikolozgochiashvili.service.AccountService;
import com.acme.test01.nikolozgochiashvili.service.AccountServiceImpl;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println("Type customer number");
            String customerNumber = scan.nextLine();
            AccountService accountService = new AccountServiceImpl(customerNumber);
            AccountRepository accountRepository = new AccountRepositoryImpl();
            System.out.println("Customer Accounts = " + accountRepository.getCustomerAccounts(customerNumber));

            while (true) {
                System.out.println("You can type commands: openSavinAccount, OpenCurrentAccount, withdraw, deposit and exit to stop");

                String command = scan.nextLine();
                if (command.equals("exit")) {
                    return;
                }
                switch (command) {
                    case "openSavinAccount":
                        System.out.println("accountId:");
                        Long accountId = Long.valueOf(scan.nextLine());
                        System.out.println("amountToDeposit:");
                        Long amountToDeposit = Long.valueOf(scan.nextLine());
                        accountService.openSavingsAccount(accountId, amountToDeposit);
                        System.out.println("Opened saving account = " + accountRepository.find(accountId));
                        break;
                    case "openCurrentAccount":
                        System.out.println("accountId:");
                        Long currentAccountId = Long.valueOf(scan.nextLine());
                        accountService.openCurrentAccount(currentAccountId);
                        System.out.println("Opened current account = " + accountRepository.find(currentAccountId));
                        break;
                    case "withdraw":
                        System.out.println("accountId:");
                        Long withdrawAccountId = Long.valueOf(scan.nextLine());
                        System.out.println("amountToWithdraw:");
                        int amountToWithdraw = Integer.parseInt(scan.nextLine());
                        try {
                            accountService.withdraw(withdrawAccountId, amountToWithdraw);
                        } catch (WithdrawalAmountTooLargeException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Add deposit account = " + accountRepository.find(withdrawAccountId));
                        break;
                    case "deposit":
                        System.out.println("accountId:");
                        Long depositAccountId = Long.valueOf(scan.nextLine());
                        System.out.println("amountToDeposit:");
                        int deposit = Integer.parseInt(scan.nextLine());
                        accountService.deposit(depositAccountId, deposit);
                        System.out.println("Add deposit account = " + accountRepository.find(depositAccountId));
                        break;
                }

                System.out.println("Customer Accounts after operations = " + accountRepository.getCustomerAccounts(customerNumber));
            }
        }
    }
}
