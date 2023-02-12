package ru.otus.homeworks.hw6.core.CashBoxFactory;

import ru.otus.homeworks.hw6.core.BanknoteDenomination;

public interface CashBox extends Comparable<CashBox> {

    BanknoteDenomination getDenomination();

    int getBanknoteLimit();

    int getBalance();

    void putCash(int banknote);

    int getCash(int banknote);

    int compareTo(CashBox cashBox);
}