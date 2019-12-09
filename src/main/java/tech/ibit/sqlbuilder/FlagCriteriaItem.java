package tech.ibit.sqlbuilder;


import lombok.Data;

import java.util.Collections;

/**
 * 标记位查询条件
 *
 * @author IBIT TECH
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
    FlagCriteriaItem(Column column, ContainsType containsType, long value) {
        super(column, null, value);
        this.containsType = containsType;
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    @Override
    PrepareStatement<KeyValuePair> getPrepareStatement(boolean useAlias) {

        StringBuilder whereSql = new StringBuilder();
        String columnName = getColumnName(getColumn(), useAlias);
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
        return new PrepareStatement<>(whereSql.toString(), Collections.singletonList(new KeyValuePair(columnName, getValue())));
    }
}
