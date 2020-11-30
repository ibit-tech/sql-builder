package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.IOrderBy;

import java.util.List;

/**
 * OrderBy Support
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface OrderBySupport<T> {


    /**
     * `ORDER BY` 语句
     *
     * @param orderBy 相关orderBy
     * @return SQL对象
     * @see IOrderBy
     */
    T orderBy(IOrderBy orderBy);

    /**
     * `ORDER BY` 语句
     *
     * @param orderBys 相关orderBy列表
     * @return SQL对象
     * @see IOrderBy
     */
    T orderBy(List<IOrderBy> orderBys);

    /**
     * `ORDER BY` 语句
     *
     * @param column orderBy列
     * @return SQL对象
     */
    T orderBy(IColumn column);


    /**
     * `ORDER BY` 语句
     *
     * @param column orderBy列
     * @param desc   是否倒序
     * @return SQL对象
     */
    T orderBy(IColumn column, boolean desc);

}
