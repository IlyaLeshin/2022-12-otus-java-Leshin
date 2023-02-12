package ru.otus.homeworks.hw6.core.CashBoxFactory;

import ru.otus.homeworks.hw6.core.BanknoteDenomination;

public class CashBoxOneThousand extends CashBoxAbstract {

    protected CashBoxOneThousand(int banknoteLimit, int banknoteCount) {
        super(banknoteLimit, banknoteCount);
    }

    @Override
    public BanknoteDenomination getDenomination() {
        return BanknoteDenomination.ONE_THOUSAND;
    }
}