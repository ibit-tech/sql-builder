package tech.ibit.sqlbuilder.sql;

import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.support.*;

import java.util.List;

/**
 * DeleteSql
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface DeleteSql extends DeleteSupport<DeleteSql>,
        FromSupport<DeleteSql>,
        JoinOnSupport<DeleteSql>,
        WhereSupport<DeleteSql>,
        UseAliasSupport {

    /**
     * 删除表，item和from同时设置
     *
     * @param table 表
     * @return SQL对象
     */
    default DeleteSql deleteFrom(Table table) {
        delete(table);
        from(table);
        return getSql();
    }


    /**
     * 删除表，item和from同时设置
     *
     * @param tables 表列表
     * @return SQL对象
     */
    default DeleteSql deleteFrom(List<Table> tables) {
        delete(tables);
        from(tables);
        return getSql();
    }
}
