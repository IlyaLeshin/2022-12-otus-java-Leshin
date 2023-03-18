package ru.otus.core.CashBoxFactory;

import ru.otus.core.BanknoteDenomination;

public class CashBoxOneHundred extends CashBoxAbstract {

    protected CashBoxOneHundred(int banknoteLimit, int banknoteCount) {
        super(banknoteLimit, banknoteCount);
    }

    @Override
    public BanknoteDenomination getDenomination() {
        return BanknoteDenomination.ONE_HUNDRED;
    }
}