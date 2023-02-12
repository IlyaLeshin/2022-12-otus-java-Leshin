package ru.otus.homeworks.hw6.impl;

import ru.otus.homeworks.hw6.core.ATM;
import ru.otus.homeworks.hw6.core.BanknoteDenomination;


import java.util.*;


public class ATMImpl implements ATM {

private final BanknotesStorage banknotesStorage;
    public ATMImpl(BanknotesStorage banknotesStorage){

        this.banknotesStorage = banknotesStorage;
    }

    @Override
    public int cashBalanceInATM() {
        return banknotesStorage.cashBalance();
    }

    @Override
    public Map<BanknoteDenomination, Integer> withdrawCashFromATM(int sumOfRequestedCash) {
        return banknotesStorage.withdrawCash(sumOfRequestedCash);
    }

    @Override
    public void putCashInATM(Map<BanknoteDenomination, Integer> someSumOfBanknotes) {
        banknotesStorage.putCash(someSumOfBanknotes);
    }
}