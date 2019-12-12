package tech.ibit.sqlbuilder.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DAO工具类
 *
 * @author IBIT TECH
 * @version 1.0
 */
@UtilityClass
public class DaoUtils {

    /**
     * 构造通过主键查询对象的SQL参数对象（单列作为主键）
     *
     * @param poClazz  返回实体类
     * @param idValues 主键值集合
     * @param <P>      返回实体类类型
     * @return SQL参数对象
     * @see SqlParams
     */
    public <P> SqlParams getByIds(Class<P> poClazz, Collection<?> idValues) {
        if (CollectionUtils.isEmpty(idValues)) {
            throw new IdValueNotFoundException();
        }
        TableColumnInfo table = getAndCheckTableIdInfo(poClazz);
        if (table.getIds().size() > 1) {
            throw new MultiIdNotSupportedException(table.getTable().getName());
        }
        return getById(table, idValues);
    }

    /**
     * 构造通过主键查询对象的SQL参数对象（单列作为主键）
     *
     * @param poClazz 返回实体类
     * @param idValue 主键值
     * @param <P>     返回实体类类型
     * @return SQL参数对象
     * @see SqlParams
     */
    public <P> SqlParams getById(Class<P> poClazz, Object idValue) {
        return getByIds(poClazz, null == idValue ? null : Collections.singletonList(idValue));
    }

    /**
     * 构造通过主键查询对象的SQL参数对象（多列作为主键）
     *
     * @param poClazz  返回实体类
     * @param idValues 主键值列表
     * @param <P>      返回实体类类型
     * @return SQL参数对象
     * @see SqlParams
     * @see MultiId
     */
    public <P> SqlParams getByMultiIds(Class<P> poClazz, List<? extends MultiId> idValues) {
        if (CollectionUtils.isEmpty(idValues)) {
            throw new IdValueNotFoundException();
        }
        TableColumnInfo tableColumnInfo = getAndCheckTableIdInfo(poClazz);
        List<Column> ids = tableColumnInfo.getIds();
        List<TableColumnSetValues> columnValuesList = EntityConverter
                .getTableColumnValuesList(new ArrayList<>(idValues), ids);
        if (1 == ids.size()) {
            return getById(tableColumnInfo, getIdValues(ids.get(0), columnValuesList));
        }

        Sql sql = new Sql()
                .select(tableColumnInfo.getColumns())
                .from(tableColumnInfo.getTable());
        appendWhereSql(columnValuesList, sql);
        sql.limit(idValues.size());
        return sql.getSqlParams();
    }

    /**
     * 构造通过主键查询对象的SQL参数对象（多列作为主键）
     *
     * @param poClazz 返回实体类
     * @param idValue 主键值
     * @param <P>     返回实体类类型
     * @return SQL参数对象
     * @see SqlParams
     * @see MultiId
     */
    public <P> SqlParams getByMultiId(Class<P> poClazz, MultiId idValue) {
        return getByMultiIds(poClazz, null == idValue ? null : Collections.singletonList(idValue));
    }


    /**
     * 构造通过主键删除对象的SQL对象参数（单列作为主键）
     *
     * @param poClazz  返回实体类类
     * @param idValues 主键值列表
     * @param <P>      返回实体类类型
     * @return SQL参数对象
     * @see SqlParams
     */
    public <P> SqlParams deleteByIds(Class<P> poClazz, Collection<?> idValues) {
        if (CollectionUtils.isEmpty(idValues)) {
            throw new IdValueNotFoundException();
        }
        TableColumnInfo tableIdInfo = getAndCheckTableIdInfo(poClazz);
        if (tableIdInfo.getIds().size() > 1) {
            throw new MultiIdNotSupportedException(tableIdInfo.getTable().getName());
        }
        Column id = tableIdInfo.getIds().get(0);
        return new Sql()
                .deleteFrom(tableIdInfo.getTable())
                .andWhere(CriteriaItemMaker.in(id, idValues))
                .getSqlParams();
    }

    /**
     * 构造通过主键删除对象的SQL对象参数（单列作为主键）
     *
     * @param poClazz 返回实体类类
     * @param idValue 主键值
     * @param <P>     返回实体类类型
     * @return SQL参数对象
     * @see SqlParams
     */
    public <P> SqlParams deleteById(Class<P> poClazz, Object idValue) {
        return deleteByIds(poClazz, null == idValue ? null : Collections.singletonList(idValue));
    }

