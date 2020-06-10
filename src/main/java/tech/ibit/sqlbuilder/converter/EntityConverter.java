package tech.ibit.sqlbuilder.converter;

import lombok.experimental.UtilityClass;
import tech.ibit.sqlbuilder.AutoIncrementIdSetterMethod;
import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.annotation.DbColumn;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 实体转换类
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@UtilityClass
public class EntityConverter {


    /**
     * 定义遍历实体类继承深度
     */
    private final int MAX_DEPTH = 3;


    /**
     * 获取表的列信息
     *
     * @param poClazz 持久化对象类
     * @return 表的列对象信息
     * @see TableColumnInfo
     */
    public TableColumnInfo getTableColumns(Class poClazz) {
        checkEntityClazz(poClazz);

        DbTable table = (DbTable) poClazz.getAnnotation(DbTable.class);
        Table dbTable = new Table(table.name(), table.alias());
        int depth = 0;
        Set<String> existedColumns = new HashSet<>();
        List<ColumnInfo> columnInfoList = new ArrayList<>(10);

        while (isContinue(poClazz, depth)) {
            depth++;
            Arrays.stream(poClazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(DbColumn.class) || field.isAnnotationPresent(DbId.class))
                    .forEach(field -> {
                        DbColumn dbColumn = field.getAnnotation(DbColumn.class);
                        if (null != dbColumn) {
                            String columnName = dbColumn.name();
                            if (!existedColumns.contains(columnName)) {
                                columnInfoList.add(new ColumnInfo(new Column(dbTable, columnName), false, dbColumn.nullable()));
                                existedColumns.add(columnName);
                            }
                        } else {
                            DbId id = field.getAnnotation(DbId.class);
                            if (null != id) {
                                String idName = id.name();
                                if (!existedColumns.contains(idName)) {
                                    columnInfoList.add(new ColumnInfo(new Column(dbTable, idName), true, false));
                                    existedColumns.add(idName);
                                }
                            }
                        }
                    });
            poClazz = poClazz.getSuperclass();
        }

        return new TableColumnInfo(dbTable, columnInfoList);
    }

    /**
     * 获取需要被更新的列
     *
     * @param poClazz 持久化对象类
     * @return 需要被更新的列列表
     */
    public List<Column> getUpdateColumns(Class poClazz) {
        return getUpdateColumns(poClazz, null);
    }

    /**
     * 获取需要被更新的列
     *
     * @param poClazz       持久化对象类
     * @param ignoreColumns 需要忽略的列
     * @return 需要被更新的列列表
     */
    public List<Column> getUpdateColumns(Class poClazz, List<Column> ignoreColumns) {
        TableColumnInfo columnInfo = getTableColumns(poClazz);
        if (CollectionUtils.isEmpty(ignoreColumns)) {
            return columnInfo.getNotIdColumns();
        }
        Set<String> ignoreColumnAlias = ignoreColumns
                .stream()
                .map(Column::getNameWithTableAlias)
                .collect(Collectors.toSet());
        return columnInfo
                .getNotIdColumns()
                .stream()
                .filter(column -> !ignoreColumnAlias.contains(column.getNameWithTableAlias()))
                .collect(Collectors.toList());
    }

    /**
     * 批量获取需要更新的列信息和相应的值
     *
     * @param pos             持久化对象列表
     * @param returnNullValue 如果为true, 则当列的值为null的时候也返回
     * @return "列-值"信息列表
     * @see TableColumnValues
     */
    public List<TableColumnSetValues> getTableColumnValuesList(List<Object> pos, boolean returnNullValue) {
        if (null == pos || pos.isEmpty()) {
            return Collections.emptyList();
        }
        return pos.stream()
                .map(entity -> getTableColumnValues(entity, returnNullValue))
                .collect(Collectors.toList());
    }

