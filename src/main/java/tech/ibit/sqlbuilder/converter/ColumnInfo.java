package tech.ibit.sqlbuilder.converter;

import tech.ibit.sqlbuilder.Column;

/**
 * 列信息
 *
 * @author iBit程序猿
 * mailto: ibit_tech@aliyun.com
 */
public class ColumnInfo {

    /**
     * 列
     */
    private Column column;

    /**
     * 是否为id
     */
    private boolean id;

    /**
     * 是否允许为空
     */
    private boolean nullable;

    /**
     * 构造函数
     *
     * @param column   列
     * @param id       是否为id
     * @param nullable 是否可以为null
     */
    public ColumnInfo(Column column, boolean id, boolean nullable) {
        this.column = column;
        this.id = id;
        this.nullable = nullable;
    }

    /**
     * 无参构造函数
     */
    public ColumnInfo() {
    }

    /**
     * Gets the value of column
     *
     * @return the value of column
     */
    public Column getColumn() {
        return column;
    }

    /**
     * Sets the column
     * <p>You can use getColumn() to get the value of column</p>
     *
     * @param column column
     */
    public void setColumn(Column column) {
        this.column = column;
    }

    /**
     * Gets the value of id
     *
     * @return the value of id
     */
    public boolean isId() {
        return id;
    }

    /**
     * Sets the id
     * <p>You can use getId() to get the value of id</p>
     *
     * @param id id
     */
    public void setId(boolean id) {
        this.id = id;
    }

    /**
     * Gets the value of nullable
     *
     * @return the value of nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * Sets the nullable
     * <p>You can use getNullable() to get the value of nullable</p>
     *
     * @param nullable nullable
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
