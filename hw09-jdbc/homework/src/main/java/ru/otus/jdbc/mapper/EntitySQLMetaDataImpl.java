package ru.otus.jdbc.mapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;


public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private static final Logger log = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);
    private String selectAllSql;
    private String selectByIdSql;
    private String insertSql;
    private String updateSql;
    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        if (selectAllSql == null) {
            var joinerAllFieldsForSelect = new StringJoiner(", ");
            List<Field> allFields = entityClassMetaData.getAllFields();

            for (Field field : allFields) {
                joinerAllFieldsForSelect.add(field.getName());
            }

            selectAllSql = String.format(
                    "SELECT %s FROM %s",
                    joinerAllFieldsForSelect,
                    entityClassMetaData.getName());
        }
        log.debug("selectAllSql: {}", selectAllSql);
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        if (selectByIdSql == null) {
            selectByIdSql = String.format(
                    "SELECT * FROM %s WHERE %s = ?",
                    entityClassMetaData.getName(),
                    entityClassMetaData.getIdField().getName());
        }
        log.debug("selectByIdSql: {}", selectByIdSql);
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        if (insertSql == null) {
            var joinerFieldNamesForInsert = new StringJoiner(", ");
            var joinerFieldValuesForInsert = new StringJoiner(", ");
            List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();

            for (Field field : fieldsWithoutId) {
                joinerFieldNamesForInsert.add(field.getName());
                joinerFieldValuesForInsert.add("?");
            }

            insertSql = String.format(
                    "INSERT INTO %s (%s) VALUES (%s)",
                    entityClassMetaData.getName(),
                    joinerFieldNamesForInsert,
                    joinerFieldValuesForInsert);
        }
        log.debug("insertSql: {}", insertSql);
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        if (updateSql == null) {
            var joinerFieldForUpdate = new StringJoiner(", ");
            List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();

            for (Field field : fieldsWithoutId) {
                joinerFieldForUpdate.add(field.getName() + "=?");
            }

            updateSql = String.format(
                    "UPDATE %s SET %s WHERE %s = ?",
                    entityClassMetaData.getName(),
                    joinerFieldForUpdate,
                    entityClassMetaData.getIdField().getName());
        }
        log.debug("updateSql: {}", updateSql);
        return updateSql;
    }
}