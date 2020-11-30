package tech.ibit.sqlbuilder.sql;

import tech.ibit.sqlbuilder.sql.support.*;

/**
 * 定义搜索接口
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface QuerySql extends ColumnSupport<QuerySql>,
        DistinctSupport<QuerySql>,
        FromSupport<QuerySql>,
        GroupBySupport<QuerySql>,
        HavingSupport<QuerySql>,
        JoinOnSupport<QuerySql>,
        LimitSupport<QuerySql>,
        OrderBySupport<QuerySql>,
        WhereSupport<QuerySql>,
        PrepareStatementSupport {

    /**
     * 转换为 CountSql
     *
     * @return CountSql对象
     */
    CountSql toCountSql();
}
