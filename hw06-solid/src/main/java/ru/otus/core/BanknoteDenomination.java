package ru.otus.core;

public enum BanknoteDenomination {
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private final int numberOfBanknote;

    BanknoteDenomination(int numberOfBanknote) {
        this.numberOfBanknote = numberOfBanknote;
    }

    public int getNumberOfBanknote() {
        return this.numberOfBanknote;
    }
}

