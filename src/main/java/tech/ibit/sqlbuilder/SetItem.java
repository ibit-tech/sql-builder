package tech.ibit.sqlbuilder;

import tech.ibit.sqlbuilder.enums.SetItemTypeEnum;

import java.util.Collections;

/**
 * 设置项
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class SetItem implements PrepareStatementSupplier {

    /**
     * 设置列
     */
    private final Column column;

    /**
     * 设置值
     */
    private final Object value;

    /**
     * 设置类型
     */
    private final SetItemTypeEnum type;

    /**
     * 设置对象
     *
     * @param column 列
     * @param value  值
     * @param type   类型
     */
    public SetItem(Column column, Object value, SetItemTypeEnum type) {
        this.column = column;
        this.value = value;
        this.type = type;
    }

    /**
     * 设置具体值
     *
     * @param column 列
     * @param value  值
     * @return 设置item
     */
    public static SetItem set(Column column, Object value) {
        return new SetItem(column, value, SetItemTypeEnum.VALUE);
    }

    /**
     * 设置自增长
     *
     * @param column 列
     * @param value  值
     * @return 自增长item
     */
    public static SetItem increaseSet(Column column, Number value) {
        return new SetItem(column, value, SetItemTypeEnum.INCREASE);
    }

    /**
     * 设置自减
     *
     * @param column 列
     * @param value  值
     * @return 自减item
     */
    public static SetItem decreaseSet(Column column, Number value) {
        return new SetItem(column, value, SetItemTypeEnum.DECREASE);
    }

    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {
        String prepareSql = getPrepareSql(useAlias);
        return new PrepareStatement(prepareSql, Collections.singletonList(new ColumnValue(column, value)));
    }

    /**
     * 获取预查询sql
     *
     * @param useAlias 是否使用别名
     * @return 预查询sql
     */
    private String getPrepareSql(boolean useAlias) {
        String columnName = column.getCompareColumnName(useAlias);
        switch (type) {
            case VALUE:
                return CriteriaMaker.eq(columnName);
            case INCREASE:
                return CriteriaMaker.eq(columnName, columnName + " + ?");
            case DECREASE:
                return CriteriaMaker.eq(columnName, columnName + " - ?");
            default:
                return null;
        }
    }

    /**
     * Gets the value of column
     *
     * @return the value of column
     */
    public Column getColumn() {
        return column;
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
     * Gets the value of type
     *
     * @return the value of type
     */
    public SetItemTypeEnum getType() {
        return type;
    }
}
