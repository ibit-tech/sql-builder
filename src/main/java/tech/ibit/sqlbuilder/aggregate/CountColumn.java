package tech.ibit.sqlbuilder.aggregate;

import tech.ibit.sqlbuilder.Column;

/**
 * 计数函数
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class CountColumn extends AggregateColumn {

    /**
     * 构造函数
     *
     * @param column 列
     * @param nameAs 别名（as后面部分）
     */
    public CountColumn(Column column, String nameAs) {
        this(column, nameAs, false);
    }

    /**
     * 构造函数
     *
     * @param column   列
     * @param nameAs   别名（as后面部分）
     * @param distinct 是否distinct
     */
    public CountColumn(Column column, String nameAs, boolean distinct) {
        super(AggregateFunctionNames.COUNT.name(), null == column ? null : new Column[]{column}, nameAs, distinct);
    }
}
