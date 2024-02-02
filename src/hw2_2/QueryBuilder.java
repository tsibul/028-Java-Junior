package hw2_2;

import java.lang.reflect.Field;
import java.util.UUID;

public class QueryBuilder {

    /**
     * Построить запрос на добавление данных в БД
     *
     * @param obj
     * @return
     */
    public String buildInsertQuery(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Table.class)) {
            return "";
        }
        StringBuilder query = new StringBuilder("INSERT INTO ");
        StringBuilder values = new StringBuilder(") VALUES (");
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query
                .append(tableAnnotation.name())
                .append(" (");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                query
                        .append(columnAnnotation.name())
                        .append(", ");
                field.setAccessible(true);
                values.append("'").append(field.get(obj)).append("', ");
            }
        }
        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
            values.delete(query.length() - 2, query.length());
        }
        query.append(values);
        query.append(")");
        return query.toString();
    }


    /**
     * Построить запрос на получение данных из БД
     *
     * @param clazz
     * @param primaryKey
     * @return
     */
    public String buildSelectQuery(Class<?> clazz, UUID primaryKey) {

        if (!clazz.isAnnotationPresent(Table.class)) {
            return "";
        }

        StringBuilder query = new StringBuilder("SELECT * FROM ");

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query.append(tableAnnotation.name()).append(" WHERE ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey()) {
                    query.append(columnAnnotation.name())
                            .append(" = ")
                            .append("'")
                            .append(primaryKey)
                            .append("'");
                    break;
                }
            }
        }

        return query.toString();
    }

    /**
     * Построить запрос на удаление данных из бд
     *
     * @param obj
     * @return
     */
    public String buildUpdateQuery(Object obj) {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Table.class)) {
            return "";
        }

        StringBuilder query = new StringBuilder("UPDATE ");
        StringBuilder where = new StringBuilder(" WHERE ");


        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query.append(tableAnnotation.name()).append(" SET ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);

                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey())
                    try {
                        where.append(columnAnnotation.name()).append(" = '").append(field.get(obj)).append("'");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                try {
                    query.append(columnAnnotation.name()).append(" = '").append(field.get(obj)).append("', ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
        }
        query.append(where);
        return query.toString();
    }

    /**
     * @return
     */
    public String buildDeleteQuery(Class<?> clazz, UUID id) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return "";
        }
        StringBuilder query = new StringBuilder("DELETE FROM ");
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query.append(tableAnnotation.name()).append(" WHERE ");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey()) {
                        query.append(columnAnnotation.name()).append(" = '").append(id).append("'");
                    break;
                }
            }
        }
        return query.toString();
    }
}