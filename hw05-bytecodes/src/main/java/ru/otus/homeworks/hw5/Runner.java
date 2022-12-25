package ru.otus.homeworks.hw5;

import ru.otus.homeworks.hw5.proxy.IoC;

public class Runner {

    public static void main(String[] args) {
        ClassWithMethodsInterface classWithMethods = IoC.createClassWithMethods();
        classWithMethods.calculation(111);
        classWithMethods.calculation(222, 333);
        classWithMethods.calculation(444, 555, "TEXT");
        classWithMethods.calculation();
    }
}