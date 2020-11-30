package tech.ibit.sqlbuilder.converter;

import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.Table;

import java.util.List;

/**
 * 列-值信息
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class TableColumnValues {

    /**
     * 表
     */
    private Table table;

    /**
     * 列-值对列表
     */
    private List<ColumnValue> columnValues;

    /**
     * 构造函数
     *
     * @param table        表
     * @param columnValues 列值
     */
    public TableColumnValues(Table table, List<ColumnValue> columnValues) {
        this.table = table;
        this.columnValues = columnValues;
    }

    /**
     * Gets the value of table
     *
     * @return the value of table
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets the table
     * <p>You can use getTable() to get the value of table</p>
     *
     * @param table table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Gets the value of columnValues
     *
     * @return the value of columnValues
     */
    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }

    /**
     * Sets the columnValues
     * <p>You can use getColumnValues() to get the value of columnValues</p>
     *
     * @param columnValues columnValues
     */
    public void setColumnValues(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }
}
