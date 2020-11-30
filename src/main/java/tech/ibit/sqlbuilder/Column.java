package tech.ibit.sqlbuilder;

import tech.ibit.sqlbuilder.column.support.*;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 列定义
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class Column implements IColumn,
        IColumnCriteriaItemSupport,
        IColumnAggregateSupport,
        IColumnFullTextSupport,
        IColumnSetItemSupport,
        IColumnOrderBySupport,
        IColumnUniqueKeySupport {

    /**
     * 表
     */
    private Table table;

    /**
     * 列名
     */
    private String name;

    /**
     * 构造函数
     *
     * @param table 表
     * @param name  列名
     */
    public Column(Table table, String name) {
        this.table = table;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Column column = (Column) o;
        return Objects.equals(getTable(), column.getTable())
                && Objects.equals(getName(), column.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTable(), getName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Column.class.getSimpleName() + "[", "]")
                .add("table=" + table)
                .add("name='" + name + "'")
                .toString();
    }

    @Override
    public String getNameWithTableAlias() {
        return table.getAlias() + "." + name;
    }

    @Override
    public String getNameAs() {
        // 这里不定义别名
        return null;
    }

    @Override
    public IColumn getColumn() {
        return this;
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

    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * <p>You can use getName() to get the value of name</p>
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
}
