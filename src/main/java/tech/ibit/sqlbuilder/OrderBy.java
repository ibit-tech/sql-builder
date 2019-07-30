package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 定义排序类
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class OrderBy implements IOrderBy {

    /**
     * 列
     */
    private Column column;

    /**
     * 是否倒叙
     */
    private boolean desc;

    /**
     * 构造函数
     *
     * @param column 列
     */
    public OrderBy(Column column) {
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
        String columnName = useAlias ? column.getNameWithTableAlias() : column.getName();
        String prepareSql = columnName + (desc ? " DESC" : "");
        return new PrepareStatement(prepareSql, null);
    }
}
