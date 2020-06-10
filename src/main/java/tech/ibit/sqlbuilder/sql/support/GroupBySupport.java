package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * GroupBy Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface GroupBySupport<T> extends SqlSupport<T>, PrepareStatementSupport {

    /**
     * Group by
     *
     * @return group by
     */
    ListField<Column> getGroupBy();


    /**
     * `GROUP BY t1.column`语句
     *
     * @param groupBy 相关列a
     * @return SQL对象
     * @see IColumn
     */
    default T groupBy(Column groupBy) {
        getGroupBy().addItem(groupBy);
        return getSql();
    }

    /**
     * `GROUP BY t1.column1, t2.column2, ...`语句
     *
     * @param groupBys 相关列列表
     * @return SQL对象
     * @see IColumn
     */
    default T groupBy(List<Column> groupBys) {
        getGroupBy().addItems(groupBys);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    default PrepareStatement getGroupByPrepareStatement(boolean useAlias) {
        List<Column> groupBys = getGroupBy().getItems();
        if (CollectionUtils.isEmpty(groupBys)) {
            return PrepareStatement.empty();
        }
        return getPrepareStatement(" GROUP BY ", groupBys
                , (Column groupBy) -> groupBy.getCompareColumnName(useAlias), null, ", ");
    }

}
