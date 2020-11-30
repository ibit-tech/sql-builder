package tech.ibit.sqlbuilder.sql.impl;

import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.UpdateSql;
import tech.ibit.sqlbuilder.sql.support.UseAliasSupport;
import tech.ibit.sqlbuilder.sql.support.impl.*;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UpdateSql实现
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class UpdateSqlImpl implements UpdateSql
        , UseAliasSupport, PrepareStatementBuildSupport {

    /**
     * Update table 支持
     */
    private final UpdateTableSupportImpl<UpdateSql> updateTableSupport;

    /**
     * set 支持
     */
    private final SetSupportImpl<UpdateSql> setSupport;

    /**
     * join on 支持
     */
    private final JoinOnSupportImpl<UpdateSql> joinOnSupport;

    /**
     * where 支持
     */
    private final WhereSupportImpl<UpdateSql> whereSupport;


    public UpdateSqlImpl() {
        this.updateTableSupport = new UpdateTableSupportImpl<>(this);
        this.setSupport = new SetSupportImpl<>(this);
        this.joinOnSupport = new JoinOnSupportImpl<>(this);
        this.whereSupport = new WhereSupportImpl<>(this);
    }

    @Override
    public boolean isUseAlias() {
        return true;
    }

    @Override
    public UpdateSql update(Table table) {
        return updateTableSupport.update(table);
    }

    @Override
    public UpdateSql update(List<Table> tables) {
        return updateTableSupport.update(tables);
    }

    @Override
    public UpdateSql set(SetItem item) {
        return setSupport.set(item);
    }

    @Override
    public UpdateSql set(List<SetItem> items) {
        return setSupport.set(items);
    }

    @Override
    public UpdateSql set(Column column, Object value) {
        return setSupport.set(column, value);
    }

    @Override
    public UpdateSql increaseSet(Column column, Number value) {
        return setSupport.increaseSet(column, value);
    }

    @Override
    public UpdateSql decreaseSet(Column column, Number value) {
        return setSupport.decreaseSet(column, value);
    }

    @Override
    public UpdateSql joinOn(JoinOn joinOn) {
        return joinOnSupport.joinOn(joinOn);
    }

    @Override
    public UpdateSql joinOn(List<JoinOn> joinOns) {
        return joinOnSupport.joinOn(joinOns);
    }

    @Override
    public UpdateSql joinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.joinOn(table, columnPairs);
    }

    @Override
    public UpdateSql leftJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.leftJoinOn(table, columnPairs);
    }

    @Override
    public UpdateSql rightJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.rightJoinOn(table, columnPairs);
    }

    @Override
    public UpdateSql fullJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.fullJoinOn(table, columnPairs);
    }

    @Override
    public UpdateSql innerJoinOn(Table table, List<Column> columnPairs) {
        return joinOnSupport.innerJoinOn(table, columnPairs);
    }

    @Override
    public UpdateSql complexLeftJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexLeftJoinOn(table, criteriaItems);
    }

    @Override
    public UpdateSql complexRightJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexRightJoinOn(table, criteriaItems);
    }

    @Override
    public UpdateSql complexFullJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexFullJoinOn(table, criteriaItems);
    }

    @Override
    public UpdateSql complexInnerJoinOn(Table table, List<CriteriaItem> criteriaItems) {
        return joinOnSupport.complexInnerJoinOn(table, criteriaItems);
    }

    @Override
    public UpdateSql where(Criteria criteria) {
        return whereSupport.where(criteria);
    }

    @Override
    public UpdateSql where(List<Criteria> criterion) {
        return whereSupport.where(criterion);
    }

    @Override
    public UpdateSql andWhere(CriteriaItem item) {
        return whereSupport.andWhere(item);
    }

    @Override
    public UpdateSql andWhere(List<Criteria> criterion) {
        return whereSupport.andWhere(criterion);
    }

    @Override
    public UpdateSql orWhere(CriteriaItem item) {
        return whereSupport.orWhere(item);
    }

    @Override
    public UpdateSql orWhere(List<Criteria> criterion) {
        return whereSupport.orWhere(criterion);
    }

    @Override
    public PrepareStatement getPrepareStatement() {
        if (whereSupport.getWhere().getItems().isEmpty()) {
            throw new SqlException("Where cannot be empty when do updating!");
        }

        if (setSupport.getSet().getItems().isEmpty()) {
            throw new SqlException("Set cannot be empty when do updating!");
        }

        // 表名为空的时候，增加默认表
        if (CollectionUtils.isEmpty(updateTableSupport.getTable().getItems())) {
            throw SqlException.tableNotFound();
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();
        boolean useAlias = isUseAlias();

        append(
                Arrays.asList(
                        updateTableSupport.getUpdatePrepareStatement(useAlias),
                        joinOnSupport.getJoinOnPrepareStatement(useAlias),
                        setSupport.getSetItemPrepareStatement(useAlias),
                        whereSupport.getWherePrepareStatement(useAlias)
                ), prepareSql, values);

        return new PrepareStatement(prepareSql.toString(), values);
    }


}
