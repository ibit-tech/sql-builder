package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.FromSupport;

import java.util.List;

/**
 * FromSupport实现
 *
 * @param <T> 对象模板类型
 * @author iBit程序猿
 */
public class FromSupportImpl<T> extends BaseTableSupportImpl<T> implements FromSupport<T> {

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public FromSupportImpl(T sql) {
        this(sql, new ListField<>());
    }

    /**
     * 构造函数
     *
     * @param sql  sql对象
     * @param from from对象
     */
    private FromSupportImpl(T sql, ListField<Table> from) {
        super(sql, from);
    }

    /**
     * 对象复制（浅复制）
     *
     * @param sql sql对象
     * @param <K> sql对象模板
     * @return 复制后的对象
     */
    public <K> FromSupportImpl<K> copy(K sql) {
        return new FromSupportImpl<>(sql, getTable());
    }

    @Override
    public T from(Table table) {
        getTable().addItem(table);
        return getSql();
    }

    @Override
    public T from(List<Table> tables) {
        getTable().addItems(tables);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getFromPrepareStatement(boolean useAlias) {
        return getTablePrepareStatement(" FROM ", useAlias);
    }

}
