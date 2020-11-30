package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.ColumnValue;

import java.util.List;

/**
 * Value Support
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface ValuesSupport<T> {


    /**
     * `(column1, column2, ...) VALUES(?, ?, ...)`语句
     *
     * @param columnValues 列和值列表
     * @return SQL对象
     * @see ColumnValue
     */
    T values(List<? extends ColumnValue> columnValues);

    /**
     * `(column1) VALUES(?)`语句
     *
     * @param columnValue 列和值
     * @return SQL对象
     * @see ColumnValue
     */
    T values(ColumnValue columnValue);

    /**
     * `(column1) VALUES(?)`语句
     *
     * @param column 列
     * @param value  值
     * @return SQL对象
     */
    T values(Column column, Object value);

    /**
     * `(column1, column2, ...) VALUES(?, ?, ...)`语句
     *
     * @param columns 列列表
     * @param values  值列表
     * @return SQL对象
     * @see ColumnValue
     */
    T values(List<Column> columns, List<Object> values);

}