    /**
     * 构造通过主键删除对象的SQL对象参数（多列作为主键）
     *
     * @param idValues 主键对象列表
     * @return SQL参数对象
     * @see SqlParams
     * @see MultiId
     */
    public SqlParams deleteByMultiIds(List<? extends MultiId> idValues) {
        if (CollectionUtils.isEmpty(idValues)) {
            throw new IdValueNotFoundException();
        }
        List<TableColumnSetValues> idValueList = EntityConverter.getTableColumnValuesList(
                new ArrayList<>(idValues), true);
        TableColumnSetValues firstIdValues = idValueList.get(0);

        //没有主键
        if (firstIdValues.getColumnValues().isEmpty()) {

            throw new IdNotFoundException(firstIdValues.getTable().getName());

        } else if (1 == firstIdValues.getColumnValues().size()) {
            //single id

            Column id = firstIdValues.getColumnValues().get(0).getColumn();
            List<Object> actualIdValues = getIdValues(idValueList);

            return new Sql()
                    .deleteFrom(firstIdValues.getTable())
                    .andWhere(CriteriaItemMaker.in(id, actualIdValues))
                    .getSqlParams();
        }

        Sql sql = new Sql()
                .deleteFrom(firstIdValues.getTable());
        appendWhereSql(idValueList, sql);
        return sql.getSqlParams();
    }


    /**
     * 构造通过主键删除对象的SQL对象参数（多列作为主键）
     *
     * @param idValue 主键对象
     * @return SQL参数对象
     * @see SqlParams
     * @see MultiId
     */
    public SqlParams deleteByMultiId(MultiId idValue) {
        return deleteByMultiIds(null == idValue ? null : Collections.singletonList(idValue));
    }


    /**
     * 构造插入对象的SQL对象参数
     *
     * @param po 插入对象
     * @return SQL参数对象
     * @see SqlParams
     */
    public SqlParams insertInto(Object po) {

        // 逻辑，1）只插入非null字段，2）如果字段不允许为null，值为null报错，3）id不允许为null
        TableColumnSetValues entity = EntityConverter.getTableColumnValues(po, true);
        if (entity.getColumnValues().isEmpty()) {
            throw new ColumnValueNotFoundException();
        }
        List<ColumnSetValue> columnValues2Insert = getFilterColumnSetValues(entity.getColumnValues());
        if (columnValues2Insert.isEmpty()) {
            throw new ColumnValueNotFoundException();
        }
        return new Sql()
                .insertInto(entity.getTable())
                .values(columnValues2Insert)
                .getSqlParams();
    }

    /**
     * 获取过滤的是指值
     *
     * @param columnSetValues 待设置值
     * @return 过滤后可插入的值
     */
    private static List<ColumnSetValue> getFilterColumnSetValues(List<ColumnSetValue> columnSetValues) {

        List<ColumnSetValue> columnValues2Insert = new ArrayList<>();
        columnSetValues.forEach(columnSetValue -> {
            Column column = columnSetValue.getColumn();
            Object value = columnSetValue.getValue();
            if (null != value) {
                // 子增长的id不能设置
                if (columnSetValue.isAutoIncrease()) {
                    throw new IdAutoIncreaseException(column.getTable().getName(), column.getName());
                }
                columnValues2Insert.add(columnSetValue);
            } else {
                // 需要判断值是否可以为null
                if (isColumnNotNullable(columnSetValue)) {
                    throw new ColumnNullPointerException(column.getTable().getName(), column.getName());
                }
            }
        });
        return columnValues2Insert;
    }

    /**
     * 判断字段是否不能为null
     *
     * @param columnSetValue 设置列-值
     * @return 判断结果
     */
    private boolean isColumnNotNullable(ColumnSetValue columnSetValue) {
        return !columnSetValue.isAutoIncrease() && !columnSetValue.isNullable();
    }

    /**
     * 构造批量插入对象的SQL对象参数
     * SQL语法 : `INSERT INTO table(column1, column2, ...) values(?, ?, ...), (?, ?, ...)`
     *
     * @param pos     返回实体类列表
     * @param columns 需要插入列
     * @return SQL参数对象
     * @see SqlParams
     */
    public SqlParams batchInsertInto(List<?> pos, List<Column> columns) {
        BatchInsertItems batchInsertItems = getBatchInsertItems(pos, columns);
        return new Sql()
                .batchInsertInto(batchInsertItems.getTable())
                .values(batchInsertItems.getColumns(), batchInsertItems.getValues())
                .getSqlParams();
    }