    /**
     * 批量指定列获取需要更新的列信息和相应的值
     *
     * @param pos          持久化对象列表
     * @param columnsOrder 指定列
     * @return "列-值"信息列表
     */
    public List<TableColumnSetValues> getTableColumnValuesList(List<Object> pos, List<Column> columnsOrder) {
        if (null == pos || pos.isEmpty()) {
            return Collections.emptyList();
        }
        return pos.stream()
                .map(entity -> getTableColumnValues(entity, columnsOrder))
                .collect(Collectors.toList());
    }

    /**
     * 获取自增长id的Setter方法
     *
     * @param poClazz 持久化对象类
     * @return 自增正id的Setter方法
     * @see AutoIncrementIdSetterMethod
     */
    public AutoIncrementIdSetterMethod getAutoIncrementIdSetterMethod(Class poClazz) {
        checkEntityClazz(poClazz);

        int depth = 0;
        while (isContinue(poClazz, depth)) {
            depth++;
            for (Field field : poClazz.getDeclaredFields()) {
                DbId id = field.getAnnotation(DbId.class);
                if (null != id && id.autoIncrease()) {
                    return new AutoIncrementIdSetterMethod(field.getType(), MethodUtils.getSetterMethod(poClazz, field));
                }
            }
            poClazz = poClazz.getSuperclass();
        }
        return null;
    }

    /**
     * 获取列
     *
     * @param poClazz 持久化对象类
     * @return 列列表
     */
    public List<Column> getColumns(Class poClazz) {
        TableColumnInfo tableColumnInfo = getTableColumns(poClazz);
        return tableColumnInfo.getColumns();
    }


    /**
     * 对象复制
     *
     * @param originalObject 原始对象
     * @param poClazz        目标类
     * @param <T>            原始类类型
     * @param <P>            目标类类型
     * @return 目标对象
     */
    public <T, P> P copyColumns(T originalObject, Class<P> poClazz) {
        if (null == originalObject) {
            return null;
        }
        checkEntityClazz(originalObject.getClass());
        checkEntityClazz(poClazz);

        DbTable oTable = originalObject.getClass().getAnnotation(DbTable.class);
        DbTable poTable = poClazz.getAnnotation(DbTable.class);
        if (!oTable.name().equals(poTable.name())) {
            throw SqlException.tableNotMatched(oTable.name(), poTable.name());
        }

        Map<String, Method> fieldGetterMethods = getFieldGetterMethods(originalObject.getClass());
        Map<String, Method> fieldSetterMethods = getFieldSetterMethods(poClazz);
        Map<String, Object> columnValues = getFieldValues(originalObject, fieldGetterMethods);
        return convert2Object(poClazz, fieldSetterMethods, columnValues);
    }

    /**
     * 对象列表复制
     *
     * @param originalObjects 原始对象列表
     * @param poClazz         目标类
     * @param <T>             原始对象类型
     * @param <P>             目标类类型
     * @return 目标对象列表
     */
    public <T, P> List<P> copyColumns(List<T> originalObjects, Class<P> poClazz) {
        if (null == originalObjects) {
            return null;
        }
        if (originalObjects.isEmpty()) {
            return Collections.emptyList();
        }
        checkEntityClazz(originalObjects.get(0).getClass());
        checkEntityClazz(poClazz);

        T covertObject = originalObjects.get(0);
        DbTable oTable = covertObject.getClass().getAnnotation(DbTable.class);
        DbTable poTable = poClazz.getAnnotation(DbTable.class);
        if (!oTable.name().equals(poTable.name())) {
            throw SqlException.tableNotMatched(oTable.name(), poTable.name());
        }

        Map<String, Method> fieldGetterMethods = getFieldGetterMethods(covertObject.getClass());
        Map<String, Method> fieldSetterMethods = getFieldSetterMethods(poClazz);
        List<P> result = new ArrayList<>();
        originalObjects.forEach(obj -> {
            Map<String, Object> fieldValues = getFieldValues(obj, fieldGetterMethods);
            result.add(convert2Object(poClazz, fieldSetterMethods, fieldValues));
        });
        return result;
    }


