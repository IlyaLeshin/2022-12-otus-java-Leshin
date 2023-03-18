package ru.otus.impl;


import ru.otus.core.CashBoxFactory.CashBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public record CashBoxesContainer(CashBox cashBox1, CashBox cashBox2, CashBox cashBox3, CashBox cashBox4,
                                 CashBox cashBox5, CashBox cashBox6) {

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

    public List<CashBox> getSortedList() {

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
}