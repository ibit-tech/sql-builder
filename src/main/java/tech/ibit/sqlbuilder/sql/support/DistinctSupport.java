package tech.ibit.sqlbuilder.sql.support;

/**
 * Distinct Support
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface DistinctSupport<T> {


    /**
     * distinct 操作
     *
     * @return SQL对象
     */
    T distinct();


    /**
     * distinct 操作
     *
     * @param distinct 是否distinct
     * @return SQL对象
     */
    T distinct(boolean distinct);

}
