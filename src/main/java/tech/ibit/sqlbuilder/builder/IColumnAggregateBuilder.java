package tech.ibit.sqlbuilder.builder;

import tech.ibit.sqlbuilder.AggregateColumn;
import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.enums.AggregateFunctionNameEnum;

/**
 * 构造聚合函数
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface IColumnAggregateBuilder {

    /**
     * 获取列
     *
     * @return 列
     */
    IColumn getColumn();


    /**
     * 平均数
     *
     * @param nameAs 别名
     * @return 平均数聚合列
     */
    default AggregateColumn avg(String nameAs) {
        return aggregate(AggregateFunctionNameEnum.AVG, nameAs);
    }

    /**
     * 求和
     *
     * @param nameAs 别名
     * @return 求和聚合列
     */
    default AggregateColumn sum(String nameAs) {
        return aggregate(AggregateFunctionNameEnum.SUM, nameAs);
    }


    /**
     * 最大值
     *
     * @param nameAs 别名
     * @return 最大值聚合列
     */
    default AggregateColumn max(String nameAs) {
        return aggregate(AggregateFunctionNameEnum.MAX, nameAs);
    }

    /**
     * 最小值
     *
     * @param nameAs 别名
     * @return 最小值聚合列
     */
    default AggregateColumn min(String nameAs) {
        return aggregate(AggregateFunctionNameEnum.MIN, nameAs);
    }


    /**
     * 计数
     *
     * @param nameAs 别名
     * @return 计数聚合列
     */
    default AggregateColumn count(String nameAs) {
        return aggregate(AggregateFunctionNameEnum.COUNT, nameAs);
    }

    /**
     * 计数
     *
     * @param nameAs   别名
     * @param distinct 是否distinct
     * @return 计数聚合列
     */
    default AggregateColumn count(String nameAs, boolean distinct) {
        return aggregate(AggregateFunctionNameEnum.COUNT, nameAs, distinct);
    }

    /**
     * IFNULL函数
     * <p>
     * 语法: IFNULL(expr1, expr2)
     * 规则: 如果 expr1 不为 NULL，就返回 expr1，否则返回 expr2。
     *
     * @param column2 列2
     * @param nameAs  别名
     * @return IFNULL函数列
     */
    default AggregateColumn ifnull(IColumn column2, String nameAs) {
        return aggregate(AggregateFunctionNameEnum.IFNULL.name(), new IColumn[]{getColumn(), column2}, nameAs, false);
    }

    /**
     * NULLIF函数
     * <p>
     * 语法: NULLIF(expr1,expr2)
     * 规则: 如果 expr1 = expr2 成立，那么返回值为NULL，否则返回值为 expr1。
     *
     * @param column2 列2
     * @param nameAs  别名
     * @return NULLIF函数列
     */
    default AggregateColumn nullif(IColumn column2, String nameAs) {
        return aggregate(AggregateFunctionNameEnum.NULLIF.name(), new IColumn[]{getColumn(), column2}, nameAs, false);
    }


    /**
     * 获取聚合列
     *
     * @param functionName 聚合函数名称
     * @param nameAs       别名（as后面部分）
     * @return 聚合列
     */
    default AggregateColumn aggregate(AggregateFunctionNameEnum functionName, String nameAs) {
        return aggregate(functionName, nameAs, false);
    }

    /**
     * 获取聚合列
     *
     * @param functionName 聚合函数名称
     * @param nameAs       别名（as后面部分）
     * @param distinct     是否distinct
     * @return 聚合列
     */
    default AggregateColumn aggregate(AggregateFunctionNameEnum functionName, String nameAs, boolean distinct) {
        return aggregate(functionName.name(), new IColumn[]{getColumn()}, nameAs, distinct);
    }

    /**
     * 获取聚合列
     *
     * @param functionName 函数名称
     * @param columns      列
     * @param nameAs       别名
     * @param distinct     是否distinct
     * @return 聚合列
     */
    default AggregateColumn aggregate(String functionName, IColumn[] columns, String nameAs, boolean distinct) {
        return new AggregateColumn(functionName, columns, nameAs, distinct);
    }


}
