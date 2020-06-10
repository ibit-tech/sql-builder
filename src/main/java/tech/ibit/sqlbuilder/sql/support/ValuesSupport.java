package tech.ibit.sqlbuilder.sql.support;

import org.apache.commons.lang.StringUtils;
import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.CriteriaMaker;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Value Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface ValuesSupport<T> extends SqlSupport<T>, PrepareStatementSupport {

    /**
     * 获取列
     *
     * @return 列
     */
    ListField<Column> getColumn();

    /**
     * 获取值
     *
     * @return 值
     */
    ListField<Object> getValue();

    /**
     * `(column1, column2, ...) VALUES(?, ?, ...)`语句
     *
     * @param columnValues 列和值列表
     * @return SQL对象
     * @see ColumnValue
     */
    default T values(List<? extends ColumnValue> columnValues) {
        columnValues.forEach(this::values);
        return getSql();
    }

    /**
     * `(column1) VALUES(?)`语句
     *
     * @param columnValue 列和值
     * @return SQL对象
     * @see ColumnValue
     */
    default T values(ColumnValue columnValue) {
        getColumn().addItem((Column) columnValue.getColumn());
        getValue().addItem(columnValue.getValue());
        return getSql();
    }

    /**
     * `(column1, column2, ...) VALUES(?, ?, ...)`语句
     *
     * @param columns 列列表
     * @param values  值列表
     * @return SQL对象
     * @see ColumnValue
     */
    default T values(List<Column> columns, List<Object> values) {
        getColumn().addItems(columns);
        getValue().addItems(values);
        return getSql();
    }

    /**
     * 获取列预查询SQL
     *
     * @return 预查询SQL
     */
    default PrepareStatement getColumnPrepareStatement() {
        List<Column> columns = getColumn().getItems();
        if (CollectionUtils.isEmpty(columns)) {
            return PrepareStatement.empty();
        }
        return getPrepareStatement("(", columns, Column::getName, null, ", ", ")");
    }

    /**
     * 获取Value预查询SQL
     *
     * @return 预查询SQL
     */
    default PrepareStatement getValuePrepareStatement() {
        List<Column> columns = getColumn().getItems();
        if (CollectionUtils.isEmpty(columns)) {
            return PrepareStatement.empty();
        }

        List<Object> values = getValue().getItems();
        if (CollectionUtils.isEmpty(values)) {
            return PrepareStatement.empty();
        }

        int columnSize = columns.size();
        int valueSize = values.size();

        List<String> valuesIns = getValueIns(columnSize, valueSize);
        String prepareSql = " VALUES" + StringUtils.join(valuesIns, ", ");

        List<ColumnValue> columnValues = new ArrayList<>();

        for (int i = 0; i < valueSize; i++) {
            columnValues.add(new ColumnValue(columns.get(i % columnSize), values.get(i)));
        }

        return new PrepareStatement(prepareSql, columnValues);
    }


    /**
     * 构造`?`参数
     *
     * @param columnSize 列数量
     * @param totalSize  总参数数量
     * @return ? 列表
     */
    default List<String> getValueIns(int columnSize, int totalSize) {
        List<String> valueIns = new ArrayList<>();
        int size = totalSize / columnSize;
        for (int i = 0; i < size; i++) {
            valueIns.add("(" + CriteriaMaker.getIn(columnSize) + ")");
        }
        return valueIns;
    }

}
