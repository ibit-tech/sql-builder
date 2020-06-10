package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;

/**
 * 定义排序类
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class OrderBy implements IOrderBy {

    /**
     * 列
     */
    private IColumn column;

    /**
     * 是否倒叙
     */
    private boolean desc;

    /**
     * 构造函数
     *
     * @param column 列
     */
    public OrderBy(IColumn column) {
        this(column, false);
    }

    /**
     * 构造预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return BETWEEN值预查询SQL对象
     */
    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {
        String columnName = getColumn().getCompareColumnName(useAlias);
        String prepareSql = columnName + (desc ? " DESC" : "");
        return new PrepareStatement(prepareSql, Collections.emptyList());
    }
}
