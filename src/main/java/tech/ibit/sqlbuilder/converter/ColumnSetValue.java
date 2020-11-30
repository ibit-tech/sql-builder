package tech.ibit.sqlbuilder.converter;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.ColumnValue;

/**
 * 列-值对（带列额外信息）
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class ColumnSetValue extends ColumnValue {

    /**
     * 是否为主键
     */
    private boolean id;

    /**
     * 是否可为null
     */
    private boolean nullable;

    /**
     * 是否自增长
     */
    private boolean autoIncrease;

    public ColumnSetValue(Column column, Object value, boolean id
            , boolean nullable, boolean autoIncrease) {
        super(column, value);
        this.id = id;
        this.nullable = nullable;
        this.autoIncrease = autoIncrease;
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

    /**
     * Gets the value of autoIncrease
     *
     * @return the value of autoIncrease
     */
    public boolean isAutoIncrease() {
        return autoIncrease;
    }

    /**
     * Sets the autoIncrease
     * <p>You can use getAutoIncrease() to get the value of autoIncrease</p>
     *
     * @param autoIncrease autoIncrease
     */
    public void setAutoIncrease(boolean autoIncrease) {
        this.autoIncrease = autoIncrease;
    }
}
