package ru.otus.homeworks.hw6.impl;

import ru.otus.homeworks.hw6.core.ATM;
import ru.otus.homeworks.hw6.core.ATMException;
import ru.otus.homeworks.hw6.core.BanknoteDenomination;
import ru.otus.homeworks.hw6.core.CashBoxFactory.CashBox;
import ru.otus.homeworks.hw6.core.CashBoxFactory.CashBoxFactory;


import java.util.*;


public class ATMImpl implements ATM {

    private final CashBox cashBoxOneHundred = CashBoxFactory.getCashBox(BanknoteDenomination.ONE_HUNDRED);
    private final CashBox cashBoxTwoHundred = CashBoxFactory.getCashBox(BanknoteDenomination.TWO_HUNDRED);
    private final CashBox cashBoxFiveHundred = CashBoxFactory.getCashBox(BanknoteDenomination.FIVE_HUNDRED);
    private final CashBox cashBoxOneThousand = CashBoxFactory.getCashBox(BanknoteDenomination.ONE_THOUSAND);
    private final CashBox cashBoxTwoThousand = CashBoxFactory.getCashBox(BanknoteDenomination.TWO_THOUSAND);
    private final CashBox cashBoxFiveThousand = CashBoxFactory.getCashBox(BanknoteDenomination.FIVE_THOUSAND);
    private final CashBoxesContainer container = new CashBoxesContainer.Builder()
            .addCashBox1(cashBoxOneHundred)
            .addCashBox2(cashBoxTwoHundred)
            .addCashBox3(cashBoxFiveHundred)
            .addCashBox4(cashBoxOneThousand)
            .addCashBox5(cashBoxTwoThousand)
            .addCashBox6(cashBoxFiveThousand)
            .build();
    private final List<CashBox> cashBoxesList = container.createCashBoxesSortList();

    @Override
    public int cashBalanceInATM() {
        int cashBalanceInATM = 0;
        for (CashBox cashBox : cashBoxesList) {
            cashBalanceInATM += cashBox.getBalance();
        }
        if (cashBalanceInATM != 0) {
            return cashBalanceInATM;
        } else throw new ATMException("В банкомате нет купюр");
    }

    @Override
    public Map<BanknoteDenomination, Integer> withdrawCashFromATM(int sumOfRequestedCash) {

        if (sumOfRequestedCash <= cashBalanceInATM()) {
            Map<BanknoteDenomination, Integer> withdrawCashFromATM = new TreeMap<>();
            int tempSumForWithdraw = sumOfRequestedCash;
            int numberOfBanknote;
            int banknotesCount;

            for (int i = cashBoxesList.size() - 1; i >= 0; i--) {
                CashBox cashBox = cashBoxesList.get(i);
                BanknoteDenomination banknoteDenomination = cashBox.getDenomination();

                numberOfBanknote = cashBox.getDenomination().getNumberOfBanknote();
                banknotesCount = tempSumForWithdraw / numberOfBanknote;

                if (banknotesCount != 0) {
                    try {
                        withdrawCashFromATM.put(banknoteDenomination, cashBox.getCash(banknotesCount));
                        tempSumForWithdraw -= numberOfBanknote * banknotesCount;
                    } catch (ATMException ignored) {
                    }
                }
            }
            return withdrawCashFromATM;
        } else throw new ATMException("в АТМ не хватает денег для выдачи");
    }

    @Override
    public void putCashInATM(Map<BanknoteDenomination, Integer> someSumOfBanknotes) {
        if (someSumOfBanknotes != null) {
            try {
                for (CashBox cashBox : cashBoxesList) {
                    BanknoteDenomination banknoteDenomination = cashBox.getDenomination();
                    Integer banknoteCount = someSumOfBanknotes.get(banknoteDenomination);
                    if (banknoteCount != null) {
                        cashBox.putCash(banknoteCount);
                    }
                }
            } catch (ATMException e) {
                throw new ATMException(e.getMessage());
            }
        } else throw new ATMException("Отсутствует сумма для вненсения");
    }
}