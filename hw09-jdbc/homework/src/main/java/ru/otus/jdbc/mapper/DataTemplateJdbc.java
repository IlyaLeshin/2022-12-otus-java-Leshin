package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultSet -> {
            try {
                if (resultSet.next()) {
                    return createAnInstance(resultSet);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), resultSet -> {
            try {
                List<T> list = new ArrayList<>();
                if (resultSet.next()) {
                    var field = createAnInstance(resultSet);
                    list.add(field);
                }
                return list;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow();
    }

    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                entityClassMetaData.getFieldsWithoutId()
                        .stream()
                        .map(field -> {
                            field.setAccessible(true);
                            try {
                                return field.get(client);
                            } catch (IllegalAccessException e) {
                                throw new DataTemplateException(e);
                            }
                        }).toList());
    }

    @Override
    public void update(Connection connection, T client) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                entityClassMetaData
                        .getFieldsWithoutId()
                        .stream()
                        .map(field -> {
                            field.setAccessible(true);
                            try {
                                return field.get(client);
                            } catch (IllegalAccessException e) {
                                throw new DataTemplateException(e);
                            }
                        })
                        .toList());
    }

    private T createAnInstance(ResultSet resultSet) {
        try {
            T instance = entityClassMetaData.getConstructor().newInstance();
            entityClassMetaData.getAllFields().forEach(field ->
            {
                try {
                    field.setAccessible(true);
                    field.set(instance, resultSet.getObject(field.getName()));
                } catch (IllegalAccessException | SQLException e) {
                    throw new DataTemplateException(e);
                }
            });
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }
}