    /**
     * 构造通过主键更新对象的SQL参数对象（支持单列或多列主键）
     *
     * @param updateObject 更新对象
     * @return SQL参数对象
     * @see SqlParams
     */
    public SqlParams updateById(Object updateObject) {
        return updateById(updateObject, null);
    }

    /**
     * 构造通过主键更新对象指定列的SQL参数对象（支持单列或多列主键）
     *
     * @param updateObject  更新对象
     * @param updateColumns 指定更新字段
     * @return SQL参数对象
     * @see SqlParams
     */
    public SqlParams updateById(Object updateObject, List<Column> updateColumns) {
        TableColumnInfo idEntity = getAndCheckTableIdInfo(updateObject.getClass());
        if (null != updateColumns) {
            if (updateColumns.isEmpty()) {
                throw new ColumnValueNotFoundException();
            }
            Set<Column> updateColumnSet = new LinkedHashSet<>(updateColumns);
            updateColumnSet.addAll(idEntity.getIds());
            updateColumns = new ArrayList<>(updateColumnSet);
        }
        TableColumnSetValues tableColumnValues = null == updateColumns
                ? EntityConverter.getTableColumnValues(updateObject, false)
                : EntityConverter.getTableColumnValues(updateObject, updateColumns);

        //检查主键是否为空
        checkIdNotNull(idEntity.getIds(), tableColumnValues.getColumnValues());

        Sql sql = new Sql()
                .update(idEntity.getTable());
        for (ColumnSetValue cv : tableColumnValues.getColumnValues()) {
            Column column = cv.getColumn();
            Object value = cv.getValue();
            if (cv.isId()) {
                if (null == value) {
                    throw new IdNullPointerException(idEntity.getTable().getName(), column.getName());
                }
                sql.andWhere(CriteriaItemMaker.equalsTo(column, value));
            } else {
                if (!cv.isNullable() && null == value) {
                    throw new ColumnNullPointerException(idEntity.getTable().getName(), column.getName());
                }
                sql.set(cv);
            }
        }
        return sql.getSqlParams();
    }


    /**
     * 构造通过主键批量更新对象的SQL参数对象（单列作为主键）
     *
     * @param updateObject 更新对象
     * @param idValues     主键值列表
     * @return SQL参数对象
     * @see SqlParams
     */
    public SqlParams updateByIds(Object updateObject, Collection<?> idValues) {
        return updateByIds(updateObject, null, idValues);
    }

    /**
     * 构造通过主键批量更新对象指定列的SQL参数对象（单列作为主键）
     *
     * @param updateObject  更新对象
     * @param idValues      主键值列表
     * @param updateColumns 指定更新列
     * @return SQL参数对象
     * @see SqlParams
     */
    public SqlParams updateByIds(Object updateObject, List<Column> updateColumns, Collection<?> idValues) {
        if (CollectionUtils.isEmpty(idValues)) {
            throw new IdValueNotFoundException();
        }
        TableColumnInfo idEntity = getAndCheckTableIdInfo(updateObject.getClass());
        if (idEntity.getIds().size() > 1) {
            throw new MultiIdNotSupportedException(idEntity.getTable().getName());
        }

        TableColumnSetValues tableColumnValues = null == updateColumns
                ? EntityConverter.getTableColumnValues(updateObject, false)
                : EntityConverter.getTableColumnValues(updateObject, updateColumns);

        Table table = idEntity.getTable();
        Sql sql = new Sql()
                .update(table);
        addSetsSql(table, tableColumnValues, sql);
        sql.andWhere(CriteriaItemMaker.in(idEntity.getIds().get(0), idValues));
        return sql.getSqlParams();
    }

    /**
     * 构造通过主键批量更新对象的SQL参数对象（多列作为主键）
     *
     * @param updateObject 更新对象
     * @param idValues     主键对象列表
     * @return SQL参数对象
     * @see SqlParams
     * @see MultiId
     */
    public SqlParams updateByMultiIds(Object updateObject, List<? extends MultiId> idValues) {
        return updateByMultiIds(updateObject, null, idValues);
    }


