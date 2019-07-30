package tech.ibit.sqlbuilder.aggregate;

import tech.ibit.sqlbuilder.Column;

/**
 * NULLIF相关列
 * <p>
 * 语法: NULLIF(expr1,expr2)
 * 规则: 如果 expr1 = expr2 成立，那么返回值为NULL，否则返回值为 expr1。
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class NullIfColumn extends AggregateColumn {

    /**
     * 构造函数
     *
     * @param column1 列1
     * @param column2 列2
     * @param nameAs  别名（as后面部分）
     */
    public NullIfColumn(Column column1, Column column2, String nameAs) {
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
    public NullIfColumn(Column column1, Column column2, String nameAs, boolean distinct) {
        super(AggregateFunctionNames.NULLIF.name(), new Column[]{column1, column2}, nameAs, distinct);
    }
}
