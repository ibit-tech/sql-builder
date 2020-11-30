package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.IOrderBy;
import tech.ibit.sqlbuilder.OrderBy;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.OrderBySupport;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * OrderBySupport实现
 *
 * @author iBit程序猿
 */
public class OrderBySupportImpl<T> implements SqlSupport<T>,
        OrderBySupport<T>, PrepareStatementBuildSupport {

    private final T sql;

    /**
     * Order by
     */
    private final ListField<IOrderBy> orderBy;

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public OrderBySupportImpl(T sql) {
        this.sql = sql;
        this.orderBy = new ListField<>();
    }

    /**
     * Order by
     *
     * @return Order by
     */
    private ListField<IOrderBy> getOrderBy() {
        return orderBy;
    }

    @Override
    public T getSql() {
        return sql;
    }

    @Override
    public T orderBy(IOrderBy orderBy) {
        getOrderBy().addItem(orderBy);
        return getSql();
    }

    @Override
    public T orderBy(List<IOrderBy> orderBys) {
        getOrderBy().addItems(orderBys);
        return getSql();
    }

    @Override
    public T orderBy(IColumn column) {
        return orderBy(column, false);
    }

    @Override
    public T orderBy(IColumn column, boolean desc) {
        if (null != column) {
            getOrderBy().addItem(new OrderBy(column, desc));
        }
        return getSql();
    }


    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getOrderByPrepareStatement(boolean useAlias) {
        List<IOrderBy> orderBys = getOrderBy().getItems();
        if (CollectionUtils.isEmpty(orderBys)) {
            return new PrepareStatement("", Collections.emptyList());
        }
        return getPrepareStatement(" ORDER BY ", orderBys, ", ", useAlias);
    }

}
