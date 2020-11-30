package tech.ibit.sqlbuilder.sql.field;

/**
 * Distinct 字段
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class BooleanField {

    /**
     * boolean 值
     */
    private boolean value;

    /**
     * 构造函数
     */
    public BooleanField() {
    }

    /**
     * 复制
     *
     * @return 新的BooleanField
     */
    public BooleanField copy() {
        return new BooleanField(value);
    }

    /**
     * 构造函数
     *
     * @param value boolean 值
     */
    public BooleanField(boolean value) {
        this.value = value;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public boolean isValue() {
        return value;
    }

    /**
     * Sets the value
     * <p>You can use getValue() to get the value of value</p>
     *
     * @param value value
     */
    public void setValue(boolean value) {
        this.value = value;
    }
}