    /**
     * 构造通过主键批量更新对象指定列的SQL参数对象（多列作为主键）
     *
     * @param updateObject  更新对象
     * @param idValues      主键值列表
     * @param updateColumns 指定更新列
     * @return Update相关SQLParams
     * @see SqlParams
     * @see MultiId
     */
    public SqlParams updateByMultiIds(Object updateObject, List<Column> updateColumns, List<? extends MultiId> idValues) {
        if (CollectionUtils.isEmpty(idValues)) {
            throw new IdValueNotFoundException();
        }
        List<TableColumnSetValues> idValueList = EntityConverter.getTableColumnValuesList(
                new ArrayList<>(idValues), true);
        TableColumnSetValues firstIdValues = idValueList.get(0);

        Table table = firstIdValues.getTable();
        if (firstIdValues.getColumnValues().isEmpty()) {
            throw new IdNotFoundException(table.getName());
        }

        TableColumnSetValues tableColumnValues = null == updateColumns
                ? EntityConverter.getTableColumnValues(updateObject, false)
                : EntityConverter.getTableColumnValues(updateObject, updateColumns);

        Sql sql = new Sql()
                .update(table);
        addSetsSql(table, tableColumnValues, sql);
        //只有一个id
        if (1 == firstIdValues.getColumnValues().size()) {
            Column id = firstIdValues.getColumnValues().get(0).getColumn();
            sql.andWhere(CriteriaItemMaker.in(id, getIdValues(idValueList)));
        } else {
            appendWhereSql(idValueList, sql);
        }
        return sql.getSqlParams();
    }

    /**
     * 检查主键是否为null
     *
     * @param ids             主键离列表
     * @param columnSetValues 列-值对
     */
    private void checkIdNotNull(List<Column> ids, List<ColumnSetValue> columnSetValues) {
        Map<String, Object> idValueMap = new LinkedHashMap<>();
        ids.forEach(id -> idValueMap.put(id.getNameWithTableAlias(), null));
        columnSetValues.forEach(columnSetValue -> {
            String columnAlias = columnSetValue.getColumn().getNameWithTableAlias();
            if (idValueMap.containsKey(columnAlias)) {
                idValueMap.put(columnAlias, columnSetValue.getValue());
            }
        });
        for (Column id : ids) {
            if (null == idValueMap.get(id.getNameWithTableAlias())) {
                throw new IdNullPointerException(id.getTable().getName(), id.getName());
            }
        }
    }

    /**
     * 扩展`set`语句
     *
     * @param table             表
     * @param tableColumnValues 表列-值信息
     * @param sql               SQL对象
     */
    private void addSetsSql(Table table, TableColumnSetValues tableColumnValues, Sql sql) {
        for (ColumnSetValue cv : tableColumnValues.getColumnValues()) {
            Column column = cv.getColumn();
            Object value = cv.getValue();
            //id不能更新
            if (cv.isId()) {
                throw new IdInvalidUpdateException(table.getName(), column.getName());
            } else {
                if (!cv.isNullable() && null == value) {
                    throw new ColumnNullPointerException(table.getName(), column.getName());
                }
                sql.set(cv);
            }
        }
    }


    /**
     * 构造通过单个主键构造函数
     *
     * @param table    表
     * @param idValues 主键值列表
     * @return
     */
    private SqlParams getById(TableColumnInfo table, Collection<?> idValues) {
        Column id = table.getIds().get(0);
        return new Sql()
                .select(table.getColumns())
                .from(table.getTable())
                .andWhere(CriteriaItemMaker.in(id, idValues))
                .limit(idValues.size()).getSqlParams();
    }


    /**
     * 获取单个主建值列表
     *
     * @param id               主键
     * @param columnValuesList 列-值列表
     * @return 主键值列表
     */
    private List<Object> getIdValues(Column id, List<TableColumnSetValues> columnValuesList) {
        List<Object> idValues = new ArrayList<>(columnValuesList.size());
        for (TableColumnSetValues columnValues : columnValuesList) {
            for (ColumnValue kvPair : columnValues.getColumnValues()) {
                if (kvPair.getColumn().equals(id)) {
                    idValues.add(kvPair.getValue());
                }
            }
        }
        return idValues;
    }

    /**
     * 获取单个主键值列表
     *
     * @param columnValuesList 列-值列表
     * @return 主键值列表
     */
    private List<Object> getIdValues(List<TableColumnSetValues> columnValuesList) {
        List<Object> idValues = new ArrayList<>(columnValuesList.size());
        for (TableColumnSetValues aColumnValuesList : columnValuesList) {
            idValues.add(aColumnValuesList.getColumnValues().get(0).getValue());

        }
        return idValues;
    }

