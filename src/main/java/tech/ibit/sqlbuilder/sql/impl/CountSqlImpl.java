package tech.ibit.sqlbuilder.sql.impl;

import org.apache.commons.lang.StringUtils;
import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.CountSql;
import tech.ibit.sqlbuilder.sql.support.UseAliasSupport;
import tech.ibit.sqlbuilder.sql.support.impl.*;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CountSql实现
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class CountSqlImpl
        implements CountSql,
        UseAliasSupport, PrepareStatementBuildSupport {

    /**
     * distinct 支持
     */
    private DistinctSupportImpl<CountSql> distinctSupport;

    /**
     * 列支持
     */
    private ColumnSupportImpl<CountSql> columnSupport;

    /**
     * From 支持
     */
    private FromSupportImpl<CountSql> fromSupport;

    /**
     * join on 支持
     */
    private JoinOnSupportImpl<CountSql> joinOnSupport;

    /**
     * where 支持
     */
    private WhereSupportImpl<CountSql> whereSupport;

    /**
     * group by 支持
     */
    private GroupBySupportImpl<CountSql> groupBySupport;

    /**
     * having 支持
     */
    private HavingSupportImpl<CountSql> havingSupport;

    /**
     * 无参构造函数
     */
    public CountSqlImpl() {
        this(true);
    }

    /**
     * 构造函数
     *
     * @param needToInit 是否初始化
     */
    public CountSqlImpl(boolean needToInit) {
        if (needToInit) {
            this.columnSupport = new ColumnSupportImpl<>(this);
            this.fromSupport = new FromSupportImpl<>(this);
            this.distinctSupport = new DistinctSupportImpl<>(this);
            this.joinOnSupport = new JoinOnSupportImpl<>(this);
            this.whereSupport = new WhereSupportImpl<>(this);
            this.groupBySupport = new GroupBySupportImpl<>(this);
            this.havingSupport = new HavingSupportImpl<>(this);
        }
    }


    @Override
    public CountSql column(List<? extends IColumn> columns) {
        return columnSupport.column(columns);
    }

    @Override
    public CountSql column(IColumn column) {
        return columnSupport.column(column);
    }

    @Override
    public CountSql columnPo(Class<?> poClass) {
        return columnSupport.columnPo(poClass);
    }

    @Override
    public CountSql from(Table table) {
        return fromSupport.from(table);
    }

    @Override
    public CountSql from(List<Table> tables) {
        return fromSupport.from(tables);
    }

    @Override
    public CountSql distinct() {
        return distinctSupport.distinct();
    }

    @Override
    public CountSql distinct(boolean distinct) {
        return distinctSupport.distinct(distinct);
    }

    @Override
    public CountSql having(Criteria having) {
        return havingSupport.having(having);
    }

    @Override
    public CountSql having(List<Criteria> havings) {
        return havingSupport.having(havings);
    }

    @Override
    public CountSql andHaving(CriteriaItem havingItem) {
        return havingSupport.andHaving(havingItem);
    }

    @Override
    public CountSql andHaving(List<Criteria> havings) {
        return havingSupport.andHaving(havings);
    }

    @Override
    public CountSql orHaving(CriteriaItem havingItem) {
        return havingSupport.orHaving(havingItem);
    }

    @Override
    public CountSql orHaving(List<Criteria> havings) {
        return havingSupport.orHaving(havings);
    }

    @Override
    public CountSql joinOn(JoinOn joinOn) {
        return joinOnSupport.joinOn(joinOn);
    }

    @Override
    public CountSql joinOn(List<JoinOn> joinOns) {
        return joinOnSupport.joinOn(joinOns);
    }

    @Override
    public CountSql joinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.joinOn(table, columnPairs);
    }

    @Override
    public CountSql leftJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.leftJoinOn(table, columnPairs);
    }

    @Override
    public CountSql rightJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.rightJoinOn(table, columnPairs);
    }

    @Override
    public CountSql fullJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.fullJoinOn(table, columnPairs);
    }

    @Override
    public CountSql innerJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.innerJoinOn(table, columnPairs);
    }

    @Override
    public CountSql complexLeftJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexLeftJoinOn(table, criteriaItems);
    }

    @Override
    public CountSql complexRightJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexRightJoinOn(table, criteriaItems);
    }

    @Override
    public CountSql complexFullJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexFullJoinOn(table, criteriaItems);
    }

    @Override
    public CountSql complexInnerJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexInnerJoinOn(table, criteriaItems);
    }

    @Override
    public CountSql where(Criteria criteria) {
        return whereSupport.where(criteria);
    }

    @Override
    public CountSql where(List<Criteria> criterion) {
        return whereSupport.where(criterion);
    }

    @Override
    public CountSql andWhere(CriteriaItem item) {
        return whereSupport.andWhere(item);
    }

    @Override
    public CountSql andWhere(List<Criteria> criterion) {
        return whereSupport.andWhere(criterion);
    }

    @Override
    public CountSql orWhere(CriteriaItem item) {
        return whereSupport.orWhere(item);
    }

    @Override
    public CountSql orWhere(List<Criteria> criterion) {
        return whereSupport.orWhere(criterion);
    }

    @Override
    public CountSql groupBy(Column groupBy) {
        return groupBySupport.groupBy(groupBy);
    }

    @Override
    public CountSql groupBy(List<Column> groupBys) {
        return groupBySupport.groupBy(groupBys);
    }

    @Override
    public boolean isUseAlias() {
        return true;
    }


    /**
     * Sets the distinctSupport
     * <p>You can use getDistinctSupport() to get the value of distinctSupport</p>
     *
     * @param distinctSupport distinctSupport
     */
    public void setDistinctSupport(DistinctSupportImpl<CountSql> distinctSupport) {
        this.distinctSupport = distinctSupport;
    }

    /**
     * Sets the columnSupport
     * <p>You can use getColumnSupport() to get the value of columnSupport</p>
     *
     * @param columnSupport columnSupport
     */
    public void setColumnSupport(ColumnSupportImpl<CountSql> columnSupport) {
        this.columnSupport = columnSupport;
    }

    /**
     * Sets the fromSupport
     * <p>You can use getFromSupport() to get the value of fromSupport</p>
     *
     * @param fromSupport fromSupport
     */
    public void setFromSupport(FromSupportImpl<CountSql> fromSupport) {
        this.fromSupport = fromSupport;
    }

    /**
     * Sets the joinOnSupport
     * <p>You can use getJoinOnSupport() to get the value of joinOnSupport</p>
     *
     * @param joinOnSupport joinOnSupport
     */
    public void setJoinOnSupport(JoinOnSupportImpl<CountSql> joinOnSupport) {
        this.joinOnSupport = joinOnSupport;
    }

    /**
     * Sets the whereSupport
     * <p>You can use getWhereSupport() to get the value of whereSupport</p>
     *
     * @param whereSupport whereSupport
     */
    public void setWhereSupport(WhereSupportImpl<CountSql> whereSupport) {
        this.whereSupport = whereSupport;
    }

    /**
     * Sets the groupBySupport
     * <p>You can use getGroupBySupport() to get the value of groupBySupport</p>
     *
     * @param groupBySupport groupBySupport
     */
    public void setGroupBySupport(GroupBySupportImpl<CountSql> groupBySupport) {
        this.groupBySupport = groupBySupport;
    }

    /**
     * Sets the havingSupport
     * <p>You can use getHavingSupport() to get the value of havingSupport</p>
     *
     * @param havingSupport havingSupport
     */
    public void setHavingSupport(HavingSupportImpl<CountSql> havingSupport) {
        this.havingSupport = havingSupport;
    }

    @Override
    public PrepareStatement getPrepareStatement() {

        boolean useAlias = isUseAlias();
        boolean distinct = distinctSupport.getDistinct().isValue();

        PrepareStatement columnPrepareStatement = columnSupport.getColumnPrepareStatement(useAlias);

        String columnStr = columnPrepareStatement.getPrepareSql();
        if (StringUtils.isBlank(columnStr) && distinct) {
            throw new SqlException("Columns cannot be empty while at distinct statement!");
        }

        // 如果from为空的，则使用默认
        if (CollectionUtils.isEmpty(fromSupport.getTable().getItems())) {
            throw SqlException.tableNotFound();
        }

        // 构造count字段
        StringBuilder prepareSql = new StringBuilder();
        prepareSql.append("SELECT COUNT(")
                .append(distinct ? ("DISTINCT " + columnStr) : "*")
                .append(")");

        List<ColumnValue> values = new ArrayList<>();

        append(
                Arrays.asList(
                        fromSupport.getFromPrepareStatement(useAlias),
                        joinOnSupport.getJoinOnPrepareStatement(useAlias),
                        whereSupport.getWherePrepareStatement(useAlias),
                        groupBySupport.getGroupByPrepareStatement(useAlias),
                        havingSupport.getHavingPrepareStatement(useAlias)
                ), prepareSql, values
        );
        return new PrepareStatement(prepareSql.toString(), values);
    }
}
