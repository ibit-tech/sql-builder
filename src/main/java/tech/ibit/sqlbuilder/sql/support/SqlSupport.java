package tech.ibit.sqlbuilder.sql.support;

/**
 * DefaultSqlSupport
 *
 * @author iBit程序猿
 */
public interface SqlSupport<T> {

    /**
     * 返回sql
     *
     * @return sql
     */
    T getSql();

}
