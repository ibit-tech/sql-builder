package tech.ibit.sqlbuilder;


import lombok.Data;
import tech.ibit.sqlbuilder.enums.CriteriaItemValueTypeEnum;

import java.util.Collections;

/**
 * 标记位查询条件
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
public class FlagCriteriaItem extends CriteriaItem {

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
        super(column, null, null, value, null, CriteriaItemValueTypeEnum.SINGLE_VALUE);
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

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {

        StringBuilder whereSql = new StringBuilder();
        String columnName = getColumn().getCompareColumnName(useAlias);
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
        return new PrepareStatement(whereSql.toString(), Collections.singletonList(new ColumnValue(getColumn(), getValue())));
    }
}
