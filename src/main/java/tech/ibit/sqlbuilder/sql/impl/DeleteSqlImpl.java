package tech.ibit.sqlbuilder.sql.impl;

import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.DeleteSql;
import tech.ibit.sqlbuilder.sql.support.UseAliasSupport;
import tech.ibit.sqlbuilder.sql.support.impl.*;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DeleteSql实现
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class DeleteSqlImpl implements DeleteSql,
        UseAliasSupport, PrepareStatementBuildSupport {

    /**
     * delete 支持
     */
    private final DeleteSupportImpl<DeleteSql> deleteSupport;

    /**
     * From 支持
     */
    private final FromSupportImpl<DeleteSql> fromSupport;

    /**
     * where 支持
     */
    private final WhereSupportImpl<DeleteSql> whereSupport;

    /**
     * join on 支持
     */
    private final JoinOnSupportImpl<DeleteSql> joinOnSupport;

    public DeleteSqlImpl() {
        this.deleteSupport = new DeleteSupportImpl<>(this);
        this.fromSupport = new FromSupportImpl<>(this);
        this.whereSupport = new WhereSupportImpl<>(this);
        this.joinOnSupport = new JoinOnSupportImpl<>(this);
    }

    @Override
    public DeleteSql delete(Table table) {
        return deleteSupport.delete(table);
    }

    @Override
    public DeleteSql delete(List<Table> tables) {
        return deleteSupport.delete(tables);
    }

    @Override
    public DeleteSql from(Table table) {
        return fromSupport.from(table);
    }

    @Override
    public DeleteSql from(List<Table> tables) {
        return fromSupport.from(tables);
    }

    @Override
    public DeleteSql joinOn(JoinOn joinOn) {
        return joinOnSupport.joinOn(joinOn);
    }

    @Override
    public DeleteSql joinOn(List<JoinOn> joinOns) {
        return joinOnSupport.joinOn(joinOns);
    }

    @Override
    public DeleteSql joinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.joinOn(table, columnPairs);
    }

    @Override
    public DeleteSql leftJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.leftJoinOn(table, columnPairs);
    }

    @Override
    public DeleteSql rightJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.rightJoinOn(table, columnPairs);
    }

    @Override
    public DeleteSql fullJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.fullJoinOn(table, columnPairs);
    }

    @Override
    public DeleteSql innerJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.innerJoinOn(table, columnPairs);
    }

    @Override
    public DeleteSql complexLeftJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexLeftJoinOn(table, criteriaItems);
    }

    @Override
    public DeleteSql complexRightJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexRightJoinOn(table, criteriaItems);
    }

    @Override
    public DeleteSql complexFullJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexFullJoinOn(table, criteriaItems);
    }

    @Override
    public DeleteSql complexInnerJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexInnerJoinOn(table, criteriaItems);
    }

    @Override
    public DeleteSql where(Criteria criteria) {
        return whereSupport.where(criteria);
    }

    @Override
    public DeleteSql where(List<Criteria> criterion) {
        return whereSupport.where(criterion);
    }

    @Override
    public DeleteSql andWhere(CriteriaItem item) {
        return whereSupport.andWhere(item);
    }

    @Override
    public DeleteSql andWhere(List<Criteria> criterion) {
        return whereSupport.andWhere(criterion);
    }

    @Override
    public DeleteSql orWhere(CriteriaItem item) {
        return whereSupport.orWhere(item);
    }

    @Override
    public DeleteSql orWhere(List<Criteria> criterion) {
        return whereSupport.orWhere(criterion);
    }

    @Override
    public boolean isUseAlias() {
        return isMultiTables();
    }

    /**
     * 是否关联多张表
     *
     * @return 关联多张表
     */
    private boolean isMultiTables() {
        return fromSupport.getTable().getItems().size() > 1 || joinOnSupport.getJoinOn().getItems().size() > 0;
    }

    @Override
    public PrepareStatement getPrepareStatement() {
        if (whereSupport.getWhere().getItems().isEmpty()) {
            throw new SqlException("Where cannot be empty when do deleting!");
        }

        // 补充默认的表
        if (CollectionUtils.isEmpty(fromSupport.getTable().getItems())) {
            throw SqlException.tableNotFound();
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();

        boolean useAlias = isUseAlias();
        boolean multiTables = isMultiTables();

        prepareSql.append("DELETE");

        append(
                Arrays.asList(
                        deleteSupport.getDeleteItemPrepareStatement(multiTables),
                        fromSupport.getFromPrepareStatement(useAlias),
                        joinOnSupport.getJoinOnPrepareStatement(useAlias),
                        whereSupport.getWherePrepareStatement(useAlias)
                ), prepareSql, values);

        return new PrepareStatement(prepareSql.toString(), values);
    }


    @Override
    public DeleteSql deleteFrom(Table table) {
        delete(table);
        from(table);
        return this;
    }

    @Override
    public DeleteSql deleteFrom(List<Table> tables) {
        delete(tables);
        from(tables);
        return this;
    }

}
