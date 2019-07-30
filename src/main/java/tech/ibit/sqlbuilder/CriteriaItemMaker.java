package tech.ibit.sqlbuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 条件内容创建类
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class CriteriaItemMaker {

    private CriteriaItemMaker() {
    }

    /**
     * 构造"IS NULL"条件内容
     *
     * @param column 列
     * @return 条件内容
     */
    public static CriteriaItem isNull(Column column) {
        return new CriteriaItem(column, Operator.IS_NULL);
    }

    /**
     * 构造"IS NOT NULL"条件内容
     *
     * @param column 列
     * @return 条件内容
     */
    public static CriteriaItem isNotNull(Column column) {
        return new CriteriaItem(column, Operator.IS_NOT_NULL);
    }

    /**
     * 构造"相等"条件内容
     *
     * @param column 列
     * @param value  值
     * @return 条件内容
     */
    public static CriteriaItem equalsTo(Column column, Object value) {
        return new CriteriaItem(column, Operator.EQUALS, value);
    }

    /**
     * 构造"相等"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容
     */
    public static CriteriaItem equalsTo(Column column, Column secondColumn) {
        return new CriteriaItem(column, Operator.EQUALS, secondColumn);
    }

    /**
     * 构造"不等"条件内容
     *
     * @param column 列
     * @param value  值
     * @return 条件内容
     */
    public static CriteriaItem notEqualsTo(Column column, Object value) {
        return new CriteriaItem(column, Operator.NOT_EQUALS, value);
    }

    /**
     * 构造"不等"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容
     */
    public static CriteriaItem notEqualsTo(Column column, Column secondColumn) {
        return new CriteriaItem(column, Operator.NOT_EQUALS, secondColumn);
    }

    /**
     * 构造"大于"条件内容
     *
     * @param column 列
     * @param value  值
     * @return 条件内容
     */
    public static CriteriaItem greaterThan(Column column, Object value) {
        return new CriteriaItem(column, Operator.GREATER, value);
    }

    /**
     * 构造"大于"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容
     */
    public static CriteriaItem greaterThan(Column column, Column secondColumn) {
        return new CriteriaItem(column, Operator.GREATER, secondColumn);
    }

    /**
     * 构造"大于等于"条件内容
     *
     * @param column 列
     * @param value  值
     * @return 条件内容
     */
    public static CriteriaItem greaterThanOrEqualsTo(Column column, Object value) {
        return new CriteriaItem(column, Operator.GREATER_OR_EQUALS, value);
    }

    /**
     * 构造"大于等于"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容
     */
    public static CriteriaItem greaterThanOrEqualsTo(Column column, Column secondColumn) {
        return new CriteriaItem(column, Operator.GREATER_OR_EQUALS, secondColumn);
    }

    /**
     * 构造"小于"条件内容
     *
     * @param column 列
     * @param value  值
     * @return 条件内容
     */
    public static CriteriaItem lessThan(Column column, Object value) {
        return new CriteriaItem(column, Operator.LESS, value);
    }

    /**
     * 构造"小于"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容
     */
    public static CriteriaItem lessThan(Column column, Column secondColumn) {
        return new CriteriaItem(column, Operator.LESS, secondColumn);
    }

    /**
     * 构造"小于等于"条件内容
     *
     * @param column 列
     * @param value  值
     * @return 条件内容
     */
    public static CriteriaItem lessThanOrEqualTo(Column column, Object value) {
        return new CriteriaItem(column, Operator.LESS_OR_EQUALS, value);
    }

    /**
     * 构造"小于等于"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容
     */
    public static CriteriaItem lessThanOrEqualTo(Column column, Column secondColumn) {
        return new CriteriaItem(column, Operator.LESS_OR_EQUALS, secondColumn);
    }

    /**
     * 构造"IN"条件内容
     *
     * @param column 列
     * @param values 值列表
     * @return 条件内容
     */
    public static CriteriaItem in(Column column, Collection<?> values) {
        if (null != values && 1 == values.size()) {
            return equalsTo(column, new ArrayList<>(values).get(0));
        }
        return new CriteriaItem(column, Operator.IN, values);
    }

    /**
     * 构造"NOT IN"条件内容
     *
     * @param column 列
     * @param values 值列表
     * @return 条件内容
     */
    public static CriteriaItem notIn(Column column, Collection<?> values) {
        if (null != values && 1 == values.size()) {
            return notEqualsTo(column, new ArrayList<>(values).get(0));
        }
        return new CriteriaItem(column, Operator.NOT_IN, values);
    }

    /**
     * 构造"BETWEEN AND"条件内容
     *
     * @param column 列
     * @param value1 第一个值
     * @param value2 第二个值
     * @return 条件内容
     */
    public static CriteriaItem between(Column column, Object value1, Object value2) {
        return new CriteriaItem(column, Operator.BETWEEN, value1, value2);
    }

    /**
     * 构造"NOT BETWEEN AND"条件内容
     *
     * @param column 列
     * @param value1 第一个值
     * @param value2 第二个值
     * @return 条件内容
     */
    public static CriteriaItem notBetween(Column column, Object value1, Object value2) {
        return new CriteriaItem(column, Operator.NOT_BETWEEN, value1, value2);
    }

    /**
     * 构造"LIKE"条件内容
     *
     * @param column 列
     * @param value  值
     * @return 条件内容
     */
    public static CriteriaItem like(Column column, String value) {
        return new CriteriaItem(column, Operator.LIKE, value);
    }

    /**
     * 构造"NOT LIKE"条件内容
     *
     * @param column 列
     * @param value  值
     * @return 条件内容
     */
    public static CriteriaItem notLike(Column column, String value) {
        return new CriteriaItem(column, Operator.NOT_LIKE, value);
    }


    /**
     * 构造全包含标记位条件内容
     *
     * @param column 列
     * @param flags  标记位值
     * @return 标记位条件内容
     */
    public static FlagCriteriaItem containsAllFlags(Column column, long flags) {
        return new FlagCriteriaItem(column, FlagCriteriaItem.ContainsType.CONTAINS_ALL, flags);
    }

    /**
     * 构造全不包含标记位条件内容
     *
     * @param column 列
     * @param flags  标记位值
     * @return 标记位条件内容
     */
    public static FlagCriteriaItem containsNoneFlags(Column column, long flags) {
        return new FlagCriteriaItem(column, FlagCriteriaItem.ContainsType.CONTAINS_NONE, flags);
    }

    /**
     * 构造全包含任意标记位条件内容
     *
     * @param column 列
     * @param flags  标记位值
     * @return 标记位条件内容
     */
    public static FlagCriteriaItem containsAnyFlags(Column column, long flags) {
        return new FlagCriteriaItem(column, FlagCriteriaItem.ContainsType.CONTAINS_ANY, flags);
    }
}
