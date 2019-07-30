package tech.ibit.sqlbuilder;

import tech.ibit.sqlbuilder.annotation.DbColumn;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;
import tech.ibit.sqlbuilder.exception.TableNotFoundException;
import tech.ibit.sqlbuilder.exception.TableNotMatchedException;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 实体转换类
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class EntityConverter {


    /**
     * 定义遍历实体类继承深度
     */
    private static final int MAX_DEPTH = 3;


    /**
     * 获取表的列信息
     *
     * @param poClazz 持久化对象类
     * @return 表的列对象信息
     * @see TableColumnInfo
     */
    public static TableColumnInfo getTableColumns(Class poClazz) {
        if (!isEntity(poClazz)) {
            return null;
        }
        DbTable table = (DbTable) poClazz.getAnnotation(DbTable.class);
        Table dbTable = new Table(table.name(), table.alias());
        int depth = 0;
        Set<String> columns = new LinkedHashSet<>();
        Set<String> ids = new LinkedHashSet<>();
        while (isContinue(poClazz, depth)) {
            depth++;
            Arrays.stream(poClazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(DbColumn.class) || field.isAnnotationPresent(DbId.class))
                    .forEach(field -> {
                        DbColumn dbColumn = field.getAnnotation(DbColumn.class);
                        //simple dbColumn
                        if (null != dbColumn) {
                            columns.add(dbColumn.name());
                        } else {
                            DbId id = field.getAnnotation(DbId.class);
                            if (null != id) {
                                ids.add(id.name());
                                columns.add(id.name());
                            }
                        }
                    });
            poClazz = poClazz.getSuperclass();
        }

        List<Column> idList = new ArrayList<>();
        List<Column> columnList = new ArrayList<>();
        for (String column : columns) {
            Column dbColumn = new Column(dbTable, column);
            columnList.add(dbColumn);
            if (ids.contains(column)) {
                idList.add(dbColumn);
            }
        }
        return new TableColumnInfo(dbTable, idList, columnList);
    }

    /**
     * 获取需要被更新的列
     *
     * @param poClazz 持久化对象类
     * @return 需要被更新的列列表
     */
    public static List<Column> getUpdateColumns(Class poClazz) {
        return getUpdateColumns(poClazz, null);
    }

    /**
     * 获取需要被更新的列
     *
     * @param poClazz       持久化对象类
     * @param ignoreColumns 需要忽略的列
     * @return 需要被更新的列列表
     */
    public static List<Column> getUpdateColumns(Class poClazz, List<Column> ignoreColumns) {
        TableColumnInfo columnInfo = getTableColumns(poClazz);
        if (null == columnInfo) {
            return null;
        }
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
     * 获取需要更新的列信息和相应的值
     *
     * @param po       持久化对象
     * @param nullable 如果为true, 则当列的值为null的时候也返回
     * @return "列-值"信息
     * @see TableColumnValues
     */
    public static TableColumnSetValues getTableColumnValues(Object po, boolean nullable) {
        return getTableColumnValues(po, nullable, null);
    }

    /**
     * 指定列获取需要更新的列信息和相应的值
     *
     * @param po           持久化对象
     * @param columnsOrder 指定列
     * @return "列-值"信息
     */
    public static TableColumnSetValues getTableColumnValues(Object po, List<Column> columnsOrder) {
        return getTableColumnValues(po, false, columnsOrder);
    }

    /**
     * 批量获取需要更新的列信息和相应的值
     *
     * @param pos      持久化对象列表
     * @param nullable 如果为true, 则当列的值为null的时候也返回
     * @return "列-值"信息列表
     * @see TableColumnValues
     */
    public static List<TableColumnSetValues> getTableColumnValuesList(List<Object> pos, boolean nullable) {
        if (null == pos || pos.isEmpty()) {
            return Collections.emptyList();
        }
        return pos.stream()
                .map(entity -> getTableColumnValues(entity, nullable))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 批量指定列获取需要更新的列信息和相应的值
     *
     * @param pos          持久化对象列表
     * @param columnsOrder 指定列
     * @return "列-值"信息列表
     */
    public static List<TableColumnSetValues> getTableColumnValuesList(List<Object> pos, List<Column> columnsOrder) {
        if (null == pos || pos.isEmpty()) {
            return Collections.emptyList();
        }
        return pos.stream()
                .map(entity -> getTableColumnValues(entity, columnsOrder))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 获取自增长id的Setter方法
     *
     * @param poClazz 持久化对象类
     * @return 自增正id的Setter方法
     * @see AutoIncrementIdSetterMethod
     */
    public static AutoIncrementIdSetterMethod getAutoIncrementIdSetterMethod(Class poClazz) {
        if (!isEntity(poClazz)) {
            return null;
        }
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
    public static List<Column> getColumns(Class poClazz) {
        TableColumnInfo tableColumnInfo = getTableColumns(poClazz);
        return null == tableColumnInfo ? Collections.emptyList() : tableColumnInfo.getColumns();
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
    public static <T, P> P copyColumns(T originalObject, Class<P> poClazz) {
        if (null == originalObject) {
            return null;
        }
        if (!originalObject.getClass().isAnnotationPresent(DbTable.class)
                || null == poClazz || !(poClazz.isAnnotationPresent(DbTable.class))) {
            throw new TableNotFoundException();
        }
        DbTable oTable = originalObject.getClass().getAnnotation(DbTable.class);
        DbTable poTable = poClazz.getAnnotation(DbTable.class);
        if (!oTable.name().equals(poTable.name())) {
            throw new TableNotMatchedException(oTable.name(), poTable.name());
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
    public static <T, P> List<P> copyColumns(List<T> originalObjects, Class<P> poClazz) {
        if (null == originalObjects) {
            return null;
        }
        if (originalObjects.isEmpty()) {
            return Collections.emptyList();
        }
        if (!(originalObjects.get(0).getClass().isAnnotationPresent(DbTable.class))
                || null == poClazz || !(poClazz.isAnnotationPresent(DbTable.class))) {
            throw new TableNotFoundException();
        }
        T covertObject = originalObjects.get(0);
        DbTable oTable = covertObject.getClass().getAnnotation(DbTable.class);
        DbTable poTable = poClazz.getAnnotation(DbTable.class);
        if (!oTable.name().equals(poTable.name())) {
            throw new TableNotMatchedException(oTable.name(), poTable.name());
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
    private static Map<String, Method> getFieldGetterMethods(Class clazz) {
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
    private static Map<String, Method> getFieldSetterMethods(Class clazz) {
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
    private static Map<String, Object> getFieldValues(Object objectToConvert, Map<String, Method> fieldGetterMethods) {
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
    private static <P> P convert2Object(Class<P> poClazz, Map<String, Method> fieldSetterMethods
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
     * @param entity    实体
     * @param nullable  是否可置为null
     * @param orderList 列排序列表（指定返回列）
     * @return 列更新信息
     */
    private static TableColumnSetValues getTableColumnValues(Object entity, boolean nullable
            , List<Column> orderList) {
        Class clazz = entity.getClass();
        if (!isEntity(entity.getClass())) {
            return null;
        }
        DbTable table = (DbTable) clazz.getAnnotation(DbTable.class);
        Table dbTable = new Table(table.name(), table.alias());
        int depth = 0;
        Map<String, ColumnSetValue> columnValueMap = new LinkedHashMap<>();
        Set<String> columnSet = null == orderList
                ? null
                : orderList.stream().map(Column::getNameWithTableAlias).collect(Collectors.toSet());
        while (isContinue(clazz, depth)) {

            depth++;
            for (Field field : clazz.getDeclaredFields()) {
                //主键
                if (field.isAnnotationPresent(DbId.class)) {
                    DbId idAnnotation = field.getAnnotation(DbId.class);
                    Column id = new Column(dbTable, idAnnotation.name());
                    String idAlias = id.getNameWithTableAlias();
                    if (null == columnSet || columnSet.contains(idAlias)) {
                        //set field accessible
                        field.setAccessible(true);
                        try {
                            Object value = field.get(entity);
                            if (isNeed(value, nullable, columnSet, idAlias)) {
                                columnValueMap.putIfAbsent(idAlias, new ColumnSetValue(id, value, true, false
                                        , idAnnotation.autoIncrease()));
                            }
                        } catch (IllegalAccessException e) {
                            //ignore
                        }
                    }
                } else if (field.isAnnotationPresent(DbColumn.class)) {
                    DbColumn columnAnnotation = field.getAnnotation(DbColumn.class);
                    Column column = new Column(dbTable, columnAnnotation.name());
                    String columnAlias = column.getNameWithTableAlias();
                    if (null == columnSet || columnSet.contains(columnAlias)) {
                        //set field accessible
                        field.setAccessible(true);
                        try {
                            Object value = field.get(entity);
                            if (isNeed(value, nullable, columnSet, columnAlias)) {
                                columnValueMap.putIfAbsent(columnAlias, new ColumnSetValue(column, value, false, columnAnnotation.nullable()
                                        , false));
                            }
                        } catch (IllegalAccessException e) {
                            //ignore
                        }
                    }

                }
            }
            clazz = clazz.getSuperclass();
        }
        List<ColumnSetValue> columnValues = new ArrayList<>();
        if (null != orderList) {
            orderList.forEach(column -> columnValues.add(columnValueMap.get(column.getNameWithTableAlias())));
        } else {
            columnValueMap.forEach((column, value) -> columnValues.add(value));
        }
        return new TableColumnSetValues(dbTable, columnValues);
    }

    /**
     * 是否需要
     *
     * @param value     值
     * @param nullable  值是否可以为null
     * @param columnSet 列集合
     * @param column    列
     * @return 是否需要
     */
    private static boolean isNeed(Object value, boolean nullable, Set<String> columnSet, String column) {
        return null != value || nullable || (null != columnSet && columnSet.contains(column));
    }

    /**
     * 判断是否继续遍历
     *
     * @param clazz 类
     * @param depth 当前层级
     * @return 判断结果
     */
    private static boolean isContinue(Class clazz, int depth) {
        return clazz != Object.class && depth < MAX_DEPTH;
    }

    /**
     * 判断是否为实体类
     *
     * @param clazz 类
     * @return 判断结果
     */
    private static boolean isEntity(Class clazz) {
        return clazz.isAnnotationPresent(DbTable.class);
    }
}
