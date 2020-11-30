package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.UpdateTableSupport;

import java.util.List;

/**
 * UpdateTableSupport实现
 *
 * @param <T> 对象模板类型
 * @author iBit程序猿
 */
public class UpdateTableSupportImpl<T> extends BaseTableSupportImpl<T> implements UpdateTableSupport<T> {

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public UpdateTableSupportImpl(T sql) {
        super(sql, new ListField<>());
    }

    @Override
    public T update(Table table) {
        getTable().addItem(table);
        return getSql();
    }

    @Override
    public T update(List<Table> tables) {
        getTable().addItems(tables);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getUpdatePrepareStatement(boolean useAlias) {
        return getTablePrepareStatement("UPDATE ", useAlias);
    }

}
