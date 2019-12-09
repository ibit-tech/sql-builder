package tech.ibit.sqlbuilder;


import java.util.List;

/**
 * 抽象SQL构造对象
 *
 * @author IBIT TECH
 * @version 1.0
 */
abstract class AbstractSql<T> {

    /**
     * SQL语句构造对象
     */
    private SqlStatement sqlStatement = new SqlStatement();

    /**
     * 获取Sql对象
     *
     * @return SQL对象对象
     */
    abstract T getSql();

    /**
     * `SELECT t.column1, t.column2, ...`语句, "t": 为表的别名
     *
     * @param columns 查询字段对象
     * @return SQL对象
     * @see IColumn
     */
    public T select(List<? extends IColumn> columns) {
        sqlStatement().setStatementType(SqlStatement.StatementType.SELECT);
        sqlStatement().addColumns(columns);
        return getSql();
    }

    /**
     * `SELECT t.column`语句, "t": 为表的别名
     *
     * @param column 查询字段对象
     * @return SQL对象
     * @see IColumn
     */
    public T select(IColumn column) {
        sqlStatement().setStatementType(SqlStatement.StatementType.SELECT);
        sqlStatement().addColumn(column);
        return getSql();
    }

    /**
     * `SELECT DISTINCT t.column1, t.column2, ...`语句, "t": 为表的别名
     *
     * @param columns 查询字段对象
     * @return SQL对象
     * @see IColumn
     */
    public T selectDistinct(List<? extends IColumn> columns) {
        sqlStatement().setDistinct(true);
        return select(columns);
    }

    /**
     * `SELECT DISTINCT t.column`语句, "t": 为表的别名
     *
     * @param column 查询字段对象
     * @return SQL对象
     * @see IColumn
     */
    public T selectDistinct(IColumn column) {
        sqlStatement().setDistinct(true);
        return select(column);
    }

    /**
     * `SELECT t.column1, t.column2, ...`语句, "t": 为表的别名
     *
     * @param poClazz 查询持久化对象类
     * @return SQL对象
     */
    public T selectPo(Class poClazz) {
        return select(EntityConverter.getColumns(poClazz));
    }

    /**
     * `SELECT DISTINCT t.column1, t.column2, ...`语句, "t": 为表的别名
     *
     * @param poClazz 查询持久化对象类
     * @return SQL对象
     */
    public T selectDistinctPo(Class poClazz) {
        return selectDistinct(EntityConverter.getColumns(poClazz));
    }

    /**
     * `select COUNT(*)`语句
     *
     * @return SQL对象
     */
    public T count() {
        sqlStatement().setStatementType(SqlStatement.StatementType.COUNT);
        return getSql();
    }

    /**
     * `SELECT COUNT(DISTINCT t.column1, t.column2...)`语句, "t": 为表的别名
     *
     * @param columns Count对应列
     * @return SQL对象
     * @see IColumn
     */
    public T countDistinct(List<? extends IColumn> columns) {
        sqlStatement().setStatementType(SqlStatement.StatementType.COUNT);
        sqlStatement().setDistinct(true);
        sqlStatement().addColumns(columns);
        return getSql();
    }

    /**
     * `SELECT COUNT(DISTINCT t.column)`语句, "t": 为表的别名
     *
     * @param column Count对应列
     * @return SQL对象
     * @see IColumn
     */
    public T countDistinct(IColumn column) {
        sqlStatement().setStatementType(SqlStatement.StatementType.COUNT);
        sqlStatement().setDistinct(true);
        sqlStatement().addColumn(column);
        return getSql();
    }

