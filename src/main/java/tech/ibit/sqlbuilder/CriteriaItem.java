package tech.ibit.sqlbuilder;


import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 条件内容对象
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Getter
public class CriteriaItem {

    /**
     * 第一列
     */
    private Column column;

    /**
     * 第二列
     */
    private Column secondColumn;

    /**
     * 操作符
     */
    private Operator operator;

    /**
     * 第一个值
     */
    private Object value;

    /**
     * 第二个值
     */
    private Object secondValue;

    /**
     * 值类型
     */
    private ValueType valueType;


    /**
     * 构造函数（无值）
     *
     * @param column   列
     * @param operator 操作符
     * @return 条件内容
     */
    CriteriaItem(Column column, Operator operator) {
        this.column = column;
        this.operator = operator;
        valueType = ValueType.NO_VALUE;
    }

    /**
     * 构造函数（两列比较实例）
     *
     * @param column       第一列
     * @param operator     操作符
     * @param secondColumn 第二列
     * @return 条件内容
     */
    CriteriaItem(Column column, Operator operator, Column secondColumn) {
        this.column = column;
        this.operator = operator;
        this.secondColumn = secondColumn;
        valueType = ValueType.COLUMN_COMPARE;
    }

    /**
     * 构造函数（列-值）
     *
     * @param column   列
     * @param operator 操作符
     * @param value    值
     * @return 条件内容
     */
    CriteriaItem(Column column, Operator operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        valueType = ValueType.SINGLE_VALUE;
    }

    /**
     * 构造函数（列-列表）
     *
     * @param column   列
     * @param operator 操作符
     * @param values   值列表
     * @return 条件内容
     */
    CriteriaItem(Column column, Operator operator, Collection<?> values) {
        this.column = column;
        this.operator = operator;
        this.value = values;
        valueType = ValueType.LIST_VALUE;
    }

    /**
     * 构造函数（BETWEEN）
     *
     * @param column      列
     * @param operator    操作符
     * @param value       值
     * @param secondValue 第二个值
     * @return 条件内容
     */
    CriteriaItem(Column column, Operator operator, Object value, Object secondValue) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.secondValue = secondValue;
        valueType = ValueType.BETWEEN_VALUE;
    }

    /**
     * 获取预查询SQL对象
     *
     * @return 预查询SQL对象
     */
    PrepareStatement getPrepareStatement() {
        return getPrepareStatement(false);
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    PrepareStatement<KeyValuePair> getPrepareStatement(boolean useAlias) {
        switch (valueType) {
            case COLUMN_COMPARE:
                return getColumnsComparePrepareStatement(useAlias);
            case NO_VALUE:
                return getNoValuePrepareStatement(useAlias);
            case SINGLE_VALUE:
                return getSingleValuePrepareStatement(useAlias);
            case BETWEEN_VALUE:
                return getBetweenPrepareStatement(useAlias);
            case LIST_VALUE:
                return getListPrepareStatement(useAlias);
            default:
                return null;
        }
    }

    /**
     * 构造两列比较预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 两列比较预查询SQL对象
     */
    private PrepareStatement<KeyValuePair> getColumnsComparePrepareStatement(boolean useAlias) {
        String sql = getColumnName(column, useAlias) + " " + operator.getValue() + " " + getColumnName(secondColumn, useAlias);
        return new PrepareStatement<>(sql, Collections.emptyList());
    }

    /**
     * 构造无值预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 无值预查询SQL对象
     */
    private PrepareStatement<KeyValuePair> getNoValuePrepareStatement(boolean useAlias) {
        String sql = getColumnName(column, useAlias) + " " + operator.getValue();
        return new PrepareStatement<>(sql, Collections.emptyList());
    }

    /**
     * 构造单值预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 单值预查询SQL对象
     */
    private PrepareStatement<KeyValuePair> getSingleValuePrepareStatement(boolean useAlias) {
        String columnName = getColumnName(column, useAlias);
        String sql = columnName + " " + operator.getValue() + " ?";
        return new PrepareStatement<>(sql, Collections.singletonList(new KeyValuePair(columnName, value)));
    }

    /**
     * 构造BETWEEN值预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return BETWEEN值预查询SQL对象
     */
    private PrepareStatement<KeyValuePair> getBetweenPrepareStatement(boolean useAlias) {
        String columnName = getColumnName(column, useAlias);
        String sql = columnName + " " + operator.getValue() + " ? " + operator.getSecondValue() + " ?";
        return new PrepareStatement<>(sql, Arrays.asList(new KeyValuePair(columnName, value), new KeyValuePair(columnName, secondValue)));
    }

    /**
     * 构造列表值预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 列表值预查询SQL对象
     */
    private PrepareStatement<KeyValuePair> getListPrepareStatement(boolean useAlias) {
        Collection<?> values = (Collection<?>) value;
        String columnName = getColumnName(column, useAlias);
        String sql = columnName + " " + operator.getValue() + "(" + CriteriaMaker.getIn(values.size()) + ")";
        List<KeyValuePair> keyValuePairs = values.stream().map(v -> new KeyValuePair(columnName, v)).collect(Collectors.toList());
        return new PrepareStatement<>(sql, keyValuePairs);
    }


    /**
     * 获取列名
     *
     * @param column    列
     * @param userAlias 是否使用别名
     * @return 列名
     */
    protected String getColumnName(Column column, boolean userAlias) {
        return userAlias ? column.getNameWithTableAlias() : column.getName();
    }

    /**
     * 值类型
     */
    private enum ValueType {
        /**
         * 列比较
         */
        COLUMN_COMPARE,

        /**
         * 无值
         */
        NO_VALUE,

        /**
         * 单个值
         */
        SINGLE_VALUE,

        /**
         * BETWEEN值
         */
        BETWEEN_VALUE,

        /**
         * 列表值
         */
        LIST_VALUE,

        ;
    }
}
