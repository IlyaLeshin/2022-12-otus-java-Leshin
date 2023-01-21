package ru.otus.homeworks.hw6.core.CashBoxFactory;

import ru.otus.homeworks.hw6.core.BanknoteDenomination;

public class CashBoxFiveThousand extends CashBoxAbstract {

    protected CashBoxFiveThousand(int banknoteLimit, int banknoteCount) {
        super(banknoteLimit, banknoteCount);
    }

    @Override
    public BanknoteDenomination getDenomination() {
        return BanknoteDenomination.FIVE_THOUSAND;
    }
}