    /**
     * 扩展`where`语句
     *
     * @param columnValuesList 列-值列表
     * @param sql              Sql对象
     */
    private void appendWhereSql(List<TableColumnSetValues> columnValuesList, Sql sql) {
        for (TableColumnSetValues columnValues : columnValuesList) {
            List<ColumnSetValue> cvs = columnValues.getColumnValues();
            if (CollectionUtils.isNotEmpty(cvs)) {
                List<CriteriaItem> items = cvs.stream()
                        .filter(Objects::nonNull)
                        .map(cv -> null == cv.getValue()
                                ? CriteriaItemMaker.isNull(cv.getColumn())
                                : CriteriaItemMaker.equalsTo(cv.getColumn(), cv.getValue()))
                        .collect(Collectors.toList());
                sql.orWhere(Criteria.ands(items));
            }
        }
    }

    /**
     * 获取列信息并检查主键是否为空
     *
     * @param poClazz 实体类
     * @param <P>     返回实体类类型
     * @return 列信息
     */
    private <P> TableColumnInfo getAndCheckTableIdInfo(Class<P> poClazz) {
        TableColumnInfo table = EntityConverter.getTableColumns(poClazz);
        if (CollectionUtils.isEmpty(table.getIds())) {
            throw new IdNotFoundException(table.getTable().getName());
        }
        return table;
    }

    /**
     * 构造批量插入对象
     *
     * @param objs    待插入对象列表
     * @param columns 插入列
     * @return 批量插入对象
     */
    private BatchInsertItems getBatchInsertItems(List<?> objs, List<Column> columns) {
        if (CollectionUtils.isEmpty(columns)) {
            throw new ColumnValueNotFoundException();
        }

        Set<String> filterColumnSet = columns.stream()
                .map(Column::getNameWithTableAlias).collect(Collectors.toCollection(LinkedHashSet::new));

        List<Object> values = new ArrayList<>(objs.size() * columns.size());
        Table table = null;
        for (Object obj : objs) {
            TableColumnSetValues entity = EntityConverter.getTableColumnValues(obj, true);
            if (null == table) {
                table = entity.getTable();
            } else {
                if (!table.equals(entity.getTable())) {
                    throw new TableNotMatchedException(table.getName(), entity.getTable().getName());
                }
            }
            List<ColumnSetValue> columnValues2Insert = getFilterColumnSetValues(entity.getColumnValues(), filterColumnSet);
            columnValues2Insert.forEach(columnValue -> values.add(columnValue.getValue()));
        }
        return new BatchInsertItems(table, columns, values);
    }

    /**
     * 获取过滤的是指值
     *
     * @param columnSetValues 待设置值
     * @return 过滤后可插入的值
     */
    private List<ColumnSetValue> getFilterColumnSetValues(List<ColumnSetValue> columnSetValues
            , Set<String> filterColumnSet) {

        Map<String, ColumnSetValue> columnSetValueMap = new HashMap<>();

        columnSetValues.forEach(columnSetValue -> {

            Column column = columnSetValue.getColumn();
            String columnNameWithTableAlias = column.getNameWithTableAlias();
            Object value = columnSetValue.getValue();

            // 包含该字段
            if (filterColumnSet.contains(columnNameWithTableAlias)) {
                if (null != value) {
                    if (columnSetValue.isAutoIncrease()) {
                        throw new IdAutoIncreaseException(column.getTable().getName(), column.getName());
                    }
                } else {
                    if (isColumnNotNullable(columnSetValue)) {
                        throw new ColumnNullPointerException(column.getTable().getName(), column.getName());
                    }
                }
                columnSetValueMap.put(columnNameWithTableAlias, columnSetValue);
            } else {
                // 字段不存在
                if (isColumnNotNullable(columnSetValue)) {
                    throw new ColumnNullPointerException(column.getTable().getName(), column.getName());
                }
            }
        });

        List<ColumnSetValue> columnValues2Insert = new ArrayList<>();
        filterColumnSet.forEach(column -> columnValues2Insert.add(columnSetValueMap.get(column)));
        return columnValues2Insert;
    }

    /**
     * 批量插入对象
     */
    @AllArgsConstructor
    @Getter
    private class BatchInsertItems {
        /**
         * 表
         */
        private Table table;

        /**
         * 插入列
         */
        private List<Column> columns;

        /**
         * 插入值列表
         */
        private List<Object> values;
    }

}
