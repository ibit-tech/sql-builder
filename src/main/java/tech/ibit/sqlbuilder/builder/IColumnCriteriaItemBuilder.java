package tech.ibit.sqlbuilder.builder;

import tech.ibit.sqlbuilder.CriteriaItem;
import tech.ibit.sqlbuilder.FlagCriteriaItem;
import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.enums.OperatorEnum;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 条件内容创建类
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface IColumnCriteriaItemBuilder {

    /**
     * 获取列
     *
     * @return 列
     */
    IColumn getColumn();

    /**
     * 构造"IS NULL"条件内容
     *
     * @return 条件内容
     */
    default CriteriaItem isNull() {
        return CriteriaItem.getNoValueInstance(getColumn(), OperatorEnum.IS_NULL);
    }

    /**
     * 构造"IS NOT NULL"条件内容
     *
     * @return 条件内容
     */
    default CriteriaItem isNotNull() {
        return CriteriaItem.getNoValueInstance(getColumn(), OperatorEnum.IS_NOT_NULL);
    }


    /**
     * 构造"= ''"条件内容
     *
     * @return 条件内容
     */
    default CriteriaItem isEmpty() {
        return CriteriaItem.getNoValueInstance(getColumn(), OperatorEnum.IS_EMPTY);
    }

    /**
     * 构造"不为空"条件内容
     *
     * @return 条件内容
     */
    default CriteriaItem isNotEmpty() {
        return CriteriaItem.getNoValueInstance(getColumn(), OperatorEnum.IS_NOT_EMPTY);
    }

    /**
     * 构造"相等"条件内容
     *
     * @param value 值
     * @return 条件内容
     */
    default CriteriaItem eq(Object value) {
        return CriteriaItem.getSingleValueInstance(getColumn(), OperatorEnum.EQ, value);
    }

    /**
     * 构造"相等"条件内容
     *
     * @param secondColumn 第二列
     * @return 条件内容
     */
    default CriteriaItem eq(IColumn secondColumn) {
        return CriteriaItem.getColumnCompareInstance(getColumn(), OperatorEnum.EQ, secondColumn);
    }

    /**
     * 构造"不等"条件内容
     *
     * @param value 值
     * @return 条件内容
     */
    default CriteriaItem neq(Object value) {
        return CriteriaItem.getSingleValueInstance(getColumn(), OperatorEnum.NEQ, value);
    }

    /**
     * 构造"不等"条件内容
     *
     * @param secondColumn 第二列
     * @return 条件内容
     */
    default CriteriaItem neq(IColumn secondColumn) {
        return CriteriaItem.getColumnCompareInstance(getColumn(), OperatorEnum.NEQ, secondColumn);
    }

    /**
     * 构造"大于"条件内容
     *
     * @param value 值
     * @return 条件内容
     */
    default CriteriaItem gt(Object value) {
        return CriteriaItem.getSingleValueInstance(getColumn(), OperatorEnum.GT, value);
    }

    /**
     * 构造"大于"条件内容
     *
     * @param secondColumn 第二列
     * @return 条件内容
     */
    default CriteriaItem gt(IColumn secondColumn) {
        return CriteriaItem.getColumnCompareInstance(getColumn(), OperatorEnum.GT, secondColumn);
    }

    /**
     * 构造"大于等于"条件内容
     *
     * @param value 值
     * @return 条件内容
     */
    default CriteriaItem egt(Object value) {
        return CriteriaItem.getSingleValueInstance(getColumn(), OperatorEnum.EGT, value);
    }

    /**
     * 构造"大于等于"条件内容
     *
     * @param secondColumn 第二列
     * @return 条件内容
     */
    default CriteriaItem egt(IColumn secondColumn) {
        return CriteriaItem.getColumnCompareInstance(getColumn(), OperatorEnum.EGT, secondColumn);
    }

    /**
     * 构造"小于"条件内容
     *
     * @param value 值
     * @return 条件内容
     */
    default CriteriaItem lt(Object value) {
        return CriteriaItem.getSingleValueInstance(getColumn(), OperatorEnum.LT, value);
    }

    /**
     * 构造"小于"条件内容
     *
     * @param secondColumn 第二列
     * @return 条件内容
     */
    default CriteriaItem lt(IColumn secondColumn) {
        return CriteriaItem.getColumnCompareInstance(getColumn(), OperatorEnum.LT, secondColumn);
    }

    /**
     * 构造"小于等于"条件内容
     *
     * @param value 值
     * @return 条件内容
     */
    default CriteriaItem elt(Object value) {
        return CriteriaItem.getSingleValueInstance(getColumn(), OperatorEnum.ELT, value);
    }

    /**
     * 构造"小于等于"条件内容
     *
     * @param secondColumn 第二列
     * @return 条件内容
     */
    default CriteriaItem elt(IColumn secondColumn) {
        return CriteriaItem.getColumnCompareInstance(getColumn(), OperatorEnum.ELT, secondColumn);
    }

    /**
     * 构造"IN"条件内容
     *
     * @param values 值列表
     * @return 条件内容
     */
    default CriteriaItem in(Collection<?> values) {
        if (null != values && 1 == values.size()) {
            return eq(new ArrayList<>(values).get(0));
        }
        return CriteriaItem.getMultiValueInstance(getColumn(), OperatorEnum.IN, values);
    }

    /**
     * 构造"NOT IN"条件内容
     *
     * @param values 值列表
     * @return 条件内容
     */
    default CriteriaItem notIn(Collection<?> values) {
        if (null != values && 1 == values.size()) {
            return neq(new ArrayList<>(values).get(0));
        }
        return CriteriaItem.getMultiValueInstance(getColumn(), OperatorEnum.NOT_IN, values);
    }

    /**
     * 构造"BETWEEN AND"条件内容
     *
     * @param value1 第一个值
     * @param value2 第二个值
     * @return 条件内容
     */
    default CriteriaItem between(Object value1, Object value2) {
        return CriteriaItem.getBetweenInstance(getColumn(), OperatorEnum.BETWEEN, value1, value2);
    }

    /**
     * 构造"NOT BETWEEN AND"条件内容
     *
     * @param value1 第一个值
     * @param value2 第二个值
     * @return 条件内容
     */
    default CriteriaItem notBetween(Object value1, Object value2) {
        return CriteriaItem.getBetweenInstance(getColumn(), OperatorEnum.NOT_BETWEEN, value1, value2);
    }

    /**
     * 构造"LIKE"条件内容
     *
     * @param value 值
     * @return 条件内容
     */
    default CriteriaItem like(String value) {
        return CriteriaItem.getSingleValueInstance(getColumn(), OperatorEnum.LIKE, value);
    }

    /**
     * 构造"NOT LIKE"条件内容
     *
     * @param value 值
     * @return 条件内容
     */
    default CriteriaItem notLike(String value) {
        return CriteriaItem.getSingleValueInstance(getColumn(), OperatorEnum.NOT_LIKE, value);
    }


    /**
     * 构造全包含标记位条件内容（扩展）
     *
     * @param flags 标记位值
     * @return 标记位条件内容
     */
    default CriteriaItem allFlgs(long flags) {
        return FlagCriteriaItem.getInstance(getColumn(), FlagCriteriaItem.ContainsType.CONTAINS_ALL, flags);
    }

    /**
     * 构造全不包含标记位条件内容（扩展）
     *
     * @param flags 标记位值
     * @return 标记位条件内容
     */
    default CriteriaItem noFlgs(long flags) {
        return FlagCriteriaItem.getInstance(getColumn(), FlagCriteriaItem.ContainsType.CONTAINS_NONE, flags);
    }

    /**
     * 构造全包含任意标记位条件内容（扩展）
     *
     * @param flags 标记位值
     * @return 标记位条件内容
     */
    default CriteriaItem anyFlgs(long flags) {
        return FlagCriteriaItem.getInstance(getColumn(), FlagCriteriaItem.ContainsType.CONTAINS_ANY, flags);
    }
}
