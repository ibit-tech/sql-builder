package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.Criteria;
import tech.ibit.sqlbuilder.CriteriaItem;

import java.util.List;

/**
 * Having Support
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface HavingSupport<T> {


    /**
     * `HAVING`语句
     *
     * @param having having语句对象
     * @return SQL对象
     */
    T having(Criteria having);

    /**
     * `HAVING`语句
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    T having(List<Criteria> havings);

    /**
     * `HAVING AND item`语句
     *
     * @param havingItem having语句对象
     * @return SQL对象
     */
    T andHaving(CriteriaItem havingItem);

    /**
     * `HAVING AND (havings)`语句
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    T andHaving(List<Criteria> havings);


    /**
     * `HAVING OR item`语句
     *
     * @param havingItem having语句对象
     * @return SQL对象
     */
    T orHaving(CriteriaItem havingItem);

    /**
     * `HAVING OR (havings)`语句（多个OR关系）
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    T orHaving(List<Criteria> havings);

}
