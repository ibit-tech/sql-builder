package tech.ibit.sqlbuilder.sql.support.impl;

import org.apache.commons.lang.StringUtils;
import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.CriteriaMaker;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;
import tech.ibit.sqlbuilder.sql.support.ValuesSupport;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * publicValuesSupport
 *
 * @author iBit程序猿
 */
public class ValuesSupportImpl<T> implements SqlSupport<T>,
        ValuesSupport<T>, PrepareStatementBuildSupport {

    /**
     * sql 对象
     */
    private final T sql;

    /**
     * column
     */
    private final ListField<Column> column;

    /**
     * value
     */
    private final ListField<Object> value;

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public ValuesSupportImpl(T sql) {
        this.sql = sql;
        this.column = new ListField<>();
        this.value = new ListField<>();
    }


    /**
     * 获取列
     *
     * @return 列
     */
    private ListField<Column> getColumn() {
        return column;
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public ListField<Object> getValue() {
        return value;
    }


    @Override
    public T getSql() {
        return sql;
    }


    @Override
    public T values(List<? extends ColumnValue> columnValues) {
        columnValues.forEach(this::values);
        return getSql();
    }

    @Override
    public T values(ColumnValue columnValue) {
        return values((Column) columnValue.getColumn(), columnValue.getValue());
    }

    @Override
    public T values(Column column, Object value) {
        getColumn().addItem(column);
        getValue().addItem(value);
        return getSql();
    }

    @Override
    public T values(List<Column> columns, List<Object> values) {
        getColumn().addItems(columns);
        getValue().addItems(values);
        return getSql();
    }

    /**
     * 获取列预查询SQL
     *
     * @return 预查询SQL
     */
    public PrepareStatement getColumnPrepareStatement() {
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
    public PrepareStatement getValuePrepareStatement() {
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
    private List<String> getValueIns(int columnSize, int totalSize) {
        List<String> valueIns = new ArrayList<>();
        int size = totalSize / columnSize;
        for (int i = 0; i < size; i++) {
            valueIns.add("(" + CriteriaMaker.getIn(columnSize) + ")");
        }
        return valueIns;
    }


}
