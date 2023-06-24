package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            var configInstance = configClass.getConstructor().newInstance();
            var componentMethods = getComponentMethods(configClass.getDeclaredMethods());

            for (var method : componentMethods) {
                try {
                    String name = method.getAnnotation(AppComponent.class).name();
                    var parameters = Arrays.stream(method.getParameterTypes()).map(this::getAppComponent).toArray();
                    var bean = method.invoke(configInstance, parameters);
                    if (appComponentsByName.containsKey(name)) {
                        throw new RuntimeException(String.format("Дублирование компонента %s", name));
                    }
                    appComponentsByName.put(name, bean);
                    appComponents.add(bean);
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Method> getComponentMethods(Method[] declaredMethods) {
        return Arrays.stream(declaredMethods)
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .toList();
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components = appComponents.stream()
                .filter(component -> componentClass.isAssignableFrom(component.getClass()))
                .toList();
        if (components.size() == 1) {
            return (C) components.get(0);
        } else if (components.size() == 0) {
            throw new RuntimeException(String.format("Компонент не найден в классе %s", componentClass.getName()));
        } else
            throw new RuntimeException(String.format("Дублирование компонентов в классе %s", componentClass.getName()));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C appComponent = (C) appComponentsByName.get(componentName);
        if (appComponent != null) {
            return appComponent;
        } else throw new RuntimeException(String.format("Компонент %s не найден", componentName));
    }
}
