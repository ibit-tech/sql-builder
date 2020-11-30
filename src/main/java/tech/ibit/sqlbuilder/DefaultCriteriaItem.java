package tech.ibit.sqlbuilder;


import tech.ibit.sqlbuilder.enums.CriteriaItemValueTypeEnum;
import tech.ibit.sqlbuilder.enums.OperatorEnum;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 条件内容对象
 *
 * @author iBit程序猿
 * @since 2.6
 */
public class DefaultCriteriaItem extends CriteriaItem {

    /**
     * 第一列
     */
    private final IColumn column;

    /**
     * 操作符
     */
    private final OperatorEnum operator;

    /**
     * 第二列
     */
    private final IColumn secondColumn;

    /**
     * 第一个值
     */
    private final Object value;

    /**
     * 第二个值
     */
    private final Object secondValue;

    /**
     * 值类型
     */
    private final CriteriaItemValueTypeEnum valueType;

    /**
     * 条件对象
     *
     * @param column       列
     * @param operator     操作符
     * @param secondColumn 第二列
     * @param value        值
     * @param secondValue  第二个值
     * @param valueType    值类型
     */
    public DefaultCriteriaItem(IColumn column,
                               OperatorEnum operator,
                               IColumn secondColumn,
                               Object value, Object secondValue,
                               CriteriaItemValueTypeEnum valueType) {
        this.column = column;
        this.operator = operator;
        this.secondColumn = secondColumn;
        this.value = value;
        this.secondValue = secondValue;
        this.valueType = valueType;
    }

    /**
     * 构造无值条件
     *
     * @param column   列
     * @param operator 操作符
     * @return 条件内容
     */
    public static CriteriaItem getNoValueInstance(IColumn column, OperatorEnum operator) {
        return new DefaultCriteriaItem(column, operator, null, null, null, CriteriaItemValueTypeEnum.NO_VALUE);
    }

    /**
     * 构造两列比较条件
     *
     * @param column       第一列
     * @param operator     操作符
     * @param secondColumn 第二列
     * @return 条件内容
     */
    public static CriteriaItem getColumnCompareInstance(IColumn column, OperatorEnum operator, IColumn secondColumn) {
        return new DefaultCriteriaItem(column, operator, secondColumn, null, null, CriteriaItemValueTypeEnum.COLUMN_COMPARE);
    }

    /**
     * 构造单值条件
     *
     * @param column   列
     * @param operator 操作符
     * @param value    值
     * @return 条件内容
     */
    public static CriteriaItem getSingleValueInstance(IColumn column, OperatorEnum operator, Object value) {
        return new DefaultCriteriaItem(column, operator, null, value, null, CriteriaItemValueTypeEnum.SINGLE_VALUE);
    }

    /**
     * 构造多值条件
     *
     * @param column   列
     * @param operator 操作符
     * @param values   值列表
     * @return 条件内容
     */
    public static CriteriaItem getMultiValueInstance(IColumn column, OperatorEnum operator, Collection<?> values) {
        return new DefaultCriteriaItem(column, operator, null, values, null, CriteriaItemValueTypeEnum.LIST_VALUE);
    }

    /**
     * 构造between条件
     *
     * @param column      列
     * @param operator    操作符
     * @param value       值
     * @param secondValue 第二个值
     * @return 条件内容
     */
    public static CriteriaItem getBetweenInstance(IColumn column, OperatorEnum operator, Object value, Object secondValue) {
        return new DefaultCriteriaItem(column, operator, null, value, secondValue, CriteriaItemValueTypeEnum.BETWEEN_VALUE);
    }

    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {
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
    private PrepareStatement getColumnsComparePrepareStatement(boolean useAlias) {
        String sql = column.getCompareColumnName(useAlias) + " " + operator.getValue() + " " + secondColumn.getCompareColumnName(useAlias);
        return new PrepareStatement(sql, Collections.emptyList());
    }

    /**
     * 构造无值预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 无值预查询SQL对象
     */
    private PrepareStatement getNoValuePrepareStatement(boolean useAlias) {
        String sql = column.getCompareColumnName(useAlias) + " " + operator.getValue();
        return new PrepareStatement(sql, Collections.emptyList());
    }

    /**
     * 构造单值预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 单值预查询SQL对象
     */
    private PrepareStatement getSingleValuePrepareStatement(boolean useAlias) {
        String sql = column.getCompareColumnName(useAlias) + " " + operator.getValue() + " ?";
        return new PrepareStatement(sql, Collections.singletonList(new ColumnValue(column, value)));
    }

    /**
     * 构造BETWEEN值预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return BETWEEN值预查询SQL对象
     */
    private PrepareStatement getBetweenPrepareStatement(boolean useAlias) {
        String sql = column.getCompareColumnName(useAlias) + " " + operator.getValue() + " ? " + operator.getSecondValue() + " ?";
        return new PrepareStatement(sql, Arrays.asList(new ColumnValue(column, value), new ColumnValue(column, secondValue)));
    }

    /**
     * 构造列表值预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 列表值预查询SQL对象
     */
    private PrepareStatement getListPrepareStatement(boolean useAlias) {
        Collection<?> values = (Collection<?>) value;
        String sql = column.getCompareColumnName(useAlias) + " " + operator.getValue() + "(" + CriteriaMaker.getIn(values.size()) + ")";
        List<ColumnValue> keyValuePairs = values.stream().map(v -> new ColumnValue(column, v)).collect(Collectors.toList());
        return new PrepareStatement(sql, keyValuePairs);
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
     * Gets the value of operator
     *
     * @return the value of operator
     */
    public OperatorEnum getOperator() {
        return operator;
    }

    /**
     * Gets the value of secondColumn
     *
     * @return the value of secondColumn
     */
    public IColumn getSecondColumn() {
        return secondColumn;
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
     * Gets the value of secondValue
     *
     * @return the value of secondValue
     */
    public Object getSecondValue() {
        return secondValue;
    }

    /**
     * Gets the value of valueType
     *
     * @return the value of valueType
     */
    public CriteriaItemValueTypeEnum getValueType() {
        return valueType;
    }
}
