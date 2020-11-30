package tech.ibit.sqlbuilder.sql.impl;

import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.CountSql;
import tech.ibit.sqlbuilder.sql.QuerySql;
import tech.ibit.sqlbuilder.sql.support.UseAliasSupport;
import tech.ibit.sqlbuilder.sql.support.impl.*;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * QuerySql实现
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class QuerySqlImpl implements QuerySql,
        UseAliasSupport, PrepareStatementBuildSupport {

    /**
     * 列支持
     */
    private final ColumnSupportImpl<QuerySql> columnSupport;

    /**
     * From 支持
     */
    private final FromSupportImpl<QuerySql> fromSupport;

    /**
     * distinct 支持
     */
    private final DistinctSupportImpl<QuerySql> distinctSupport;

    /**
     * join on 支持
     */
    private final JoinOnSupportImpl<QuerySql> joinOnSupport;

    /**
     * where 支持
     */
    private final WhereSupportImpl<QuerySql> whereSupport;

    /**
     * group by 支持
     */
    private final GroupBySupportImpl<QuerySql> groupBySupport;

    /**
     * having 支持
     */
    private final HavingSupportImpl<QuerySql> havingSupport;

    /**
     * order by 支持
     */
    private final OrderBySupportImpl<QuerySql> orderBySupport;

    /**
     * limit 支持
     */
    private final LimitSupportImpl<QuerySql> limitSupport;

    /**
     * 构造函数
     */
    public QuerySqlImpl() {
        this.columnSupport = new ColumnSupportImpl<>(this);
        this.fromSupport = new FromSupportImpl<>(this);
        this.joinOnSupport = new JoinOnSupportImpl<>(this);
        this.whereSupport = new WhereSupportImpl<>(this);
        this.groupBySupport = new GroupBySupportImpl<>(this);
        this.distinctSupport = new DistinctSupportImpl<>(this);
        this.havingSupport = new HavingSupportImpl<>(this);
        this.orderBySupport = new OrderBySupportImpl<>(this);
        this.limitSupport = new LimitSupportImpl<>(this);
    }

    @Override
    public QuerySql column(List<? extends IColumn> columns) {
        return columnSupport.column(columns);
    }

    @Override
    public QuerySql column(IColumn column) {
        return columnSupport.column(column);
    }

    @Override
    public QuerySql columnPo(Class<?> poClass) {
        return columnSupport.columnPo(poClass);
    }

    @Override
    public QuerySql from(Table table) {
        return fromSupport.from(table);
    }

    @Override
    public QuerySql from(List<Table> tables) {
        return fromSupport.from(tables);
    }

    @Override
    public QuerySql distinct() {
        return distinctSupport.distinct();
    }

    @Override
    public QuerySql distinct(boolean distinct) {
        return distinctSupport.distinct(distinct);
    }

    @Override
    public QuerySql having(Criteria having) {
        return havingSupport.having(having);
    }

    @Override
    public QuerySql having(List<Criteria> havings) {
        return havingSupport.having(havings);
    }

    @Override
    public QuerySql andHaving(CriteriaItem havingItem) {
        return havingSupport.andHaving(havingItem);
    }

    @Override
    public QuerySql andHaving(List<Criteria> havings) {
        return havingSupport.andHaving(havings);
    }

    @Override
    public QuerySql orHaving(CriteriaItem havingItem) {
        return havingSupport.orHaving(havingItem);
    }

    @Override
    public QuerySql orHaving(List<Criteria> havings) {
        return havingSupport.orHaving(havings);
    }

    @Override
    public QuerySql joinOn(JoinOn joinOn) {
        return joinOnSupport.joinOn(joinOn);
    }

    @Override
    public QuerySql joinOn(List<JoinOn> joinOns) {
        return joinOnSupport.joinOn(joinOns);
    }

    @Override
    public QuerySql joinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.joinOn(table, columnPairs);
    }

    @Override
    public QuerySql leftJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.leftJoinOn(table, columnPairs);
    }

    @Override
    public QuerySql rightJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.rightJoinOn(table, columnPairs);
    }

    @Override
    public QuerySql fullJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.fullJoinOn(table, columnPairs);
    }

    @Override
    public QuerySql innerJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.innerJoinOn(table, columnPairs);
    }

    @Override
    public QuerySql complexLeftJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexLeftJoinOn(table, criteriaItems);
    }

    @Override
    public QuerySql complexRightJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexRightJoinOn(table, criteriaItems);
    }

    @Override
    public QuerySql complexFullJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexFullJoinOn(table, criteriaItems);
    }

    @Override
    public QuerySql complexInnerJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexInnerJoinOn(table, criteriaItems);
    }

    @Override
    public QuerySql where(Criteria criteria) {
        return whereSupport.where(criteria);
    }

    @Override
    public QuerySql where(List<Criteria> criterion) {
        return whereSupport.where(criterion);
    }

    @Override
    public QuerySql andWhere(CriteriaItem item) {
        return whereSupport.andWhere(item);
    }

    @Override
    public QuerySql andWhere(List<Criteria> criterion) {
        return whereSupport.andWhere(criterion);
    }

    @Override
    public QuerySql orWhere(CriteriaItem item) {
        return whereSupport.orWhere(item);
    }

    @Override
    public QuerySql orWhere(List<Criteria> criterion) {
        return whereSupport.orWhere(criterion);
    }

    @Override
    public QuerySql groupBy(Column groupBy) {
        return groupBySupport.groupBy(groupBy);
    }

    @Override
    public QuerySql groupBy(List<Column> groupBys) {
        return groupBySupport.groupBy(groupBys);
    }

    @Override
    public QuerySql orderBy(IOrderBy orderBy) {
        return orderBySupport.orderBy(orderBy);
    }

    @Override
    public QuerySql orderBy(List<IOrderBy> orderBys) {
        return orderBySupport.orderBy(orderBys);
    }

    @Override
    public QuerySql orderBy(IColumn column) {
        return orderBySupport.orderBy(column);
    }

    @Override
    public QuerySql orderBy(IColumn column, boolean desc) {
        return orderBySupport.orderBy(column, desc);
    }


    @Override
    public QuerySql limit(int start, int limit) {
        return limitSupport.limit(start, limit);
    }

    @Override
    public QuerySql limit(int limit) {
        return limitSupport.limit(limit);
    }


    @Override
    public boolean isUseAlias() {
        return true;
    }

    @Override
    public PrepareStatement getPrepareStatement() {


        // 列为空的时候，补充默认列
        if (CollectionUtils.isEmpty(columnSupport.getColumn().getItems())) {
            throw SqlException.columnNotFound();
        }

        // 表为空的时候，补充默认表
        if (CollectionUtils.isEmpty(fromSupport.getTable().getItems())) {
            throw SqlException.tableNotFound();
        }

        boolean distinct = distinctSupport.getDistinct().isValue();

        StringBuilder prepareSql = new StringBuilder();
        prepareSql.append(distinct ? "SELECT DISTINCT " : "SELECT ");

        List<ColumnValue> values = new ArrayList<>();

        boolean useAlias = isUseAlias();

        append(
                Arrays.asList(
                        columnSupport.getColumnPrepareStatement(useAlias),
                        fromSupport.getFromPrepareStatement(useAlias),
                        joinOnSupport.getJoinOnPrepareStatement(useAlias),
                        whereSupport.getWherePrepareStatement(useAlias),
                        groupBySupport.getGroupByPrepareStatement(useAlias),
                        havingSupport.getHavingPrepareStatement(useAlias),
                        orderBySupport.getOrderByPrepareStatement(useAlias),
                        limitSupport.getLimitPrepareStatement()

                ), prepareSql, values
        );
        return new PrepareStatement(prepareSql.toString(), values);
    }

    @Override
    public CountSql toCountSql() {
        CountSqlImpl countSql = new CountSqlImpl(false);
        countSql.setDistinctSupport(distinctSupport.copy(countSql));
        countSql.setFromSupport(fromSupport.copy(countSql));
        countSql.setJoinOnSupport(joinOnSupport.copy(countSql));
        countSql.setWhereSupport(whereSupport.copy(countSql));
        countSql.setGroupBySupport(groupBySupport.copy(countSql));
        countSql.setHavingSupport(havingSupport.copy(countSql));
        countSql.setColumnSupport(columnSupport.copy(countSql));
        return countSql;
    }
}
