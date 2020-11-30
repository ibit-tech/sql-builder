package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.CriteriaItem;
import tech.ibit.sqlbuilder.JoinOn;
import tech.ibit.sqlbuilder.Table;

import java.util.List;

/**
 * JoinOn Support
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface JoinOnSupport<T> {

    /**
     * JoinOn操作
     *
     * @param joinOn JoinOn操作
     * @return SQL对象
     */
    T joinOn(JoinOn joinOn);


    /**
     * JoinOn操作
     *
     * @param joinOns JoinOn操作列表
     * @return SQL对象
     */
    T joinOn(List<JoinOn> joinOns);

    /**
     * `JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs on相关的"列-对"
     * @return SQL对象
     * @see Column
     */
    T joinOn(Table table, List<Column> columnPairs);

    /**
     * `LEFT JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs DbColumn pairs
     * @return SQL对象
     * @see Column
     */
    T leftJoinOn(Table table, List<Column> columnPairs);

    /**
     * `RIGHT JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs DbColumn pairs
     * @return SQL对象
     * @see Column
     */
    T rightJoinOn(Table table, List<Column> columnPairs);

    /**
     * `FULL JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs DbColumn pairs
     * @return SQL对象
     * @see Column
     */
    T fullJoinOn(Table table, List<Column> columnPairs);

    /**
     * `INNER JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs DbColumn pairs
     * @return SQL对象
     * @see Column
     */
    T innerJoinOn(Table table, List<Column> columnPairs);


    /**
     * `LEFT JOIN table t1 on t1.column1=t0.column2, t1.column3=t0.column4 AND t1.column5=?`语句
     *
     * @param table         需要join的表对象
     * @param criteriaItems 条件
     * @return SQL对象
     */
    T complexLeftJoinOn(Table table, List<CriteriaItem> criteriaItems);

    /**
     * `RIGHT JOIN table t1 on t1.column1=t0.column2, t1.column3=t0.column4 AND t1.column5=?`语句
     *
     * @param table         需要join的表对象
     * @param criteriaItems 条件
     * @return SQL对象
     */
    T complexRightJoinOn(Table table, List<CriteriaItem> criteriaItems);

    /**
     * `FULL JOIN table t1 on t1.column1=t0.column2, t1.column3=t0.column4 AND t1.column5=?`语句
     *
     * @param table         需要join的表对象
     * @param criteriaItems 条件
     * @return SQL对象
     */
    T complexFullJoinOn(Table table, List<CriteriaItem> criteriaItems);

    /**
     * `INNER JOIN table t1 on t1.column1=t0.column2, t1.column3=t0.column4 AND t1.column5=?`语句
     *
     * @param table         需要join的表对象
     * @param criteriaItems 条件
     * @return SQL对象
     */
    T complexInnerJoinOn(Table table, List<CriteriaItem> criteriaItems);

}
