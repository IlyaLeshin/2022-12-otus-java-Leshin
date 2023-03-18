package ru.otus.core.CashBoxFactory;

import ru.otus.core.BanknoteDenomination;
import ru.otus.core.ATMException;

public abstract class CashBoxAbstract implements CashBox {

    private final int banknoteLimit;
    private int banknoteCount;

    protected CashBoxAbstract(int banknoteLimit, int banknoteCount) {
        this.banknoteLimit = banknoteLimit;
        this.banknoteCount = banknoteCount;
    }

    @Override
    public abstract BanknoteDenomination getDenomination();

    @Override
    public int getBanknoteLimit() {
        return banknoteLimit;
    }

    @Override
    public int getBalance() {
        return banknoteCount * getDenomination().getNumberOfBanknote();
    }

    @Override
    public void putCash(int banknote) {
        if ((this.banknoteCount + banknote) <= banknoteLimit) {
            this.banknoteCount += banknote;
        } else throw new ATMException("Положить деньги невозможно, ячейка " + getDenomination() + " будет переполнена");
    }

    @Override
    public int getCash(int banknoteCount) {
        if ((this.banknoteCount - banknoteCount) >= 0) {
            this.banknoteCount -= banknoteCount;
            return banknoteCount;
        } else throw new ATMException("В ячейке " + getDenomination() + " нет нужного количества банкнот");
    }

    @Override
    public int compareTo(CashBox cashBox) {
        return Integer.compare(getDenomination().getNumberOfBanknote(), cashBox.getDenomination().getNumberOfBanknote());
    }
}