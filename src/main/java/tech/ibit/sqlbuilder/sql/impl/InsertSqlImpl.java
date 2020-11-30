package tech.ibit.sqlbuilder.sql.impl;

import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.InsertSql;
import tech.ibit.sqlbuilder.sql.support.UseAliasSupport;
import tech.ibit.sqlbuilder.sql.support.impl.InsertTableSupportImpl;
import tech.ibit.sqlbuilder.sql.support.impl.OnDuplicateKeyUpdateSupportImpl;
import tech.ibit.sqlbuilder.sql.support.impl.PrepareStatementBuildSupport;
import tech.ibit.sqlbuilder.sql.support.impl.ValuesSupportImpl;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * InsertSql实现
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class InsertSqlImpl implements InsertSql,
        UseAliasSupport, PrepareStatementBuildSupport {

    /**
     * insert table 支持
     */
    private final InsertTableSupportImpl<InsertSql> insertTableSupport;

    /**
     * value 支持
     */
    private final ValuesSupportImpl<InsertSql> valuesSupport;

    /**
     * on duplicate key update 支持
     */
    private final OnDuplicateKeyUpdateSupportImpl<InsertSql> onDuplicateKeyUpdateSupport;


    public InsertSqlImpl() {
        this.insertTableSupport = new InsertTableSupportImpl<>(this);
        this.valuesSupport = new ValuesSupportImpl<>(this);
        this.onDuplicateKeyUpdateSupport = new OnDuplicateKeyUpdateSupportImpl<>(this);
    }

    @Override
    public InsertSql insert(Table table) {
        return insertTableSupport.insert(table);
    }

    @Override
    public InsertSql insert(List<Table> tables) {
        return insertTableSupport.insert(tables);
    }

    @Override
    public InsertSql values(List<? extends ColumnValue> columnValues) {
        return valuesSupport.values(columnValues);
    }

    @Override
    public InsertSql values(ColumnValue columnValue) {
        return valuesSupport.values(columnValue);
    }

    @Override
    public InsertSql values(Column column, Object value) {
        return valuesSupport.values(column, value);
    }

    @Override
    public InsertSql values(List<Column> columns, List<Object> values) {
        return valuesSupport.values(columns, values);
    }

    @Override
    public InsertSql onDuplicateKeyUpdate(SetItem item) {
        return onDuplicateKeyUpdateSupport.onDuplicateKeyUpdate(item);
    }

    @Override
    public InsertSql onDuplicateKeyUpdate(List<SetItem> items) {
        return onDuplicateKeyUpdateSupport.onDuplicateKeyUpdate(items);
    }

    @Override
    public boolean isUseAlias() {
        return false;
    }


    @Override
    public PrepareStatement getPrepareStatement() {


        // 没有指定表，指定默认的
        if (CollectionUtils.isEmpty(insertTableSupport.getTable().getItems())) {
            throw SqlException.tableNotFound();
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();

        append(
                Arrays.asList(
                        insertTableSupport.getInsertPrepareStatement(isUseAlias()),
                        valuesSupport.getColumnPrepareStatement(),
                        valuesSupport.getValuePrepareStatement(),
                        onDuplicateKeyUpdateSupport.getOnDuplicateKeyUpdatePrepareStatement(isUseAlias())
                ), prepareSql, values);


        return new PrepareStatement(prepareSql.toString(), values);
    }

}
