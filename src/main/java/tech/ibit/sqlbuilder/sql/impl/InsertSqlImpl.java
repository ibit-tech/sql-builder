package tech.ibit.sqlbuilder.sql.impl;

import lombok.Getter;
import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.sql.InsertSql;
import tech.ibit.sqlbuilder.sql.field.ListField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * InsertSqlImpl
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Getter
public class InsertSqlImpl implements InsertSql {

    /**
     * from
     */
    private ListField<Table> insertTable = new ListField<>();

    /**
     * column
     */
    private ListField<Column> column = new ListField<>();

    /**
     * value
     */
    private ListField<Object> value = new ListField<>();


    @Override
    public boolean isUseAlias() {
        return false;
    }

    @Override
    public InsertSql getSql() {
        return this;
    }

    @Override
    public PrepareStatement getPrepareStatement() {

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();

        append(
                Arrays.asList(
                        getInsertPrepareStatement(isUseAlias()),
                        getColumnPrepareStatement(),
                        getValuePrepareStatement()
                ), prepareSql, values);


        return new PrepareStatement(prepareSql.toString(), values);
    }
}
