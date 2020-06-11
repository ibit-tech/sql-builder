package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * DeleteItem Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface DeleteSupport<T> extends SqlSupport<T>, PrepareStatementSupport {

    /**
     * Delete Item
     *
     * @return 删除项
     */
    ListField<Table> getDeleteItem();


    /**
     * `DELETE t1.*` 语句, t1表示"表别名"
     *
     * @param table 表对象
     * @return SQL对象
     * @see Table
     */
    default T delete(Table table) {
        getDeleteItem().addItem(table);
        return getSql();
    }

    /**
     * `DELETE t1.*, t2.* ...` 语句, t1, t2表示"表别名"
     *
     * @param tables 表对象列表
     * @return SQL对象
     * @see Table
     */
    default T delete(List<Table> tables) {
        getDeleteItem().addItems(tables);
        return getSql();
    }


    /**
     * 获取预查询SQL对象
     *
     * @param multiTable 是否查询多张表
     * @return 预查询SQL对象
     */
    default PrepareStatement getDeleteItemPrepareStatement(boolean multiTable) {

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
