package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.field.ListField;

import java.util.List;

/**
 * 插入表Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface InsertTableSupport<T> extends SqlSupport<T>, PrepareStatementSupport, TableSupport {

    /**
     * 获取插入表
     *
     * @return 插入表
     */
    ListField<Table> getInsertTable();


    /**
     * `INSERT INTO table1 t1` 语句, t1表示"表别名"
     *
     * @param table 表对象
     * @return SQL对象
     * @see Table
     */
    default T insert(Table table) {
        getInsertTable().addItem(table);
        return getSql();
    }

    /**
     * `INSERT INTO table1 t1, table2 t2...` 语句, t1, t2表示"表别名"
     *
     * @param tables 表对象列表
     * @return SQL对象
     * @see Table
     */
    default T insert(List<Table> tables) {
        getInsertTable().addItems(tables);
        return getSql();
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    default PrepareStatement getInsertPrepareStatement(boolean useAlias) {
        return getTablePrepareStatement(getInsertTable(), "INSERT INTO ", useAlias);
    }


}