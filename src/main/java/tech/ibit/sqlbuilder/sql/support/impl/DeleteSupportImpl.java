package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.DeleteSupport;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * DeleteSupport实现
 *
 * @author iBit程序猿
 */
public class DeleteSupportImpl<T> implements SqlSupport<T>,
        DeleteSupport<T>, PrepareStatementBuildSupport {

    /**
     * sql 对象
     */
    private final T sql;

    /**
     * 删除项
     */
    private final ListField<Table> deleteItem;

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public DeleteSupportImpl(T sql) {
        this.sql = sql;
        this.deleteItem = new ListField<>();
    }

    @Override
    public T getSql() {
        return sql;
    }

    /**
     * Delete Item
     *
     * @return 删除项
     */
    private ListField<Table> getDeleteItem() {
        return deleteItem;
    }


    @Override
    public T delete(Table table) {
        getDeleteItem().addItem(table);
        return getSql();
    }

    @Override
    public T delete(List<Table> tables) {
        getDeleteItem().addItems(tables);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param multiTable 是否查询多张表
     * @return 预查询SQL对象
     */
    public PrepareStatement getDeleteItemPrepareStatement(boolean multiTable) {

        if (!multiTable) {
            return PrepareStatement.empty();
        }

        List<Table> deleteTables = getDeleteItem().getItems();
        if (CollectionUtils.isEmpty(deleteTables)) {
            return PrepareStatement.empty();
        }

        // 查询多张表的时候，才需要别名
        return getPrepareStatement(" ", deleteTables
                , (Table table) -> table.getSelectTableName(true) + ".*", null, ", ");
    }
}