    /**
     * 获取类的"字段-Getter方法"Map
     *
     * @param clazz 类
     * @return "字段-Getter方法"Map
     */
    private Map<String, Method> getFieldGetterMethods(Class clazz) {
        Map<String, Method> fieldGetterMethods = new HashMap<>();
        int depth = 0;
        while (isContinue(clazz, depth)) {
            depth++;
            Class finalClazz = clazz;
            Arrays.stream(finalClazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(DbColumn.class) || field.isAnnotationPresent(DbId.class))
                    .forEach(field -> fieldGetterMethods.put(field.getName(), MethodUtils.getGetterMethod(finalClazz, field)));
            clazz = clazz.getSuperclass();
        }
        return fieldGetterMethods;
    }

    /**
     * 获取类的"字段-Setter方法"Map
     *
     * @param clazz 类
     * @return "字段-Setter方法"Map
     */
    private Map<String, Method> getFieldSetterMethods(Class clazz) {
        Map<String, Method> fieldGetterMethods = new HashMap<>();
        int depth = 0;
        while (isContinue(clazz, depth)) {
            depth++;
            Class finalClazz = clazz;
            Arrays.stream(finalClazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(DbColumn.class) || field.isAnnotationPresent(DbId.class))
                    .forEach(field -> fieldGetterMethods.put(field.getName(), MethodUtils.getSetterMethod(finalClazz, field)));
            clazz = clazz.getSuperclass();
        }
        return fieldGetterMethods;
    }


