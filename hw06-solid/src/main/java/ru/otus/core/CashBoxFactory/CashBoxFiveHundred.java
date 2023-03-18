package ru.otus.core.CashBoxFactory;

import ru.otus.core.BanknoteDenomination;

public class CashBoxFiveHundred extends CashBoxAbstract {

    protected CashBoxFiveHundred(int banknoteLimit, int banknoteCount) {
        super(banknoteLimit, banknoteCount);
    }

    @Override
    public BanknoteDenomination getDenomination() {
        return BanknoteDenomination.FIVE_HUNDRED;
    }
}