package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.JoinOnSupport;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * JoinOnSupport实现
 *
 * @author iBit程序猿
 */
public class JoinOnSupportImpl<T> implements SqlSupport<T>,
        JoinOnSupport<T>, PrepareStatementBuildSupport {

    /**
     * sql 对象
     */
    private final T sql;


    /**
     * Join on
     */
    private final ListField<JoinOn> joinOn;

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public JoinOnSupportImpl(T sql) {
        this(sql, new ListField<>());
    }

    /**
     * 构造函数
     *
     * @param sql    sql对象
     * @param joinOn joinOn对象
     */
    private JoinOnSupportImpl(T sql, ListField<JoinOn> joinOn) {
        this.sql = sql;
        this.joinOn = joinOn;
    }

    /**
     * 对象复制（浅复制）
     *
     * @param sql sql对象
     * @param <K> sql对象模板
     * @return 复制后的对象
     */
    public <K> JoinOnSupportImpl<K> copy(K sql) {
        return new JoinOnSupportImpl<>(sql, joinOn);
    }


    /**
     * Join on
     *
     * @return JoinOn
     */
    public ListField<JoinOn> getJoinOn() {
        return joinOn;
    }

    @Override
    public T getSql() {
        return sql;
    }

    @Override
    public T joinOn(JoinOn joinOn) {
        getJoinOn().addItem(joinOn);
        return getSql();
    }

    @Override
    public T joinOn(List<JoinOn> joinOns) {
        getJoinOn().addItems(joinOns);
        return getSql();
    }

    @Override
    public T joinOn(Table table, List<Column> columnPairs) {
        joinOn(JoinOn.none(table, columnPairs));
        return getSql();
    }

    @Override
    public T leftJoinOn(Table table, List<Column> columnPairs) {
        joinOn(JoinOn.left(table, columnPairs));
        return getSql();
    }

    @Override
    public T rightJoinOn(Table table, List<Column> columnPairs) {
        joinOn(JoinOn.right(table, columnPairs));
        return getSql();
    }

    @Override
    public T fullJoinOn(Table table, List<Column> columnPairs) {
        joinOn(JoinOn.full(table, columnPairs));
        return getSql();
    }

    @Override
    public T innerJoinOn(Table table, List<Column> columnPairs) {
        joinOn(JoinOn.inner(table, columnPairs));
        return getSql();
    }

    @Override
    public T complexLeftJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        joinOn(JoinOn.left(table, null, criteriaItems));
        return getSql();
    }

    @Override
    public T complexRightJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        joinOn(JoinOn.right(table, null, criteriaItems));
        return getSql();
    }

    @Override
    public T complexFullJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        joinOn(JoinOn.full(table, null, criteriaItems));
        return getSql();
    }

    @Override
    public T complexInnerJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        joinOn(JoinOn.inner(table, null, criteriaItems));
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getJoinOnPrepareStatement(boolean useAlias) {
        List<JoinOn> joinOns = getJoinOn().getItems();
        if (CollectionUtils.isEmpty(joinOns)) {
            return PrepareStatement.empty();
        }

        return getPrepareStatement(" ", joinOns, " ", useAlias);
    }
}
