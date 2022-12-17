package ru.otus.homeworks.hw3;

import ru.otus.homeworks.hw3.annotations.*;

public class TestClass {

    @Test
    public void firstTest() {
        System.out.println("firstTest");
        throw new RuntimeException("firstTest");
    }

    @Test
    public void secondTest() {
        System.out.println("secondTest");
    }

    @Before
    public static void firstBefore() {
        System.out.println("firstBefore");
    }

    @Test
    public void thirdTest() {
        System.out.println("thirdTest");
        throw new RuntimeException("thirdTest");
    }

    @Test
    public static void fourthTest() {
        System.out.println("fourthTest");
    }

    @After
    public static void firstAfter() {
        System.out.println("firstAfter");
    }

    @Before
    public void secondBefore() {
        System.out.println("secondBefore");
    }

    @After
    public void secondAfter() {
        System.out.println("secondAfter");
    }
}