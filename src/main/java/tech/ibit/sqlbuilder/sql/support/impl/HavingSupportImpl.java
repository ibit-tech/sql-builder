package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.Criteria;
import tech.ibit.sqlbuilder.CriteriaItem;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.HavingSupport;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * HavingSupport实现
 *
 * @author iBit程序猿
 */
public class HavingSupportImpl<T> extends CriteriaSupportImpl
        implements SqlSupport<T>, HavingSupport<T> {

    /**
     * sql 对象
     */
    private final T sql;

    /**
     * Having
     */
    private final ListField<Criteria> having;

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public HavingSupportImpl(T sql) {
        this(sql, new ListField<>());
    }


    /**
     * 构造函数
     *
     * @param sql    sql对象
     * @param having having对象
     */
    private HavingSupportImpl(T sql, ListField<Criteria> having) {
        this.sql = sql;
        this.having = having;
    }

    /**
     * 对象复制（浅复制）
     *
     * @param sql sql对象
     * @param <K> sql对象模板
     * @return 复制后的对象
     */
    public <K> HavingSupportImpl<K> copy(K sql) {
        return new HavingSupportImpl<>(sql, having.copy());
    }

    /**
     * Having
     *
     * @return Having
     */
    private ListField<Criteria> getHaving() {
        return having;
    }

    @Override
    public T getSql() {
        return sql;
    }

    @Override
    public T having(Criteria having) {
        getHaving().addItem(having);
        return getSql();
    }

    @Override
    public T having(List<Criteria> havings) {
        getHaving().addItems(havings);
        return getSql();
    }

    @Override
    public T andHaving(CriteriaItem havingItem) {
        having(havingItem.and());
        return getSql();
    }

    @Override
    public T andHaving(List<Criteria> havings) {
        having(Criteria.and(havings));
        return getSql();
    }

    @Override
    public T orHaving(CriteriaItem havingItem) {
        having(havingItem.or());
        return getSql();
    }

    @Override
    public T orHaving(List<Criteria> havings) {
        having(Criteria.or(havings));
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getHavingPrepareStatement(boolean useAlias) {
        List<Criteria> criterion = getHaving().getItems();
        if (CollectionUtils.isEmpty(criterion)) {
            return PrepareStatement.empty();
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();
        prepareSql.append(" HAVING ");

        append(criterion, useAlias, prepareSql, values);

        return new PrepareStatement(prepareSql.toString(), values);
    }
}
