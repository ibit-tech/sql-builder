package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.IOrderBy;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * OrderBy Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface OrderBySupport<T> extends SqlSupport<T>, PrepareStatementSupport {

    /**
     * Order by
     *
     * @return Order by
     */
    ListField<IOrderBy> getOrderBy();


    /**
     * `ORDER BY` 语句
     *
     * @param orderBy 相关orderBy
     * @return SQL对象
     * @see IOrderBy
     */
    default T orderBy(IOrderBy orderBy) {
        getOrderBy().addItem(orderBy);
        return getSql();
    }

    /**
     * `ORDER BY` 语句
     *
     * @param orderBys 相关orderBy列表
     * @return SQL对象
     * @see IOrderBy
     */
    default T orderBy(List<IOrderBy> orderBys) {
        getOrderBy().addItems(orderBys);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    default PrepareStatement getOrderByPrepareStatement(boolean useAlias) {
        List<IOrderBy> orderBys = getOrderBy().getItems();
        if (CollectionUtils.isEmpty(orderBys)) {
            return new PrepareStatement("", Collections.emptyList());
        }
        return getPrepareStatement(" ORDER BY ", orderBys, ", ", useAlias);
    }


}
