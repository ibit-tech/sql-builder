package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * TableSupport实现
 *
 * @param <T> 对象模板类型
 * @author iBit程序猿
 * @version 2.0
 */
public class BaseTableSupportImpl<T> implements PrepareStatementBuildSupport, SqlSupport<T> {

    /**
     * sql 对象
     */
    private final T sql;

    /**
     * 表名
     */
    private final ListField<Table> table;

    /**
     * 构造函数
     *
     * @param sql   sql对象
     * @param table 表
     */
    protected BaseTableSupportImpl(T sql, ListField<Table> table) {
        this.sql = sql;
        this.table = table;
    }

    /**
     * Gets the value of table
     *
     * @return the value of table
     */
    public ListField<Table> getTable() {
        return table;
    }

    @Override
    public T getSql() {
        return sql;
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @param hook     前缀
     * @return 预查询SQL对象
     */
    public PrepareStatement getTablePrepareStatement(String hook, boolean useAlias) {

        List<Table> froms = table.getItems();
        if (CollectionUtils.isEmpty(froms)) {
            return PrepareStatement.empty();
        }

        return getPrepareStatement(hook
                , froms, (Table from) -> from.getTableName(useAlias), null, ", ");
    }

}
