package ru.otus.core.CashBoxFactory;

import ru.otus.core.BanknoteDenomination;

public class CashBoxTwoHundred extends CashBoxAbstract {

    protected CashBoxTwoHundred(int banknoteLimit, int banknoteCount) {
        super(banknoteLimit, banknoteCount);
    }

    @Override
    public BanknoteDenomination getDenomination() {
        return BanknoteDenomination.TWO_HUNDRED;
    }
}