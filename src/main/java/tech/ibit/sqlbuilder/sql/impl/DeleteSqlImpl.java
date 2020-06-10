package tech.ibit.sqlbuilder.sql.impl;

import lombok.Getter;
import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.DeleteSql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DeleteSqlImpl
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Getter
public class DeleteSqlImpl implements DeleteSql {

    /**
     * 删除项
     */
    private ListField<Table> deleteItem = new ListField<>();

    /**
     * 表
     */
    private ListField<Table> from = new ListField<>();

    /**
     * Join on
     */
    private ListField<JoinOn> joinOn = new ListField<>();

    /**
     * where语句
     */
    private ListField<Criteria> where = new ListField<>();


    @Override
    public DeleteSql getSql() {
        return this;
    }

    @Override
    public boolean isUseAlias() {
        return true;
    }

    @Override
    public PrepareStatement getPrepareStatement() {
        if (getWhere().getItems().isEmpty()) {
            throw new SqlException("Where cannot be empty when do deleting!");
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();

        boolean useAlias = isUseAlias();

        append(
                Arrays.asList(
                        getDeleteItemPrepareStatement(useAlias),
                        getFromPrepareStatement(useAlias),
                        getJoinOnPrepareStatement(useAlias),
                        getWherePrepareStatement(useAlias)
                ), prepareSql, values);

        return new PrepareStatement(prepareSql.toString(), values);
    }


}
