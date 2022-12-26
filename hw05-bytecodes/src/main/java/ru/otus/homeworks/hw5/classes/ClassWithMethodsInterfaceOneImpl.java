package ru.otus.homeworks.hw5.classes;

import ru.otus.homeworks.hw5.annotations.Log;

public class ClassWithMethodsInterfaceOneImpl implements ClassWithMethodsInterfaceTwo {

    @Log
    @Override
    public void calculation() {
        System.out.println("Метод calculation без параметров");
    }

    @Log
    @Override
    public void calculation(int param1) {
        System.out.println("Метод calculation с одним параметром param1 = "+param1);
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("Метод calculation с двумя параметрами param1 = "+param1+" и param2 = "+param2);
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Метод calculation с тремя параметрами param1 = "+param1+", param2 = "+param2+", param3 = "+param3);
    }
}