    /**
     * 获取字段-值Map
     *
     * @param objectToConvert    被转换对象
     * @param fieldGetterMethods 字段的Getter方法
     * @return 字段-值Map
     */
    private Map<String, Object> getFieldValues(Object objectToConvert, Map<String, Method> fieldGetterMethods) {
        Map<String, Object> result = new HashMap<>();
        fieldGetterMethods.forEach((field, method) -> {
            if (null != method) {
                try {
                    result.put(field, method.invoke(objectToConvert));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        return result;
    }

    /**
     * 转为实例
     *
     * @param poClazz            实体类
     * @param fieldSetterMethods 字段Setter方法
     * @param fieldValues        字段值
     * @param <P>                实体类型
     * @return 实例
     */
    private <P> P convert2Object(Class<P> poClazz, Map<String, Method> fieldSetterMethods
            , Map<String, Object> fieldValues) {
        try {
            P result = poClazz.newInstance();
            for (String field : fieldSetterMethods.keySet()) {
                Method setMethod = fieldSetterMethods.get(field);
                if (null != setMethod && null != fieldValues.get(field)) {
                    try {
                        setMethod.invoke(result, fieldValues.get(field));
                    } catch (InvocationTargetException e) {
                        //ignore
                    }
                }
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取待更新列信息
     *
     * @param entity          实体
     * @param returnNullValue 是否返回null的字段
     * @return 列更新信息
     */
    public TableColumnSetValues getTableColumnValues(Object entity,
                                                     boolean returnNullValue) {
        Class clazz = entity.getClass();
        checkEntityClazz(clazz);

        DbTable table = (DbTable) clazz.getAnnotation(DbTable.class);
        Table dbTable = new Table(table.name(), table.alias());
        int depth = 0;
        Map<String, ColumnSetValue> columnValueMap = new LinkedHashMap<>();
        while (isContinue(clazz, depth)) {

            depth++;
            for (Field field : clazz.getDeclaredFields()) {
                //主键
                if (field.isAnnotationPresent(DbId.class)) {
                    DbId idAnnotation = field.getAnnotation(DbId.class);
                    Column id = new Column(dbTable, idAnnotation.name());
                    String idAlias = id.getNameWithTableAlias();
                    //set field accessible
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        if (returnNullValue || null != value) {
                            columnValueMap.putIfAbsent(idAlias, new ColumnSetValue(id, value, true, false, idAnnotation.autoIncrease()));
                        }
                    } catch (IllegalAccessException e) {
                        //ignore
                    }
                } else if (field.isAnnotationPresent(DbColumn.class)) {
                    DbColumn columnAnnotation = field.getAnnotation(DbColumn.class);
                    Column column = new Column(dbTable, columnAnnotation.name());
                    String columnAlias = column.getNameWithTableAlias();
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        if (returnNullValue || null != value) {
                            columnValueMap.putIfAbsent(columnAlias, new ColumnSetValue(column, value, false, columnAnnotation.nullable(), false));
                        }
                    } catch (IllegalAccessException e) {
                        //ignore
                    }

                }
            }
            clazz = clazz.getSuperclass();
        }
        List<ColumnSetValue> columnValues = new ArrayList<>();
        columnValueMap.forEach((column, value) -> columnValues.add(value));
        return new TableColumnSetValues(dbTable, columnValues);
    }

    /**
     * 获取指定表-列值信息
     *
     * @param entity    实体
     * @param orderList 列排序列表（指定列）
     * @return 表-列值信息
     */
    public TableColumnSetValues getTableColumnValues(Object entity, List<Column> orderList) {
        Class clazz = entity.getClass();
        checkEntityClazz(clazz);

        DbTable table = (DbTable) clazz.getAnnotation(DbTable.class);
        Table dbTable = new Table(table.name(), table.alias());
        if (CollectionUtils.isEmpty(orderList)) {
            return new TableColumnSetValues(dbTable, Collections.emptyList());
        }
        int depth = 0;
        Map<String, ColumnSetValue> columnValueMap = new LinkedHashMap<>();
        Set<String> columnSet = orderList.stream().map(Column::getNameWithTableAlias).collect(Collectors.toSet());
        while (isContinue(clazz, depth)) {

            depth++;
            for (Field field : clazz.getDeclaredFields()) {
                //主键
                if (field.isAnnotationPresent(DbId.class)) {
                    DbId idAnnotation = field.getAnnotation(DbId.class);
                    Column id = new Column(dbTable, idAnnotation.name());
                    String idAlias = id.getNameWithTableAlias();
                    if (columnSet.contains(idAlias)) {
                        //set field accessible
                        field.setAccessible(true);
                        try {
                            Object value = field.get(entity);
                            columnValueMap.putIfAbsent(idAlias, new ColumnSetValue(id, value, true, false
                                    , idAnnotation.autoIncrease()));
                        } catch (IllegalAccessException e) {
                            //ignore
                        }
                    }
                } else if (field.isAnnotationPresent(DbColumn.class)) {
                    DbColumn columnAnnotation = field.getAnnotation(DbColumn.class);
                    Column column = new Column(dbTable, columnAnnotation.name());
                    String columnAlias = column.getNameWithTableAlias();
                    if (columnSet.contains(columnAlias)) {
                        //set field accessible
                        field.setAccessible(true);
                        try {
                            Object value = field.get(entity);
                            columnValueMap.putIfAbsent(columnAlias, new ColumnSetValue(column, value, false, columnAnnotation.nullable()
                                    , false));
                        } catch (IllegalAccessException e) {
                            //ignore
                        }
                    }

                }
            }
            clazz = clazz.getSuperclass();
        }
        List<ColumnSetValue> columnValues = new ArrayList<>();
        orderList.forEach(column -> columnValues.add(columnValueMap.get(column.getNameWithTableAlias())));
        return new TableColumnSetValues(dbTable, columnValues);
    }

    /**
     * 检查是否为Entity类
     *
     * @param clazz 类
     */
    private void checkEntityClazz(Class clazz) {
        if (!isEntity(clazz)) {
            throw new SqlException(getNotEntityMessage(clazz.getName()));
        }
    }

    /**
     * 判断是否继续遍历
     *
     * @param clazz 类
     * @param depth 当前层级
     * @return 判断结果
     */
    private boolean isContinue(Class clazz, int depth) {
        return clazz != Object.class && depth < MAX_DEPTH;
    }

    /**
     * 判断是否为实体类
     *
     * @param clazz 类
     * @return 判断结果
     */
    private boolean isEntity(Class clazz) {
        return clazz.isAnnotationPresent(DbTable.class);
    }

    /**
     * 非实体类
     *
     * @param clazzName 类名
     * @return 错误信息
     */
    private String getNotEntityMessage(String clazzName) {
        return "Class(" + clazzName + ") is not entity!";
    }
}
