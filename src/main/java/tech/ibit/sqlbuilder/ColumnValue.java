package tech.ibit.sqlbuilder;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 列-值对
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class ColumnValue {

    /**
     * 列
     */
    private IColumn column;

    /**
     * 值
     */
    private Object value;

    /**
     * 构造函数
     *
     * @param column 列
     * @param value  值
     */
    public ColumnValue(IColumn column, Object value) {
        this.column = column;
        this.value = value;
    }

    /**
     * Gets the value of column
     *
     * @return the value of column
     */
    public IColumn getColumn() {
        return column;
    }

    /**
     * Sets the column
     * <p>You can use getColumn() to get the value of column</p>
     *
     * @param column column
     */
    public void setColumn(IColumn column) {
        this.column = column;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value
     * <p>You can use getValue() to get the value of value</p>
     *
     * @param value value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColumnValue that = (ColumnValue) o;
        return Objects.equals(getColumn(), that.getColumn())
                && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColumn(), getValue());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ColumnValue.class.getSimpleName() + "[", "]")
                .add("column=" + column)
                .add("value=" + value)
                .toString();
    }
}
