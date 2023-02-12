package ru.otus.homeworks.hw6.core;

import java.util.Map;

public interface ATM {

    int cashBalanceInATM();

    Map<BanknoteDenomination, Integer> withdrawCashFromATM(int sum);

    void putCashInATM(Map<BanknoteDenomination, Integer> someCountOfBanknotes);
}