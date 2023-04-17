package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.model.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private static final Logger log = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);
    private final Class<T> entityClass;
    private String className;
    private Field fieldWithId;
    private List<Field> allFields;
    private List<Field> fieldsWithoutId;
    private Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        if (className == null) {
            className = entityClass.getSimpleName().toLowerCase();
        }
        return className;
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructor == null) {
            try {
                constructor = entityClass.getConstructor();
            } catch (NoSuchMethodException e) {
                log.error("NoSuchMethodException in getConstructor: ", e);
                throw new RuntimeException(e);
            }
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        if (fieldWithId == null) {
            fieldWithId = getAllFields().stream()
                    .filter(f -> f.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElseThrow();
        }
        return fieldWithId;
    }

    @Override
    public List<Field> getAllFields() {
        if (allFields == null) {
            allFields = Arrays.stream(entityClass.getDeclaredFields()).toList();
        }
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId == null) {
            fieldsWithoutId = getAllFields().stream()
                    .filter(f -> !(f.isAnnotationPresent(Id.class)))
                    .toList();
        }
        return fieldsWithoutId;
    }
}