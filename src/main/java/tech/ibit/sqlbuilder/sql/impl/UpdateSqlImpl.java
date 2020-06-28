package tech.ibit.sqlbuilder.sql.impl;

import lombok.Getter;
import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.UpdateSql;
import tech.ibit.sqlbuilder.sql.field.ListField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UpdateSqlImpl
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Getter
public class UpdateSqlImpl implements UpdateSql {

    /**
     * from
     */
    private ListField<Table> updateTable = new ListField<>();

    /**
     * join on
     */
    private ListField<JoinOn> joinOn = new ListField<>();

    /**
     * set
     */
    private ListField<SetItem> set = new ListField<>();

    /**
     * where
     */
    private ListField<Criteria> where = new ListField<>();

    @Override
    public boolean isUseAlias() {
        return true;
    }

    @Override
    public UpdateSql getSql() {
        return this;
    }

    @Override
    public PrepareStatement getPrepareStatement() {
        if (getWhere().getItems().isEmpty()) {
            throw new SqlException("Where cannot be empty when do updating!");
        }

        if (getSet().getItems().isEmpty()) {
            throw new SqlException("Set cannot be empty when do updating!");
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();
        boolean useAlias = isUseAlias();

        append(
                Arrays.asList(
                        getUpdatePrepareStatement(useAlias),
                        getJoinOnPrepareStatement(useAlias),
                        getSetItemPrepareStatement(useAlias),
                        getWherePrepareStatement(useAlias)
                ), prepareSql, values);

        return new PrepareStatement(prepareSql.toString(), values);
    }
}
