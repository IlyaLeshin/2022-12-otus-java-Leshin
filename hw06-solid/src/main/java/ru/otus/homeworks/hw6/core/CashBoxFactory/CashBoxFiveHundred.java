package ru.otus.homeworks.hw6.core.CashBoxFactory;

import ru.otus.homeworks.hw6.core.BanknoteDenomination;

public class CashBoxFiveHundred extends CashBoxAbstract {

    protected CashBoxFiveHundred(int banknoteLimit, int banknoteCount) {
        super(banknoteLimit, banknoteCount);
    }

    @Override
    public BanknoteDenomination getDenomination() {
        return BanknoteDenomination.FIVE_HUNDRED;
    }
}