    /**
     * `DELETE FROM table` 语句
     *
     * @param table 表对象
     * @return SQL对象
     * @see Table
     */
    public T deleteFrom(Table table) {
        sqlStatement().setStatementType(SqlStatement.StatementType.DELETE);
        sqlStatement().setUseAlias(false);
        sqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `DELETE T.* FROM table T` 语句
     *
     * @param table 表对象
     * @return SQL对象
     * @see Table
     */
    public T deleteTableFrom(Table table) {
        sqlStatement().setStatementType(SqlStatement.StatementType.DELETE_TABLE);
        sqlStatement().setUseAlias(true);
        sqlStatement().addDeleteTable(table);
        sqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `UPDATE table t` 语句
     *
     * @param table 表对象
     * @return SQL对象
     * @see Table
     */
    public T update(Table table) {
        sqlStatement().setStatementType(SqlStatement.StatementType.UPDATE);
        sqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `INSERT INTO table` 语句
     *
     * @param table 表对象
     * @return SQL对象
     * @see Table
     */
    public T insertInto(Table table) {
        sqlStatement().setStatementType(SqlStatement.StatementType.INSERT);
        sqlStatement().setUseAlias(false);
        sqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * 批量`INSERT INTO table(column1, column2...) VALUES(value1, value2...), (value11, value22...)` 语句, Values部分为多个
     * 这个支持JDBCTemplate sql语句只有一条，但是value可以插入多条的情况
     *
     * @param table 表对象
     * @return SQL对象 对象
     * @see Table
     */
    public T batchInsertInto(Table table) {
        sqlStatement().setStatementType(SqlStatement.StatementType.BATCH_INSERT);
        sqlStatement().setUseAlias(false);
        sqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `SET` 语句
     *
     * @param sets 列和值列表
     * @return SQL对象
     * @see ColumnValue
     */
    public T set(List<ColumnValue> sets) {
        sqlStatement().addSets(sets);
        return getSql();
    }

    /**
     * `SET` 语句
     *
     * @param set 列和值
     * @return SQL对象
     * @see ColumnValue
     */
    public T set(ColumnValue set) {
        sqlStatement().addSet(set);
        return getSql();
    }

    /**
     * 自增长`SET`语句
     *
     * @param sets 列和值列表 其中值为"增长的值"
     * @return SQL对象
     * @see ColumnValue
     */
    public T increaseSet(List<ColumnValue> sets) {
        sqlStatement().addIncreaseSets(sets);
        return getSql();
    }

    /**
     * 自增长`SET`语句
     *
     * @param set 列和值 其中值为"增长的值"
     * @return SQL对象
     * @see ColumnValue
     */
    public T increaseSet(ColumnValue set) {
        sqlStatement().addIncreaseSet(set);
        return getSql();
    }

    /**
     * 自减少`SET`语句
     *
     * @param sets 列和值列表 其中值为"减少的值"
     * @return SQL对象
     * @see ColumnValue
     */
    public T decreaseSet(List<ColumnValue> sets) {
        sqlStatement().addDecreaseSets(sets);
        return getSql();
    }

    /**
     * 自减少`SET`语句
     *
     * @param set 列和值列表 其中值为"减少的值"
     * @return SQL对象
     * @see ColumnValue
     */
    public T decreaseSet(ColumnValue set) {
        sqlStatement().addDecreaseSet(set);
        return getSql();
    }

    /**
     * `(column1, column2, ...) VALUES(?, ?, ...)`语句
     *
     * @param columnValues 列和值列表
     * @return SQL对象
     * @see ColumnValue
     */
    public T values(List<? extends ColumnValue> columnValues) {
        sqlStatement().addColumnValues(columnValues);
        return getSql();
    }

    /**
     * `(column1) VALUES(?)`语句
     *
     * @param columnValue 列和值
     * @return SQL对象
     * @see ColumnValue
     */
    public T values(ColumnValue columnValue) {
        sqlStatement().addColumnValue(columnValue);
        return getSql();
    }

    /**
     * `(column1, column2, ...) VALUES(?, ?, ...)`语句
     *
     * @param columns 列列表
     * @param values  值列表
     * @return SQL对象
     * @see ColumnValue
     */
    public T values(List<Column> columns, List<Object> values) {
        sqlStatement().addColumnValue(columns, values);
        return getSql();
    }

    /**
     * `FROM table1 t1` 语句, t1表示"表别名"
     *
     * @param table 表对象
     * @return SQL对象
     * @see Table
     */
    public T from(Table table) {
        sqlStatement().addFrom(table);
        return getSql();
    }

    /**
     * `FROM table1 t1, table2 t2...` 语句, t1, t2表示"表别名"
     *
     * @param tables 表对象列表
     * @return SQL对象
     * @see Table
     */
    public T from(List<Table> tables) {
        sqlStatement().addFroms(tables);
        return getSql();
    }


    /**
     * `JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs on相关的"列-对"
     * @return SQL对象
     * @see Column
     */
    public T joinOn(Table table, List<Column> columnPairs) {
        sqlStatement().addJoinOn(null, table, columnPairs);
        return getSql();
    }

    /**
     * `LEFT JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs DbColumn pairs
     * @return SQL对象
     * @see Column
     */
    public T leftJoinOn(Table table, List<Column> columnPairs) {
        sqlStatement().addJoinOn(SqlStatement.JoinOnType.LEFT, table, columnPairs);
        return getSql();
    }

    /**
     * `RIGHT JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs DbColumn pairs
     * @return SQL对象
     * @see Column
     */
    public T rightJoinOn(Table table, List<Column> columnPairs) {
        sqlStatement().addJoinOn(SqlStatement.JoinOnType.RIGHT, table, columnPairs);
        return getSql();
    }

    /**
     * `FULL JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs DbColumn pairs
     * @return SQL对象
     * @see Column
     */
    public T fullJoinOn(Table table, List<Column> columnPairs) {
        sqlStatement().addJoinOn(SqlStatement.JoinOnType.FULL, table, columnPairs);
        return getSql();
    }

    /**
     * `INNER JOIN table t1 ON t1.column1=t0.column2, t1.column3=t0.column4`语句
     *
     * @param table       需要join的表对象
     * @param columnPairs DbColumn pairs
     * @return SQL对象
     * @see Column
     */
    public T innerJoinOn(Table table, List<Column> columnPairs) {
        sqlStatement().addJoinOn(SqlStatement.JoinOnType.INNER, table, columnPairs);
        return getSql();
    }



    /**
     * `LEFT JOIN table t1 on t1.column1=t0.column2, t1.column3=t0.column4 AND t1.column5=?`语句
     *
     * @param table         需要join的表对象
     * @param criteriaItems 条件
     * @return SQL对象
     */
    public T complexLeftJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        sqlStatement().addComplexJoinOn(SqlStatement.JoinOnType.LEFT, table, criteriaItems);
        return getSql();
    }

    /**
     * `RIGHT JOIN table t1 on t1.column1=t0.column2, t1.column3=t0.column4 AND t1.column5=?`语句
     *
     * @param table         需要join的表对象
     * @param criteriaItems 条件
     * @return SQL对象
     */
    public T complexRightJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        sqlStatement().addComplexJoinOn(SqlStatement.JoinOnType.RIGHT, table, criteriaItems);
        return getSql();
    }

    /**
     * `FULL JOIN table t1 on t1.column1=t0.column2, t1.column3=t0.column4 AND t1.column5=?`语句
     *
     * @param table         需要join的表对象
     * @param criteriaItems 条件
     * @return SQL对象
     */
    public T complexFullJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        sqlStatement().addComplexJoinOn(SqlStatement.JoinOnType.FULL, table, criteriaItems);
        return getSql();
    }

    /**
     * `INNER JOIN table t1 on t1.column1=t0.column2, t1.column3=t0.column4 AND t1.column5=?`语句
     *
     * @param table         需要join的表对象
     * @param criteriaItems 条件
     * @return SQL对象
     */
    public T complexInnerJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        sqlStatement().addComplexJoinOn(SqlStatement.JoinOnType.INNER, table, criteriaItems);
        return getSql();
    }

    /**
     * `WHERE` 语句
     *
     * @param criterion WHERE相关条件
     * @return SQL对象
     * @see Criteria
     */
    public T where(Criteria criterion) {
        sqlStatement().addWhere(criterion);
        return getSql();
    }

    /**
     * `WHERE` 语句
     *
     * @param criterion WHERE相关条件列表
     * @return SQL对象
     * @see Criteria
     */
    public T where(List<Criteria> criterion) {
        sqlStatement().addWheres(criterion);
        return getSql();
    }

    /**
     * `WHERE AND` 语句
     *
     * @param item WHERE相关条件
     * @return SQL对象
     * @see Criteria
     */
    public T andWhere(CriteriaItem item) {
        return where(Criteria.and(item));
    }

    /**
     * `WHERE AND` 语句
     *
     * @param criterion WHERE相关条件列表
     * @return SQL对象
     * @see Criteria
     */
    public T andWhere(List<Criteria> criterion) {
        return where(Criteria.and(criterion));
    }

    /**
     * `WHERE OR`语句
     *
     * @param item WHERE相关条件
     * @return SQL对象
     * @see Criteria
     */
    public T orWhere(CriteriaItem item) {
        return where(Criteria.or(item));
    }

    /**
     * `WHERE OR`语句
     *
     * @param criterion WHERE相关条件列表
     * @return SQL对象
     * @see Criteria
     */
    public T orWhere(List<Criteria> criterion) {
        return where(Criteria.or(criterion));
    }

    /**
     * `GROUP BY t1.column`语句
     *
     * @param groupBy 相关列
     * @return SQL对象
     * @see IColumn
     */
    public T groupBy(IColumn groupBy) {
        sqlStatement().addGroupBy(groupBy);
        return getSql();
    }

    /**
     * `GROUP BY t1.column1, t2.column2, ...`语句
     *
     * @param groupBys 相关列列表
     * @return SQL对象
     * @see IColumn
     */
    public T groupBy(List<? extends IColumn> groupBys) {
        sqlStatement().addGroupBys(groupBys);
        return getSql();
    }

    /**
     * `HAVING`语句
     *
     * @param having having语句对象
     * @return SQL对象
     */
    public T having(Having having) {
        sqlStatement().addHaving(having);
        return getSql();
    }

    /**
     * `HAVING`语句
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    public T having(List<Having> havings) {
        sqlStatement().addHavings(havings);
        return getSql();
    }

    /**
     * `HAVING AND item`语句
     *
     * @param havingItem having语句对象
     * @return SQL对象
     */
    public T andHaving(HavingItem havingItem) {
        sqlStatement().addHaving(Having.and(havingItem));
        return getSql();
    }

    /**
     * `HAVING AND (havings)`语句
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    public T andHaving(List<Having> havings) {
        sqlStatement().addHaving(Having.and(havings));
        return getSql();
    }


    /**
     * `HAVING OR item`语句
     *
     * @param havingItem having语句对象
     * @return SQL对象
     */
    public T orHaving(HavingItem havingItem) {
        sqlStatement().addHaving(Having.or(havingItem));
        return getSql();
    }

    /**
     * `HAVING OR (havings)`语句（多个OR关系）
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    public T orHaving(List<Having> havings) {
        sqlStatement().addHaving(Having.or(havings));
        return getSql();
    }

    /**
     * `ORDER BY` 语句
     *
     * @param orderBy 相关orderBy
     * @return SQL对象
     * @see OrderBy
     */
    public T orderBy(IOrderBy orderBy) {
        sqlStatement().addOrderBy(orderBy);
        return getSql();
    }

    /**
     * `ORDER BY` 语句
     *
     * @param orderBys 相关orderBy列表
     * @return SQL对象
     * @see OrderBy
     */
    public T orderBy(List<? extends IOrderBy> orderBys) {
        sqlStatement().addOrderBys(orderBys);
        return getSql();
    }

    /**
     * `LIMIT #{start}, #{limit}` 语句
     *
     * @param start 开始位置
     * @param limit 限制条数
     * @return SQL对象
     */
    public T limit(int start, int limit) {
        sqlStatement().setLimit(start, limit);
        return getSql();
    }

    /**
     * `LIMIT 0, #{limit}` 语句
     *
     * @param limit 限制条数
     * @return SQL对象
     */
    public T limit(int limit) {
        sqlStatement().setLimit(limit);
        return getSql();
    }


    /**
     * 获取COUNT相关的SQL参数对象
     *
     * @return SQL参数对象
     */
    public SqlParams countSqlParams() {
        return sqlStatement().countSqlParams();
    }

    /**
     * 获取相关的SQLParams对象
     *
     * @return SQL参数对象
     */
    public SqlParams getSqlParams() {
        return sqlStatement().getSqlParams();
    }

    private SqlStatement sqlStatement() {
        return sqlStatement;
    }


}
