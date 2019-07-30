package tech.ibit.sqlbuilder.aggregate;

import tech.ibit.sqlbuilder.Column;

/**
 * IFNULL函数
 * <p>
 * 语法: IFNULL(expr1, expr2)
 * 规则: 如果 expr1 不为 NULL，就返回 expr1，否则返回 expr2。
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class IfNullColumn extends AggregateColumn {

    /**
     * 构造函数
     *
     * @param column1 列1
     * @param column2 列2
     * @param nameAs  别名（as后面部分）
     */
    public IfNullColumn(Column column1, Column column2, String nameAs) {
        this(column1, column2, nameAs, false);
    }

    /**
     * 构造函数
     *
     * @param column1  列1
     * @param column2  列2
     * @param nameAs   别名（as后面部分）
     * @param distinct 是否distinct
     */
    public IfNullColumn(Column column1, Column column2, String nameAs, boolean distinct) {
        super(AggregateFunctionNames.IFNULL.name(), new Column[]{column1, column2}, nameAs, distinct);
    }
}
