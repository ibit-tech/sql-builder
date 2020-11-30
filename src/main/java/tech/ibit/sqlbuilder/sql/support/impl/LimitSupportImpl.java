package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.SimpleNameColumn;
import tech.ibit.sqlbuilder.sql.field.LimitField;
import tech.ibit.sqlbuilder.sql.support.LimitSupport;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;

import java.util.Arrays;
import java.util.List;

/**
 * LimitSupport实现
 *
 * @author iBit程序猿
 */
public class LimitSupportImpl<T> implements SqlSupport<T>,
        LimitSupport<T> {

    /**
     * sql 对象
     */
    private final T sql;

    /**
     * limit
     */
    private final LimitField limit;

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public LimitSupportImpl(T sql) {
        this.sql = sql;
        this.limit = new LimitField();
    }

    /**
     * 获取limit相关参数
     *
     * @return limit相关参数
     */
    public LimitField getLimit() {
        return limit;
    }

    @Override
    public T getSql() {
        return sql;
    }

    @Override
    public T limit(int start, int limit) {
        getLimit().limit(start, limit);
        return getSql();
    }

    @Override
    public T limit(int limit) {
        getLimit().limit(limit);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @return 预查询SQL对象
     */
    public PrepareStatement getLimitPrepareStatement() {
        LimitField limitField = getLimit();
        int limit = limitField.getLimit();
        if (limit < 0) {
            return PrepareStatement.empty();
        }

        int start = limitField.getStart();

        String prepareSql = " LIMIT ?, ?";
        List<ColumnValue> values = Arrays.asList(
                new ColumnValue(new SimpleNameColumn("$start"), start),
                new ColumnValue(new SimpleNameColumn("$limit"), limit));
        return new PrepareStatement(prepareSql, values);
    }

}
