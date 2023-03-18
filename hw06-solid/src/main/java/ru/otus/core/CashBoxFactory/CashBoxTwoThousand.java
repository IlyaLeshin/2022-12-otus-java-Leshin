package ru.otus.core.CashBoxFactory;

import ru.otus.core.BanknoteDenomination;

public class CashBoxTwoThousand extends CashBoxAbstract {

    protected CashBoxTwoThousand(int banknoteLimit, int banknoteCount) {
        super(banknoteLimit, banknoteCount);
    }

    @Override
    public BanknoteDenomination getDenomination() {
        return BanknoteDenomination.TWO_THOUSAND;
    }
}