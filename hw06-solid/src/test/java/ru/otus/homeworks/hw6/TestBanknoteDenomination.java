package ru.otus.homeworks.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homeworks.hw6.core.BanknoteDenomination;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestBanknoteDenomination {

    @Test
    @DisplayName("Проверка номинала банкнот")
    void values() {
        assertThat(BanknoteDenomination.ONE_HUNDRED.getNumberOfBanknote()).isEqualTo(100);
        assertThat(BanknoteDenomination.TWO_HUNDRED.getNumberOfBanknote()).isEqualTo(200);
        assertThat(BanknoteDenomination.FIVE_HUNDRED.getNumberOfBanknote()).isEqualTo(500);
        assertThat(BanknoteDenomination.ONE_THOUSAND.getNumberOfBanknote()).isEqualTo(1000);
        assertThat(BanknoteDenomination.TWO_THOUSAND.getNumberOfBanknote()).isEqualTo(2000);
        assertThat(BanknoteDenomination.FIVE_THOUSAND.getNumberOfBanknote()).isEqualTo(5000);
    }
}