package ru.otus.core.CashBoxFactory;

import ru.otus.core.BanknoteDenomination;

public class CashBoxFactory {

    public static CashBox getCashBox(BanknoteDenomination banknoteDenomination) {
        if (banknoteDenomination.equals(BanknoteDenomination.ONE_HUNDRED)) {
            return new CashBoxOneHundred(100,0);
        }
        if (banknoteDenomination.equals(BanknoteDenomination.TWO_HUNDRED)) {
            return new CashBoxTwoHundred(100,0);
        }
        if (banknoteDenomination.equals(BanknoteDenomination.FIVE_HUNDRED)) {
            return new CashBoxFiveHundred(100,0);
        }
        if (banknoteDenomination.equals(BanknoteDenomination.ONE_THOUSAND)) {
            return new CashBoxOneThousand(100,0);
        }
        if (banknoteDenomination.equals(BanknoteDenomination.TWO_THOUSAND)) {
            return new CashBoxTwoThousand(100,0);
        }
        if (banknoteDenomination.equals(BanknoteDenomination.FIVE_THOUSAND)) {
            return new CashBoxFiveThousand(100,0);
        }

        throw new IllegalArgumentException("в настройках нет такого наминала" + banknoteDenomination);
    }
}