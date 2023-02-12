package ru.otus.homeworks.hw6.core.CashBoxFactory;

import ru.otus.homeworks.hw6.core.BanknoteDenomination;

public class CashBoxOneHundred extends CashBoxAbstract {

    protected CashBoxOneHundred(int banknoteLimit, int banknoteCount) {
        super(banknoteLimit, banknoteCount);
    }

    @Override
    public BanknoteDenomination getDenomination() {
        return BanknoteDenomination.ONE_HUNDRED;
    }
}