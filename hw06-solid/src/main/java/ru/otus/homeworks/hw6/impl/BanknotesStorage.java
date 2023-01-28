package ru.otus.homeworks.hw6.impl;

import ru.otus.homeworks.hw6.core.ATMException;
import ru.otus.homeworks.hw6.core.BanknoteDenomination;
import ru.otus.homeworks.hw6.core.CashBoxFactory.CashBox;

import java.util.*;


public class BanknotesStorage {
    private final List<CashBox> cashBoxesList;

    public BanknotesStorage(CashBoxesContainer cashBoxesContainer) {
        this.cashBoxesList = cashBoxesContainer.getSortedList();
    }

    public int cashBalance() {
        return cashBoxesList
                .stream()
                .mapToInt(CashBox::getBalance)
                .sum();
    }

    public Map<BanknoteDenomination, Integer> withdrawCash(int sumOfRequestedCash) {

        if (sumOfRequestedCash <= cashBalance()) {
            List<CashBox> tempCashBoxesList = new ArrayList<>(cashBoxesList);
            Map<BanknoteDenomination, Integer> withdrawCash = new TreeMap<>();
            List<CashBox> reverseCashBoxesList = cashBoxesList.stream().sorted(Comparator.reverseOrder()).toList();
            int tempSumForWithdraw = sumOfRequestedCash;
            int numberOfBanknote;
            int banknotesCount;

            try {
                for (CashBox cashBox : reverseCashBoxesList) {
                    BanknoteDenomination banknoteDenomination = cashBox.getDenomination();
                    numberOfBanknote = cashBox.getDenomination().getNumberOfBanknote();

                    banknotesCount = tempSumForWithdraw / numberOfBanknote;

                    if (banknotesCount != 0 && banknotesCount <= cashBox.getBalance()) {
                        withdrawCash.put(banknoteDenomination, cashBox.getCash(banknotesCount));
                        tempSumForWithdraw -= numberOfBanknote * banknotesCount;
                    }
                }
            } catch (ATMException e) {
                cashBoxesList.clear();
                cashBoxesList.addAll(tempCashBoxesList);
                throw new ATMException(e.getMessage());
            }
            return withdrawCash;
        }
        throw new ATMException("Отсутствует сумма для выдачи");
    }

    public void putCash(Map<BanknoteDenomination, Integer> someSumOfBanknotes) {
        if (someSumOfBanknotes != null) {
            List<CashBox> tempCashBoxesList = new ArrayList<>(cashBoxesList);

            try {
                for (CashBox cashBox : cashBoxesList) {
                    BanknoteDenomination banknoteDenomination = cashBox.getDenomination();
                    Integer banknoteCount = someSumOfBanknotes.get(banknoteDenomination);

                    if (banknoteCount != null) {
                        cashBox.putCash(banknoteCount);
                    }
                }
            } catch (ATMException e) {
                cashBoxesList.clear();
                cashBoxesList.addAll(tempCashBoxesList);
                throw new ATMException(e.getMessage());
            }
        } else throw new ATMException("Отсутствует сумма для внесения");
    }
}