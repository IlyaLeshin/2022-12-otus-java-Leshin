package ru.otus.homeworks.hw6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homeworks.hw6.core.ATM;
import ru.otus.homeworks.hw6.core.ATMException;
import ru.otus.homeworks.hw6.core.BanknoteDenomination;
import ru.otus.homeworks.hw6.impl.ATMImpl;

import java.util.Map;
import java.util.TreeMap;

public class TestATMImpl {

    @Test
    @DisplayName("Внесение денежных средств и вывод баланса")
    void putAllCashNominalsAndBalanceTest() {
        ATM atm = new ATMImpl();
        Map<BanknoteDenomination, Integer> cashPutToATM = new TreeMap<>();

        cashPutToATM.put(BanknoteDenomination.ONE_HUNDRED, 1);
        cashPutToATM.put(BanknoteDenomination.TWO_HUNDRED, 1);
        cashPutToATM.put(BanknoteDenomination.FIVE_HUNDRED, 1);
        cashPutToATM.put(BanknoteDenomination.ONE_THOUSAND, 1);
        cashPutToATM.put(BanknoteDenomination.TWO_THOUSAND, 1);
        cashPutToATM.put(BanknoteDenomination.FIVE_THOUSAND, 1);

        atm.putCashInATM(cashPutToATM);

        Assertions.assertEquals(8800, atm.cashBalanceInATM());
    }

    @Test
    @DisplayName("Внесение денежных средств частями и вывод баланса")
    void putPartCashNominalsAndBalanceTest() {
        ATM atm = new ATMImpl();
        Map<BanknoteDenomination, Integer> cashPutToATM_100_500 = new TreeMap<>();

        cashPutToATM_100_500.put(BanknoteDenomination.ONE_HUNDRED, 1);
        cashPutToATM_100_500.put(BanknoteDenomination.TWO_HUNDRED, 1);
        cashPutToATM_100_500.put(BanknoteDenomination.FIVE_HUNDRED, 1);

        Map<BanknoteDenomination, Integer> cashPutToATM_1000_5000 = new TreeMap<>();

        cashPutToATM_1000_5000.put(BanknoteDenomination.ONE_THOUSAND, 1);
        cashPutToATM_1000_5000.put(BanknoteDenomination.TWO_THOUSAND, 1);
        cashPutToATM_1000_5000.put(BanknoteDenomination.FIVE_THOUSAND, 1);

        atm.putCashInATM(cashPutToATM_100_500);
        Assertions.assertEquals(800, atm.cashBalanceInATM());

        atm.putCashInATM(cashPutToATM_1000_5000);
        Assertions.assertEquals(8800, atm.cashBalanceInATM());
    }

    @Test
    @DisplayName("Внесение денежных средств больше лимита")
    void putCashNominalsMoreLimitTest() {
        ATM atm = new ATMImpl();
        Map<BanknoteDenomination, Integer> cashPutToATM_Exception = new TreeMap<>();

        cashPutToATM_Exception.put(BanknoteDenomination.ONE_HUNDRED, 500);
        Assertions.assertThrows(ATMException.class, () -> atm.putCashInATM(cashPutToATM_Exception));
    }

    @Test
    @DisplayName("Вывод денежных средств и вывод баланса")
    void withdrawCashFromATMTest() {
        ATM atm = new ATMImpl();
        Map<BanknoteDenomination, Integer> cashPutToATM = new TreeMap<>();

        cashPutToATM.put(BanknoteDenomination.ONE_HUNDRED, 10);
        cashPutToATM.put(BanknoteDenomination.TWO_HUNDRED, 10);
        cashPutToATM.put(BanknoteDenomination.FIVE_HUNDRED, 10);
        cashPutToATM.put(BanknoteDenomination.ONE_THOUSAND, 10);
        cashPutToATM.put(BanknoteDenomination.TWO_THOUSAND, 10);
        cashPutToATM.put(BanknoteDenomination.FIVE_THOUSAND, 10);

        Map<BanknoteDenomination, Integer> withdrawCash = new TreeMap<>();
        withdrawCash.put(BanknoteDenomination.ONE_HUNDRED, 1);
        withdrawCash.put(BanknoteDenomination.TWO_HUNDRED, 1);
        withdrawCash.put(BanknoteDenomination.FIVE_HUNDRED, 1);

        Map<BanknoteDenomination, Integer> withdrawCash_27200 = new TreeMap<>();
        withdrawCash_27200.put(BanknoteDenomination.FIVE_THOUSAND, 5);
        withdrawCash_27200.put(BanknoteDenomination.TWO_THOUSAND, 1);
        withdrawCash_27200.put(BanknoteDenomination.TWO_HUNDRED, 1);

        atm.putCashInATM(cashPutToATM);
        Assertions.assertEquals(88000, atm.cashBalanceInATM());

        Assertions.assertEquals(withdrawCash, atm.withdrawCashFromATM(800));
        Assertions.assertEquals(87200, atm.cashBalanceInATM());

        Assertions.assertEquals(withdrawCash_27200, atm.withdrawCashFromATM(27200));
        Assertions.assertEquals(60000, atm.cashBalanceInATM());
    }

    @Test
    @DisplayName("Вывод денежных средств превышающих баланс банкомата")
    void withdrawCashFromATMMoreBalanceTest() {
        ATM atm = new ATMImpl();
        Map<BanknoteDenomination, Integer> cashPutToATM = new TreeMap<>();
        cashPutToATM.put(BanknoteDenomination.ONE_HUNDRED, 10);
        atm.putCashInATM(cashPutToATM);
        Assertions.assertEquals(1000, atm.cashBalanceInATM());

        Assertions.assertThrows(ATMException.class, () -> atm.withdrawCashFromATM(atm.cashBalanceInATM() + 1));
    }
}