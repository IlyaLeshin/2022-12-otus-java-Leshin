package ru.otus.homeworks.hw5.proxy;

import ru.otus.homeworks.hw5.ClassWithMethodsImpl;
import ru.otus.homeworks.hw5.ClassWithMethodsInterface;
import ru.otus.homeworks.hw5.annotations.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IoC {

    public static ClassWithMethodsInterface createClassWithMethods() {
        InvocationHandler handler = new AnnotationInvocationHandler(new ClassWithMethodsImpl());
        return (ClassWithMethodsInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{ClassWithMethodsInterface.class}, handler);
    }

    static class AnnotationInvocationHandler implements InvocationHandler {

        private final ClassWithMethodsInterface classWithMethods;
        private final Set<String> annotatedMethods = new HashSet<>();
        private final Class<? extends Annotation> annotationClass = Log.class;

        AnnotationInvocationHandler(ClassWithMethodsInterface classWithMethods) {
            this.classWithMethods = classWithMethods;
            for (Method method : classWithMethods.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotationClass)) {
                    this.annotatedMethods.add(methodDescription(method));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {

            try {
                String methodName = method.getName();

                if (annotatedMethods.contains(methodDescription(method))) {
                    String loggingOutput = "executed method: " + methodName;
                    if (args != null) {
                        int paramID = 1;
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Object arg : args) {
                            stringBuilder.append(", arg").append(paramID++).append(" = ").append(arg);
                        }
                        loggingOutput += stringBuilder;
                    } else {
                        loggingOutput += ", без параметров";
                    }

                    System.out.println(loggingOutput);
                }
                return method.invoke(classWithMethods, args);
            } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        }

        private String methodDescription(Method method) {
            return method.getName() + " " + Arrays.toString(method.getParameterTypes());
        }
    }
}