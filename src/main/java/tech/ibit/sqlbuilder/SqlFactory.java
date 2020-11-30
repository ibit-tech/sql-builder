package tech.ibit.sqlbuilder;

import tech.ibit.sqlbuilder.sql.*;
import tech.ibit.sqlbuilder.sql.impl.*;

/**
 * SqlFactory
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class SqlFactory {

    /**
     * 构造函数
     */
    private SqlFactory() {
    }

    /**
     * 创建搜索
     *
     * @return 搜索sql
     */
    public static QuerySql createQuery() {
        return new QuerySqlImpl();
    }

    /**
     * 创建计数
     *
     * @return 计数sql
     */
    public static CountSql createCount() {
        return new CountSqlImpl();
    }

    /**
     * 创建删除
     *
     * @return 删除sql
     */
    public static DeleteSql createDelete() {
        return new DeleteSqlImpl();
    }

    /**
     * 创建插入
     *
     * @return 插入sql
     */
    public static InsertSql createInsert() {
        return new InsertSqlImpl();
    }

    /**
     * 创建更新
     *
     * @return 更新sql
     */
    public static UpdateSql createUpdate() {
        return new UpdateSqlImpl();
    }

}
