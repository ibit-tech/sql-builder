package tech.ibit.sqlbuilder;

import lombok.experimental.UtilityClass;
import tech.ibit.sqlbuilder.sql.*;
import tech.ibit.sqlbuilder.sql.impl.*;

/**
 * SqlFactory
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@UtilityClass
public class SqlFactory {

    /**
     * 创建搜索
     *
     * @return 搜索sql
     */
    public SearchSql createSearch() {
        return new SearchSqlImpl();
    }


    /**
     * 创建计数
     *
     * @return 计数sql
     */
    public CountSql createCount() {
        return new CountSqlImpl();
    }

    /**
     * 创建删除
     *
     * @return 删除sql
     */
    public DeleteSql createDelete() {
        return new DeleteSqlImpl();
    }

    /**
     * 创建插入
     *
     * @return 插入sql
     */
    public InsertSql createInsert() {
        return new InsertSqlImpl();
    }

    /**
     * 创建更新
     *
     * @return 更新sql
     */
    public UpdateSql createUpdate() {
        return new UpdateSqlImpl();
    }

}
