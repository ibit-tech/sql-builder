package tech.ibit.sqlbuilder;

import java.util.List;

/**
 * 抽象字符串SQL构造对象
 *
 * @author IBIT TECH
 * * @version 1.0
 */
abstract class AbstractStringSql<T> {

    private StringSqlStatement sqlStatement = new StringSqlStatement();

    /**
     * 获取Sql对象
     *
     * @return Sql对象
     */
    abstract T getSql();

    /**
     * `SELECT column1, column2, ...` 语句
     *
     * @param column 列列表
     * @return Sql对象
     */
    public T select(String column) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.SELECT);
        getSqlStatement().addColumn(column);
        return getSql();
    }

    /**
     * `SELECT column1, column2, ...` 语句
     *
     * @param columns 列列表
     * @return Sql对象
     */
    public T select(List<String> columns) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.SELECT);
        getSqlStatement().addColumns(columns);
        return getSql();
    }


    /**
     * `SELECT DISTINCT column1, column2, ...` 语句
     *
     * @param column 列
     * @return Sql对象
     */
    public T selectDistinct(String column) {
        getSqlStatement().setDistinct(true);
        return select(column);
    }

    /**
     * `SELECT DISTINCT column1, column2, ...` 语句
     *
     * @param columns 列列表
     * @return Sql对象
     */
    public T selectDistinct(List<String> columns) {
        getSqlStatement().setDistinct(true);
        return select(columns);
    }

    /**
     * `SELECT COUNT()`语句
     *
     * @return Sql对象
     */
    public T count() {
        getSqlStatement().setStatementType(SqlStatement.StatementType.COUNT);
        return getSql();
    }


    /**
     * `SELECT COUNT(DISTINCT column)`语句
     *
     * @param column 计数列
     * @return Sql对象
     */
    public T countDistinct(String column) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.COUNT);
        getSqlStatement().setDistinct(true);
        getSqlStatement().addColumn(column);
        return getSql();
    }

    /**
     * `SELECT COUNT(DISTINCT column1, column2..)`语句
     *
     * @param columns 计数列列表
     * @return Sql对象
     */
    public T countDistinct(List<String> columns) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.COUNT);
        getSqlStatement().setDistinct(true);
        getSqlStatement().addColumns(columns);
        return getSql();
    }

    /**
     * `DELETE FROM table`语句
     *
     * @param table 表
     * @return Sql对象
     */
    public T deleteFrom(String table) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.DELETE);
        getSqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `UPDATE table`语句
     *
     * @param table 表
     * @return Sql对象
     */
    public T update(String table) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.UPDATE);
        getSqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `INSERT INTO table`语句
     *
     * @param table 表
     * @return Sql对象
     */
    public T insertInto(String table) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.INSERT);
        getSqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * 批量`INSERT INTO table(column1, column2...) VALUES(value1, value2...), (value11, value22...), ...` 语句, VALUES部分为多个
     *
     * @param table 表
     * @return Sql对象
     */
    public T batchInsertInto(String table) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.BATCH_INSERT);
        getSqlStatement().addFrom(table);
        return getSql();
    }


    /**
     * 多条批量`INSERT INTO table(column1, column2...) VALUES(value1, value2...)` 语句
     *
     * @param table 表
     * @return Sql对象
     */
    public T batchInsertInto2(String table) {
        getSqlStatement().setStatementType(SqlStatement.StatementType.BATCH_INSERT2);
        getSqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `SET`语句
     * 如: `u.name=p.name`
     *
     * @param set `SET` 语句
     * @return Sql对象
     */
    public T set(String set) {
        getSqlStatement().addSet(set);
        return getSql();
    }

    /**
     * `SET`语句
     * 如: `u.name=p.name`
     *
     * @param sets `set`语句列表
     * @return Sql对象
     */
    public T set(List<String> sets) {
        getSqlStatement().addSets(sets);
        return getSql();
    }

    /**
     * `SET`语句
     *
     * @param sets   `SET`语句列表, 如："column1=?", "column2=?"
     * @param values 值列表
     * @return Sql对象
     */
    public T set(List<String> sets, List<Object> values) {
        getSqlStatement().addSets(sets, values);
        return getSql();
    }

    /**
     * `VALUES(?, ?, ...)`语句
     *
     * @param values 值列表
     * @return Sql对象
     */
    public T values(List<Object> values) {
        getSqlStatement().addValues(values);
        return getSql();
    }

    /**
     * `VALUES(?, ?, ...)`语句
     *
     * @param value 值
     * @return Sql对象
     */
    public T values(Object value) {
        getSqlStatement().addValue(value);
        return getSql();
    }

    /**
     * `(column1, column2, ...) VALUES(?, ?, ...)`语句
     *
     * @param columns 插入列
     * @param values  值列表
     * @return Sql对象
     */
    public T values(List<String> columns, List<Object> values) {
        getSqlStatement().addColumnValues(columns, values);
        return getSql();
    }

    /**
     * `FROM table1, table2, ...`语句
     *
     * @param table 表
     * @return Sql对象
     */
    public T from(String table) {
        getSqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `FROM table1, table2, ...`语句
     *
     * @param tables 表列表
     * @return Sql对象
     */
    public T from(List<String> tables) {
        getSqlStatement().addFroms(tables);
        return getSql();
    }

    /**
     * `(|LEFT|RIGHT|FULL|INNER) JOIN`语句
     *
     * @param joinOn "JSON ON"语句，"JOIN table t1 ON t1.user_id=t0.user_id"
     * @return Sql对象
     */
    public T joinOn(String joinOn) {
        getSqlStatement().addJoinOn(joinOn);
        return getSql();
    }

    /**
     * `(|LEFT|RIGHT|FULL|INNER) JOIN`语句
     *
     * @param joinOns "JSON ON"语句列表，"JOIN table t1 ON t1.user_id=t0.user_id"
     * @return Sql对象
     */
    public T joinOn(List<String> joinOns) {
        getSqlStatement().addJoinOns(joinOns);
        return getSql();
    }


    /**
     * `WHERE` 条件
     *
     * @param criteria 条件，如："t1.user_id=t0.user_id"
     * @return Sql对象
     */
    public T where(String criteria) {
        getSqlStatement().addWhere(criteria);
        return getSql();
    }

    /**
     * `WHERE` 条件
     *
     * @param criterion 条件列表，如："t1.user_id=t0.user_id"
     * @return Sql对象
     */
    public T where(List<String> criterion) {
        getSqlStatement().addWheres(criterion);
        return getSql();
    }

    /**
     * `WHERE`条件
     *
     * @param criterion   条件，如："user_id=?"
     * @param whereParams 条件参数值
     * @return Sql对象
     */
    public T where(List<String> criterion, List<Object> whereParams) {
        getSqlStatement().addWheres(criterion, whereParams);
        return getSql();
    }

    /**
     * `GROUP BY t1.column1, t2.column2, ...`语句
     *
     * @param groupBy `GROUP BY`列
     * @return Sql对象
     */
    public T groupBy(String groupBy) {
        getSqlStatement().addGroupBy(groupBy);
        return getSql();
    }

    /**
     * `GROUP BY t1.column1, t2.column2, ...`语句
     *
     * @param groupBys `GROUP BY`列列表
     * @return Sql对象
     */
    public T groupBy(List<String> groupBys) {
        getSqlStatement().addGroupBys(groupBys);
        return getSql();
    }

    /**
     * `ORDER BY`语句
     *
     * @param orderBy `GROUP BY`语句
     * @return Sql对象
     */
    public T orderBy(String orderBy) {
        getSqlStatement().addOrderBy(orderBy);
        return getSql();
    }

    /**
     * `ORDER BY`语句
     *
     * @param orderBys `GROUP BY`语句列表
     * @return Sql对象
     */
    public T orderBy(List<String> orderBys) {
        getSqlStatement().addOrderBys(orderBys);
        return getSql();
    }

    /**
     * `HAVING`语句
     *
     * @param criteria HAVING条件
     * @return Sql对象
     */
    public T having(String criteria) {
        getSqlStatement().addHaving(criteria);
        return getSql();
    }

    /**
     * `HAVING`语句
     *
     * @param criterion HAVING条件列表
     * @return Sql对象
     */
    public T having(List<String> criterion) {
        getSqlStatement().addHaving(criterion);
        return getSql();
    }

    /**
     * `HAVING`语句
     *
     * @param criterion    HAVING条件列表
     * @param havingParams HAVING参数值列表
     * @return Sql对象
     */
    private T having(List<String> criterion, List<Object> havingParams) {
        getSqlStatement().addHaving(criterion, havingParams);
        return getSql();
    }

    /**
     * 自定义语句
     *
     * @param lastClause 自定义语句
     * @return Sql对象
     */
    public T lastClause(String lastClause) {
        getSqlStatement().setLastClause(lastClause);
        return getSql();
    }

    /**
     * `LIMIT #{start}, #{limit}`语句
     *
     * @param start 开始位置
     * @param limit 返回条数
     * @return Sql对象
     */
    public T limit(int start, int limit) {
        getSqlStatement().setLimit(start, limit);
        return getSql();
    }

    /**
     * `LIMIT 0, #{limit}`语句
     *
     * @param limit 返回条数
     * @return Sql对象
     */
    public T limit(int limit) {
        getSqlStatement().setLimit(limit);
        return getSql();
    }


    /**
     * 获取COUNT相关的SQL参数对象
     *
     * @return Sql参数对象
     */
    public SqlParams countSqlParams() {
        return getSqlStatement().countSQLParams();
    }

    /**
     * 获取相关的SQLParams对象
     *
     * @return Sql参数对象
     */
    public SqlParams getSqlParams() {
        return getSqlStatement().getSQLParams();
    }


    private StringSqlStatement getSqlStatement() {
        return sqlStatement;
    }


}
