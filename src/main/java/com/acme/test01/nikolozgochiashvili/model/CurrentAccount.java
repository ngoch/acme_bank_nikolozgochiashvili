package com.acme.test01.nikolozgochiashvili.model;


public class CurrentAccount extends Account {
    private int positiveBalance;
    private int negativeBalance;

    private int overdraft;

    public int getPositiveBalance() {
        return positiveBalance;
    }

    public void setPositiveBalance(int positiveBalance) {
        this.positiveBalance = positiveBalance;
    }

    public int getNegativeBalance() {
        return negativeBalance;
    }

    public void setNegativeBalance(int negativeBalance) {
        this.negativeBalance = negativeBalance;
    }

    public int getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(int overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return "CurrentAccount{" +
                "positiveBalance=" + positiveBalance +
                ", negativeBalance=" + negativeBalance +
                ", overdraft=" + overdraft +
                ", accountId=" + accountId +
                ", customerNumber='" + customerNumber + '\'' +
                '}';
    }
}
