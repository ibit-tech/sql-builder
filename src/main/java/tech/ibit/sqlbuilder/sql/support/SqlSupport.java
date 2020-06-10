package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.PrepareStatement;

import java.util.List;

/**
 * SqlSupport
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface SqlSupport<T> {

    /**
     * 返回sql
     *
     * @return sql
     */
    T getSql();

    /**
     * 获取预查询SQL
     *
     * @return 预查询SQL
     */
    PrepareStatement getPrepareStatement();


    /**
     * 扩展目标sql和values
     *
     * @param prepareStatement 预查询sql对象
     * @param targetPrepareSql 目标sql
     * @param targetValues     目标值
     */
    default void append(PrepareStatement prepareStatement, StringBuilder targetPrepareSql, List<ColumnValue> targetValues) {
        targetPrepareSql.append(prepareStatement.getPrepareSql());
        targetValues.addAll(prepareStatement.getValues());
    }


    /**
     * 扩展目标sql和values
     *
     * @param prepareStatements 预查询sql对象列表
     * @param targetPrepareSql  目标sql
     * @param targetValues      目标值
     */
    default void append(List<PrepareStatement> prepareStatements, StringBuilder targetPrepareSql, List<ColumnValue> targetValues) {
        prepareStatements.forEach(prepareStatement -> append(prepareStatement, targetPrepareSql, targetValues));
    }
}
