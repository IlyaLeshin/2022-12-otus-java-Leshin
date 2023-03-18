package ru.otus.core.CashBoxFactory;

import ru.otus.core.BanknoteDenomination;

public interface CashBox extends Comparable<CashBox> {

    BanknoteDenomination getDenomination();

    int getBanknoteLimit();

    int getBalance();

    void putCash(int banknote);

    int getCash(int banknote);

    int compareTo(CashBox cashBox);
}