package tech.ibit.sqlbuilder.aggregate;

import tech.ibit.sqlbuilder.Column;

/**
 * 求和函数
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class SumColumn extends AggregateColumn {

    /**
     * 构造函数
     *
     * @param column 列
     * @param nameAs 别名（as后面部分）
     */
    public SumColumn(Column column, String nameAs) {
        this(column, nameAs, false);
    }

    /**
     * 构造函数
     *
     * @param column   列
     * @param nameAs   别名（as后面部分）
     * @param distinct 是否distinct
     */
    public SumColumn(Column column, String nameAs, boolean distinct) {
        super(AggregateFunctionNames.SUM.name(), null == column ? null : new Column[]{column}, nameAs, distinct);
    }
}
