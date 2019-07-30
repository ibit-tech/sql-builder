package tech.ibit.sqlbuilder.aggregate;

import tech.ibit.sqlbuilder.Column;

/**
 * 求最小值函数
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class MinColumn extends AggregateColumn {

    /**
     * 构造函数
     *
     * @param column 列
     * @param nameAs 别名（as后面部分）
     */
    public MinColumn(Column column, String nameAs) {
        this(column, nameAs, false);
    }

    /**
     * 构造函数
     *
     * @param column   列
     * @param nameAs   别名（as后面部分）
     * @param distinct 是否distinct
     */
    public MinColumn(Column column, String nameAs, boolean distinct) {
        super(AggregateFunctionNames.MIN.name(), null == column ? null : new Column[]{column}, nameAs, distinct);
    }
}
