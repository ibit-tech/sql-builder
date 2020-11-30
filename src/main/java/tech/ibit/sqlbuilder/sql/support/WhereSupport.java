package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.Criteria;
import tech.ibit.sqlbuilder.CriteriaItem;

import java.util.List;

/**
 * Where Support
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface WhereSupport<T> {


    /**
     * `WHERE` 语句
     *
     * @param criteria WHERE相关条件
     * @return SQL对象
     * @see Criteria
     */
    T where(Criteria criteria);

    /**
     * `WHERE` 语句
     *
     * @param criterion WHERE相关条件列表
     * @return SQL对象
     * @see Criteria
     */
    T where(List<Criteria> criterion);

    /**
     * `WHERE AND` 语句
     *
     * @param item WHERE相关条件
     * @return SQL对象
     * @see Criteria
     */
    T andWhere(CriteriaItem item);

    /**
     * `WHERE AND` 语句
     *
     * @param criterion WHERE相关条件列表
     * @return SQL对象
     * @see Criteria
     */
    T andWhere(List<Criteria> criterion);

    /**
     * `WHERE OR`语句
     *
     * @param item WHERE相关条件
     * @return SQL对象
     * @see Criteria
     */
    T orWhere(CriteriaItem item);

    /**
     * `WHERE OR`语句
     *
     * @param criterion WHERE相关条件列表
     * @return SQL对象
     * @see Criteria
     */
    T orWhere(List<Criteria> criterion);
}
