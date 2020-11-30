package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.InsertTableSupport;

import java.util.List;

/**
 * InsertTableSupport实现
 *
 * @param <T> 对象模板类型
 * @author iBit程序猿
 */
public class InsertTableSupportImpl<T> extends BaseTableSupportImpl<T> implements InsertTableSupport<T> {

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public InsertTableSupportImpl(T sql) {
        super(sql, new ListField<>());
    }


    @Override
    public T insert(Table table) {
        getTable().addItem(table);
        return getSql();
    }

    @Override
    public T insert(List<Table> tables) {
        getTable().addItems(tables);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getInsertPrepareStatement(boolean useAlias) {
        return getTablePrepareStatement("INSERT INTO ", useAlias);
    }
}
