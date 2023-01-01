package ru.otus.homeworks.hw5;

import ru.otus.homeworks.hw5.classes.ClassWithMethodsInterfaceTwoImpl;
import ru.otus.homeworks.hw5.classes.ClassWithMethodsInterfaceOneImpl;
import ru.otus.homeworks.hw5.classes.ClassWithMethodsInterfaceOne;
import ru.otus.homeworks.hw5.classes.ClassWithMethodsInterfaceTwo;
import ru.otus.homeworks.hw5.proxy.IoC;

public class Runner {

    public static void main(String[] args) {
        ClassWithMethodsInterfaceOne classWithMethodsOne = (ClassWithMethodsInterfaceOne) IoC.createClassWithMethods(ClassWithMethodsInterfaceTwoImpl.class);
        classWithMethodsOne.calculation(111);
        classWithMethodsOne.calculation(222, 333);
        classWithMethodsOne.calculation(444, 555, "TEXT1");
        classWithMethodsOne.calculation();

        ClassWithMethodsInterfaceTwo classWithMethodsTwo = (ClassWithMethodsInterfaceTwo) IoC.createClassWithMethods(ClassWithMethodsInterfaceOneImpl.class);
        classWithMethodsTwo.calculation(666);
        classWithMethodsTwo.calculation(777, 888);
        classWithMethodsTwo.calculation(999, 100, "TEXT2");
        classWithMethodsTwo.calculation();
    }
}