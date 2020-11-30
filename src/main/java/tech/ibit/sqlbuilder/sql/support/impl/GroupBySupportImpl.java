package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.GroupBySupport;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * GroupBySupport实现
 *
 * @author iBit程序猿
 */
public class GroupBySupportImpl<T> implements SqlSupport<T>,
        GroupBySupport<T>, PrepareStatementBuildSupport {

    /**
     * sql 对象
     */
    private final T sql;

    /**
     * group by
     */
    private final ListField<Column> groupBy;

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public GroupBySupportImpl(T sql) {
        this(sql, new ListField<>());
    }

    /**
     * 构造函数
     *
     * @param sql     sql对象
     * @param groupBy groupBy对象
     */
    private GroupBySupportImpl(T sql, ListField<Column> groupBy) {
        this.sql = sql;
        this.groupBy = groupBy;
    }

    /**
     * 对象复制（浅复制）
     *
     * @param sql sql对象
     * @param <K> sql对象模板
     * @return 复制后的对象
     */
    public <K> GroupBySupportImpl<K> copy(K sql) {
        return new GroupBySupportImpl<>(sql, groupBy);
    }

    /**
     * Group by
     *
     * @return group by
     */
    private ListField<Column> getGroupBy() {
        return groupBy;
    }

    @Override
    public T getSql() {
        return sql;
    }

    @Override
    public T groupBy(Column groupBy) {
        getGroupBy().addItem(groupBy);
        return getSql();
    }

    @Override
    public T groupBy(List<Column> groupBys) {
        getGroupBy().addItems(groupBys);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getGroupByPrepareStatement(boolean useAlias) {
        List<Column> groupBys = getGroupBy().getItems();
        if (CollectionUtils.isEmpty(groupBys)) {
            return PrepareStatement.empty();
        }
        return getPrepareStatement(" GROUP BY ", groupBys
                , (Column groupBy) -> groupBy.getCompareColumnName(useAlias), null, ", ");
    }
}
