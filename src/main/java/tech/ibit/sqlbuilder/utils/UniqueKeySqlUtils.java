package tech.ibit.sqlbuilder.utils;

import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.converter.EntityConverter;
import tech.ibit.sqlbuilder.converter.TableColumnInfo;
import tech.ibit.sqlbuilder.converter.TableColumnSetValues;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.DeleteSql;
import tech.ibit.sqlbuilder.sql.QuerySql;
import tech.ibit.sqlbuilder.sql.UpdateSql;
import tech.ibit.sqlbuilder.sql.support.WhereSupport;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Unique Key SQL 工具类
 *
 * @author iBit程序猿
 */
public class UniqueKeySqlUtils {

    private UniqueKeySqlUtils() {
    }

    /**
     * 构造通过 uniqueKey 查询对象的SQL参数对象
     *
     * @param uniqueKey uniqueKey
     * @param poClazz   查询类
     * @param <T>       模板类型
     * @return SQL参数对象
     */
    public static <T> QuerySql getByUniqueKey(Class<T> poClazz, UniqueKey uniqueKey) {
        return getByUniqueKeys(poClazz, null == uniqueKey ? null : Collections.singleton(uniqueKey));
    }

    /**
     * 构造通过 uniqueKey 查询对象的SQL参数对象
     *
     * @param uniqueKeys uniqueKey 列表
     * @param poClazz    查询类
     * @param <T>        模板类型
     * @return SQL参数对象
     */
    public static <T> QuerySql getByUniqueKeys(Class<?> poClazz
            , Collection<UniqueKey> uniqueKeys) {

        if (CollectionUtils.isEmpty(uniqueKeys)) {
            throw SqlException.uniqueKeyValueNotFound();
        }
        TableColumnInfo table = EntityConverter.getTableColumns(poClazz);
        QuerySql sql = SqlFactory.createQuery()
                .from(table.getTable())
                .column(table.getColumns())
                .limit(uniqueKeys.size());
        addWhere(sql, uniqueKeys);
        return sql;
    }

    /**
     * 构造通过 unique key 删除对象的SQL对象参数
     *
     * @param poClazz    实体类
     * @param uniqueKeys 主键对象列表
     * @param <T>        实体类类型
     * @return SQL参数对象
     * @see DeleteSql
     * @see MultiId
     */
    public static <T> DeleteSql deleteByUniqueKeys(Class<T> poClazz, Collection<UniqueKey> uniqueKeys) {
        if (CollectionUtils.isEmpty(uniqueKeys)) {
            throw SqlException.uniqueKeyValueNotFound();
        }
        TableColumnInfo table = EntityConverter.getTableColumns(poClazz);
        DeleteSql sql = SqlFactory.createDelete()
                .deleteFrom(table.getTable());
        addWhere(sql, uniqueKeys);
        return sql;
    }


    /**
     * 构造通过 unique key 删除对象的SQL对象参数
     *
     * @param poClazz   实体类
     * @param uniqueKey unique key
     * @param <T>       实体类类型
     * @return SQL参数对象
     * @see DeleteSql
     */
    public static <T> DeleteSql deleteByUniqueKey(Class<T> poClazz, UniqueKey uniqueKey) {
        return deleteByUniqueKeys(poClazz, null == uniqueKey ? null : Collections.singletonList(uniqueKey));
    }

    /**
     * 构造通过主键批量更新对象的SQL参数对象（多列作为主键）
     *
     * @param updateObject 更新对象
     * @param idValues     主键对象列表
     * @param <T>          实体类类型
     * @return SQL参数对象
     * @see UpdateSql
     */
    public static <T> UpdateSql updateByUniqueKeys(T updateObject, Collection<UniqueKey> idValues) {
        return updateByUniqueKeys(updateObject, null, idValues);
    }


    /**
     * 构造通过 unique key 批量更新对象指定列的SQL参数对象
     *
     * @param updateObject  更新对象
     * @param uniqueKeys    主键值列表
     * @param updateColumns 指定更新列
     * @param <T>           实体类类型
     * @return Update相关SQLParams
     * @see UpdateSql
     */
    public static <T> UpdateSql updateByUniqueKeys(T updateObject, List<Column> updateColumns, Collection<UniqueKey> uniqueKeys) {
        if (CollectionUtils.isEmpty(uniqueKeys)) {
            throw SqlException.uniqueKeyValueNotFound();
        }

        TableColumnSetValues tableColumnValues = null == updateColumns
                ? EntityConverter.getTableColumnValues(updateObject, false)
                : EntityConverter.getTableColumnValues(updateObject, updateColumns);

        UpdateSql sql = SqlFactory
                .createUpdate()
                .update(tableColumnValues.getTable());
        IdSqlUtils.addSetsSql(tableColumnValues, sql);
        addWhere(sql, uniqueKeys);
        return sql;
    }

    /**
     * 增加 where 条件
     *
     * @param sql        sql
     * @param uniqueKeys unique keys
     */
    private static void addWhere(WhereSupport<?> sql, Collection<UniqueKey> uniqueKeys) {
        Column uniqueColumn = getCheckUniqueColumn(uniqueKeys);
        if (null != uniqueColumn) {
            Set<Object> values = new HashSet<>(uniqueKeys.size());
            uniqueKeys.forEach(uniqueKey -> {
                values.add(uniqueKey.getColumnValues().get(0).getValue());
            });
            sql.andWhere(uniqueColumn.in(values));
            return;
        }

        if (uniqueKeys.size() == 1) {
            UniqueKey uniqueKey = uniqueKeys.stream().findFirst().get();
            List<ColumnValue> columnValues = uniqueKey.getColumnValues();
            columnValues.forEach(
                    columnValue -> {
                        Column column = (Column) columnValue.getColumn();
                        Object value = columnValue.getValue();
                        sql.andWhere(column.eq(value));
                    }
            );
            return;
        }

        uniqueKeys.forEach(uniqueKey -> {
            List<ColumnValue> columnValues = uniqueKey.getColumnValues();
            List<CriteriaItem> items = columnValues.stream().map(
                    columnValue -> {
                        Column column = (Column) columnValue.getColumn();
                        Object value = columnValue.getValue();
                        return column.eq(value);
                    }
            ).collect(Collectors.toList());
            sql.orWhere(Criteria.ands(items));
        });
    }

    /**
     * 获取唯一的列，如果为null，则列不唯一
     *
     * @param uniqueKeys unique keys
     * @return 唯一列
     */
    private static Column getCheckUniqueColumn(Collection<UniqueKey> uniqueKeys) {
        Column column = null;
        for (UniqueKey uniqueKey : uniqueKeys) {
            if (null == uniqueKey || CollectionUtils.isEmpty(uniqueKey.getColumnValues())) {
                throw SqlException.uniqueKeyValueNotFound();
            }
            if (uniqueKey.getColumnValues().size() > 1) {
                return null;
            }
            Column firstColumn = (Column) uniqueKey.getColumnValues().get(0).getColumn();
            if (null == column) {
                column = firstColumn;
            } else if (!column.equals(firstColumn)) {
                return null;
            }
        }
        return column;
    }

}
