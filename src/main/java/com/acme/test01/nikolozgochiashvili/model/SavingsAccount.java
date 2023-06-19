package com.acme.test01.nikolozgochiashvili.model;


public class SavingsAccount extends Account {

    private int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "balance=" + balance +
                ", accountId=" + accountId +
                ", customerNumber='" + customerNumber + '\'' +
                '}';
    }
}
