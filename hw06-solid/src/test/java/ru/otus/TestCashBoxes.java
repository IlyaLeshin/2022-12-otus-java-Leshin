package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.core.BanknoteDenomination;
import ru.otus.core.CashBoxFactory.CashBox;
import ru.otus.core.CashBoxFactory.CashBoxFactory;
import ru.otus.core.ATMException;

public class TestCashBoxes {
    @Test
    @DisplayName("Соответствие номинала ячейки номиналу банкноты")
    void BanknoteDenominationEqualsCashBoxDenominationTest() {
        Assertions.assertEquals(BanknoteDenomination.ONE_HUNDRED, CashBoxFactory.getCashBox(BanknoteDenomination.ONE_HUNDRED).getDenomination());
        Assertions.assertEquals(BanknoteDenomination.TWO_HUNDRED, CashBoxFactory.getCashBox(BanknoteDenomination.TWO_HUNDRED).getDenomination());
        Assertions.assertEquals(BanknoteDenomination.FIVE_HUNDRED, CashBoxFactory.getCashBox(BanknoteDenomination.FIVE_HUNDRED).getDenomination());
        Assertions.assertEquals(BanknoteDenomination.ONE_THOUSAND, CashBoxFactory.getCashBox(BanknoteDenomination.ONE_THOUSAND).getDenomination());
        Assertions.assertEquals(BanknoteDenomination.TWO_THOUSAND, CashBoxFactory.getCashBox(BanknoteDenomination.TWO_THOUSAND).getDenomination());
        Assertions.assertEquals(BanknoteDenomination.FIVE_THOUSAND, CashBoxFactory.getCashBox(BanknoteDenomination.FIVE_THOUSAND).getDenomination());
    }

    @Test
    @DisplayName("Проверка ячейки")
    void cashBoxTest() {
        CashBox cashBoxOneHundred = CashBoxFactory.getCashBox(BanknoteDenomination.ONE_HUNDRED);
        int cashBoxBanknoteLimit = cashBoxOneHundred.getBanknoteLimit();

        Assertions.assertEquals(0, cashBoxOneHundred.getBalance());

        cashBoxOneHundred.putCash(10);
        Assertions.assertEquals(1000, cashBoxOneHundred.getBalance());

        Assertions.assertThrows(ATMException.class, () -> cashBoxOneHundred.putCash(cashBoxBanknoteLimit + 1));

        cashBoxOneHundred.getCash(5);
        Assertions.assertEquals(500, cashBoxOneHundred.getBalance());

        Assertions.assertThrows(ATMException.class, () -> cashBoxOneHundred.getCash(cashBoxBanknoteLimit + 1));
    }
}