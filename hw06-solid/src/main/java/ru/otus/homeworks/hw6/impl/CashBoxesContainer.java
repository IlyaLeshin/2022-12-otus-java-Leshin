package ru.otus.homeworks.hw6.impl;


import ru.otus.homeworks.hw6.core.CashBoxFactory.CashBox;

import java.util.*;

public class CashBoxesContainer {

    private final CashBox cashBox1;
    private final CashBox cashBox2;
    private final CashBox cashBox3;
    private final CashBox cashBox4;
    private final CashBox cashBox5;
    private final CashBox cashBox6;

    private CashBoxesContainer(Builder builder) {
        this.cashBox1 = builder.cashBox1;
        this.cashBox2 = builder.cashBox2;
        this.cashBox3 = builder.cashBox3;
        this.cashBox4 = builder.cashBox4;
        this.cashBox5 = builder.cashBox5;
        this.cashBox6 = builder.cashBox6;
    }

    @Override
    public String toString() {
        return "CashBoxesContainer{" +
                "cashBox1='" + cashBox1.toString() + '\'' +
                ", cashBox2='" + cashBox2.toString() + '\'' +
                ", cashBox3='" + cashBox3.toString() + '\'' +
                ", cashBox4='" + cashBox4.toString() + '\'' +
                ", cashBox5='" + cashBox5.toString() + '\'' +
                ", cashBox6='" + cashBox6.toString() + '\'' +
                '}';
    }

    public List<CashBox> createCashBoxesSortList() {

        List<CashBox> containerList = new ArrayList<>();
        containerList.add(cashBox1);
        containerList.add(cashBox2);
        containerList.add(cashBox3);
        containerList.add(cashBox4);
        containerList.add(cashBox5);
        containerList.add(cashBox6);
        Collections.sort(containerList);
        
        return containerList;
    }

    public static class Builder {
        private CashBox cashBox1;
        private CashBox cashBox2;
        private CashBox cashBox3;
        private CashBox cashBox4;
        private CashBox cashBox5;
        private CashBox cashBox6;

        Builder() {
        }

        Builder addCashBox1(CashBox cashBox1) {
            this.cashBox1 = cashBox1;
            return this;
        }

        Builder addCashBox2(CashBox cashBox2) {
            this.cashBox2 = cashBox2;
            return this;
        }

        Builder addCashBox3(CashBox cashBox3) {
            this.cashBox3 = cashBox3;
            return this;
        }

        Builder addCashBox4(CashBox cashBox4) {
            this.cashBox4 = cashBox4;
            return this;
        }

        Builder addCashBox5(CashBox cashBox5) {
            this.cashBox5 = cashBox5;
            return this;
        }

        Builder addCashBox6(CashBox cashBox6) {
            this.cashBox6 = cashBox6;
            return this;
        }

        CashBoxesContainer build() {
            return new CashBoxesContainer(this);
        }
    }
}