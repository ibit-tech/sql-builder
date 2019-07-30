package tech.ibit.sqlbuilder;

/**
 * HavingItem创建类
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class HavingItemMaker {

    private HavingItemMaker() {
    }

    /**
     * 构造"相等"条件内容
     *
     * @param columnName 列名
     * @param value      值
     * @return 条件内容
     */
    public static HavingItem equalsTo(String columnName, Object value) {
        return new HavingItem(columnName, Operator.EQUALS, value);
    }

    /**
     * 构造"不等"条件内容
     *
     * @param columnName 列名
     * @param value      值
     * @return 条件内容
     */
    public static HavingItem notEqualsTo(String columnName, Object value) {
        return new HavingItem(columnName, Operator.NOT_EQUALS, value);
    }

    /**
     * 构造"大于"条件内容
     *
     * @param columnName 列名
     * @param value      值
     * @return 条件内容
     */
    public static HavingItem greaterThan(String columnName, Object value) {
        return new HavingItem(columnName, Operator.GREATER, value);
    }

    /**
     * 构造"大于等于"条件内容
     *
     * @param columnName 列名
     * @param value      值
     * @return 条件内容
     */
    public static HavingItem greaterThanOrEqualsTo(String columnName, Object value) {
        return new HavingItem(columnName, Operator.GREATER_OR_EQUALS, value);
    }


    /**
     * 构造"小于"条件内容
     *
     * @param columnName 列名
     * @param value      值
     * @return 条件内容
     */
    public static HavingItem lessThan(String columnName, Object value) {
        return new HavingItem(columnName, Operator.LESS, value);
    }

    /**
     * 构造"小于等于"条件内容
     *
     * @param columnName 列名
     * @param value      值
     * @return 条件内容
     */
    public static HavingItem lessThanOrEqualTo(String columnName, Object value) {
        return new HavingItem(columnName, Operator.LESS_OR_EQUALS, value);
    }

}
