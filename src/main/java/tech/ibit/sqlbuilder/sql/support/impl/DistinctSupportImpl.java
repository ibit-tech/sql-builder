package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.sql.field.BooleanField;
import tech.ibit.sqlbuilder.sql.support.DistinctSupport;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;

/**
 * DistinctSupport实现
 *
 * @author iBit程序猿
 */
public class DistinctSupportImpl<T> implements SqlSupport<T>,
        DistinctSupport<T> {

    /**
     * sql 对象
     */
    private final T sql;

    /**
     * 是否distinct
     */
    private final BooleanField distinct;

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public DistinctSupportImpl(T sql) {
        this(sql, new BooleanField(false));
    }

    /**
     * 构造函数
     *
     * @param sql      sql对象
     * @param distinct distinct对象
     */
    private DistinctSupportImpl(T sql, BooleanField distinct) {
        this.sql = sql;
        this.distinct = distinct;
    }

    /**
     * 对象复制（浅复制）
     *
     * @param sql sql对象
     * @param <K> sql对象模板
     * @return 复制后的对象
     */
    public <K> DistinctSupportImpl<K> copy(K sql) {
        return new DistinctSupportImpl<>(sql, distinct);
    }

    @Override
    public T getSql() {
        return sql;
    }

    /**
     * 获取distinct
     *
     * @return distinct
     */
    public BooleanField getDistinct() {
        return distinct;
    }

    @Override
    public T distinct() {
        return distinct(true);
    }

    @Override
    public T distinct(boolean distinct) {
        getDistinct().setValue(distinct);
        return getSql();
    }
}
