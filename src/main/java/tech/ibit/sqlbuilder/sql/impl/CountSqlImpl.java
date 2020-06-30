package tech.ibit.sqlbuilder.sql.impl;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;
import tech.ibit.sqlbuilder.sql.CountSql;
import tech.ibit.sqlbuilder.sql.SearchSql;
import tech.ibit.sqlbuilder.sql.field.BooleanField;
import tech.ibit.sqlbuilder.sql.field.ListField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CountSqlImpl
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Getter
@Setter
public class CountSqlImpl implements CountSql {


    /**
     * 是否distinct
     */
    private BooleanField distinct = new BooleanField(false);

    /**
     * form
     */
    private ListField<Table> from = new ListField<>();

    /**
     * Join on
     */
    private ListField<JoinOn> joinOn = new ListField<>();

    /**
     * where
     */
    private ListField<Criteria> where = new ListField<>();


    /**
     * group by
     */
    private ListField<Column> groupBy = new ListField<>();

    /**
     * Having
     */
    private ListField<Criteria> having = new ListField<>();

    /**
     * 列
     */
    private ListField<IColumn> column = new ListField<>();


    @Override
    public CountSql getSql() {
        return this;
    }

    @Override
    public boolean isUseAlias() {
        return true;
    }

    @Override
    public PrepareStatement getPrepareStatement() {

        boolean useAlias = isUseAlias();
        boolean distinct = getDistinct().isValue();

        PrepareStatement columnPrepareStatement = getColumnPrepareStatement(useAlias);

        String columnStr = columnPrepareStatement.getPrepareSql();
        if (StringUtils.isBlank(columnStr) && distinct) {
            throw new SqlException("Columns cannot be empty while at distinct statement!");
        }

        // 构造count字段
        StringBuilder prepareSql = new StringBuilder();
        prepareSql.append("SELECT COUNT(")
                .append(distinct ? ("DISTINCT " + columnStr) : "*")
                .append(")");

        List<ColumnValue> values = new ArrayList<>();

        append(
                Arrays.asList(
                        getFromPrepareStatement(useAlias),
                        getJoinOnPrepareStatement(useAlias),
                        getWherePrepareStatement(useAlias),
                        getGroupByPrepareStatement(useAlias),
                        getHavingPrepareStatement(useAlias)
                ), prepareSql, values
        );
        return new PrepareStatement(prepareSql.toString(), values);
    }


    @Override
    public SearchSql toSearchSql() {
        SearchSqlImpl searchSql = new SearchSqlImpl();
        searchSql.setDistinct(distinct);
        searchSql.setFrom(from);
        searchSql.setJoinOn(joinOn);
        searchSql.setWhere(where);
        searchSql.setGroupBy(groupBy);
        searchSql.setHaving(having);
        searchSql.setColumn(column);
        return searchSql;
    }
}
