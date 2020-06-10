package tech.ibit.sqlbuilder;

import org.apache.commons.lang.StringUtils;

/**
 * 定义列接口
 *
 * @author IBIT程序猿
 * @version 1.0
 */
public interface IColumn {

    /**
     * 获取列名称
     *
     * @return 列名称
     */
    String getName();

    /**
     * 获取带表别名的列名称
     *
     * @return 列名称
     */
    String getNameWithTableAlias();


    /**
     * 获取列后面的`as`名称
     *
     * @return 列后面的`as`名称
     */
    String getNameAs();


    /**
     * 比较的列名称（如where, having, order by等）
     *
     * @param useAlias 是否使用别名
     * @return 列名
     */
    default String getCompareColumnName(boolean useAlias) {
        return StringUtils.isNotBlank(getNameAs())
                ? getNameAs()
                : (useAlias ? getNameWithTableAlias() : getName());
    }

    /**
     * 获取查询的列名
     *
     * @param useAlias 是否使用别名
     * @return 列名
     */
    default String getSelectColumnName(boolean useAlias) {
        return (useAlias ? getNameWithTableAlias() : getName())
                + (StringUtils.isBlank(getNameAs()) ? "" : (" AS " + getNameAs()));

    }

    /**
     * 构造ColumnValue
     *
     * @param value 值
     * @return ColumnValue
     */
    default ColumnValue value(Object value) {
        return new ColumnValue(this, value);
    }

}
