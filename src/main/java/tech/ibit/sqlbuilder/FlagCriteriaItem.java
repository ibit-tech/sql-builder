package tech.ibit.sqlbuilder;

import java.util.Collections;

/**
 * 标记位查询条件
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class FlagCriteriaItem extends CriteriaItem {

    /**
     * 第一列
     */
    private final IColumn column;

    /**
     * 值
     */
    private final long value;

    /**
     * 包含类型
     */
    public enum ContainsType {
        /**
         * 全包含
         */
        CONTAINS_ALL,

        /**
         * 不包含
         */
        CONTAINS_NONE,

        /**
         * 任意包含
         */
        CONTAINS_ANY,

        ;
    }


    /**
     * 是否相等
     */
    private ContainsType containsType;


    /**
     * 构造函数
     *
     * @param column       列
     * @param containsType 包含类型
     * @param value        位long值
     */
    private FlagCriteriaItem(IColumn column, ContainsType containsType, long value) {
        this.column = column;
        this.value = value;
        this.containsType = containsType;
    }

    /**
     * 获取实例
     *
     * @param column       列
     * @param containsType 包含类型
     * @param value        位long值
     * @return 标记位条件
     */
    public static FlagCriteriaItem getInstance(IColumn column, ContainsType containsType, long value) {
        return new FlagCriteriaItem(column, containsType, value);
    }

    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {

        StringBuilder whereSql = new StringBuilder();
        String columnName = column.getCompareColumnName(useAlias);
        switch (containsType) {
            case CONTAINS_ALL:
                whereSql.append(columnName)
                        .append(" & ? = ")
                        .append(columnName);
                break;
            case CONTAINS_NONE:
                whereSql.append(columnName)
                        .append(" & ? = 0");
                break;
            case CONTAINS_ANY:
                whereSql.append(columnName)
                        .append(" & ? <> 0");
                break;
            default:
        }
        return new PrepareStatement(whereSql.toString(), Collections.singletonList(new ColumnValue(column, value)));
    }

    /**
     * Gets the value of containsType
     *
     * @return the value of containsType
     */
    public ContainsType getContainsType() {
        return containsType;
    }

    /**
     * Sets the containsType
     * <p>You can use getContainsType() to get the value of containsType</p>
     *
     * @param containsType containsType
     */
    public void setContainsType(ContainsType containsType) {
        this.containsType = containsType;
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
     * Gets the value of value
     *
     * @return the value of value
     */
    public long getValue() {
        return value;
    }
}
