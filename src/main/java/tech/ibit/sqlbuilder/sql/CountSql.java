package tech.ibit.sqlbuilder.sql;

import tech.ibit.sqlbuilder.sql.support.*;

/**
 * 定义计数接口
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface CountSql extends ColumnSupport<CountSql>,
        DistinctSupport<CountSql>,
        FromSupport<CountSql>,
        GroupBySupport<CountSql>,
        HavingSupport<CountSql>,
        JoinOnSupport<CountSql>,
        WhereSupport<CountSql>,
        UseAliasSupport {

    /**
     * 转换为 SearchSql
     *
     * @return SearchSql对象
     */
    SearchSql toSearchSql();

}
