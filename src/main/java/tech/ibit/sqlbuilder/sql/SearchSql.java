package tech.ibit.sqlbuilder.sql;

import tech.ibit.sqlbuilder.sql.support.*;

/**
 * 定义搜索接口
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface SearchSql extends ColumnSupport<SearchSql>,
        DistinctSupport<SearchSql>,
        FromSupport<SearchSql>,
        GroupBySupport<SearchSql>,
        HavingSupport<SearchSql>,
        JoinOnSupport<SearchSql>,
        LimitSupport<SearchSql>,
        OrderBySupport<SearchSql>,
        WhereSupport<SearchSql>,
        UseAliasSupport {

    /**
     * 转换为 CountSql
     *
     * @return CountSql对象
     */
    CountSql toCountSql();

}
