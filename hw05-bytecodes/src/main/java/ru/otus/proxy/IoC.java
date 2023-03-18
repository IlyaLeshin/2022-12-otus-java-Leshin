package ru.otus.proxy;


import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IoC {

    public static Object createClassWithMethods(Class<?> clazzImpl) {
        Class<?>[] clazzInterface = clazzImpl.getInterfaces();
        InvocationHandler handler = new AnnotationInvocationHandler(classNewInstance(clazzImpl));
        return Proxy.newProxyInstance(IoC.class.getClassLoader(), clazzInterface, handler);
    }

    private static Object classNewInstance(Class<?> clazzImpl) {
        try {
            return clazzImpl.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static class AnnotationInvocationHandler implements InvocationHandler {

        private final Object classWithMethods;
        private final Set<String> annotatedMethods = new HashSet<>();

        private AnnotationInvocationHandler(Object classWithMethods) {
            this.classWithMethods = classWithMethods;
            for (Method method : classWithMethods.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Log.class)) {
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