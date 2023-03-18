package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRunner {
    public static void runTests(String classname) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(classname);
        Method[] methods = clazz.getMethods();
        Method[] beforeMethods = getMethods(methods, Before.class);
        Method[] afterMethods = getMethods(methods, After.class);
        Method[] tests = getMethods(methods, Test.class);

        TestResult testResult = TestRunner.getTestResult(beforeMethods, tests, afterMethods, clazz);
        TestRunner.printResult(testResult);
    }

    private static TestResult getTestResult(Method[] beforeMethods, Method[] tests, Method[] afterMethods, Class<?> clazz) {
        int testCount = 0;
        int passed = 0;
        int failed = 0;

        for (Method test : tests) {
            ++testCount;
            System.out.println("Выполнение теста " + test.getName() + ":");
            try {
                Object newInstance = clazz.getConstructor().newInstance();
                runTest(newInstance, beforeMethods, afterMethods, test);
                System.out.println("PASSED");
                ++passed;
            } catch (Exception ex) {
                System.out.println("FAILED " + Arrays.toString(ex.getSuppressed()));
                ++failed;

            } finally {
                System.out.println("--------------------------------");
            }
        }
        return new TestResult(testCount, passed, failed);
    }

    private static void runTest(Object instance, Method[] beforeMethods, Method[] afterMethods, Method test) {
        List<Exception> exceptions = new ArrayList<>();
        try {
            for (Method method : beforeMethods) {
                invoke(instance, method);
            }
            invoke(instance, test);
        } catch (Exception ex) {
            exceptions.add(ex);
        } finally {
            for (Method method : afterMethods) {
                try {
                    invoke(instance, method);
                } catch (Exception ex) {
                    exceptions.add(ex);
                }
            }
        }

        if (exceptions.size() > 0) {
            RuntimeException exceptionRunTest = new RuntimeException();
            for (Exception exception : exceptions) {
                exceptionRunTest.addSuppressed(exception);
            }
            throw exceptionRunTest;
        }
    }

    public static void invoke(Object instance, Method method) {
        try {
            method.setAccessible(true);
            method.invoke(instance);
        } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private record TestResult(int testCount, int passed, int failed) {
    }

    public static void printResult(TestResult results) {
        System.out.printf("Всего тестов %d, из нх PASSED: %d, FAILED: %d", results.testCount, results.passed, results.failed);
        System.out.println("""
                
                """);
    }

    private static Method[] getMethods(Method[] methods, Class<? extends java.lang.annotation.Annotation> annotationClass) {
        List<Method> list = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                list.add(method);
            }
        }
        return list.toArray(new Method[0]);
    }
}