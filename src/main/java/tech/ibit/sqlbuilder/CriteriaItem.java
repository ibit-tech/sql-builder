package tech.ibit.sqlbuilder;


import lombok.AllArgsConstructor;
import lombok.Getter;
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
 * @author IBIT程序猿
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public class CriteriaItem implements PrepareStatementSupplier {

    /**
     * 第一列
     */
    private IColumn column;

    /**
     * 操作符
     */
    private OperatorEnum operator;

    /**
     * 第二列
     */
    private IColumn secondColumn;

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
    private CriteriaItemValueTypeEnum valueType;

    /**
     * 构造无值条件
     *
     * @param column   列
     * @param operator 操作符
     * @return 条件内容
     */
    public static CriteriaItem getNoValueInstance(IColumn column, OperatorEnum operator) {
        return new CriteriaItem(column, operator, null, null, null, CriteriaItemValueTypeEnum.NO_VALUE);
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
        return new CriteriaItem(column, operator, secondColumn, null, null, CriteriaItemValueTypeEnum.COLUMN_COMPARE);
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
        return new CriteriaItem(column, operator, null, value, null, CriteriaItemValueTypeEnum.SINGLE_VALUE);
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
        return new CriteriaItem(column, operator, null, values, null, CriteriaItemValueTypeEnum.LIST_VALUE);
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
        return new CriteriaItem(column, operator, null, value, secondValue, CriteriaItemValueTypeEnum.BETWEEN_VALUE);
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
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
     * 生成or条件
     *
     * @return or条件
     */
    public Criteria or() {
        return Criteria.or(this);
    }

    /**
     * 生成and条件
     *
     * @return and条件
     */
    public Criteria and() {
        return Criteria.and(this);
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

}
