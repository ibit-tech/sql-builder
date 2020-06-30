package tech.ibit.sqlbuilder.sql.impl;

import lombok.Getter;
import lombok.Setter;
import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.sql.CountSql;
import tech.ibit.sqlbuilder.sql.SearchSql;
import tech.ibit.sqlbuilder.sql.field.BooleanField;
import tech.ibit.sqlbuilder.sql.field.LimitField;
import tech.ibit.sqlbuilder.sql.field.ListField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SearchSqlImpl
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Getter
@Setter
public class SearchSqlImpl implements SearchSql {


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
     * Order by
     */
    private ListField<IOrderBy> orderBy = new ListField<>();

    /**
     * limit
     */
    private LimitField limit = new LimitField();

    /**
     * 列
     */
    private ListField<IColumn> column = new ListField<>();


    @Override
    public SearchSql getSql() {
        return this;
    }

    @Override
    public boolean isUseAlias() {
        return true;
    }

    @Override
    public PrepareStatement getPrepareStatement() {
        boolean distinct = getDistinct().isValue();

        StringBuilder prepareSql = new StringBuilder();
        prepareSql.append(distinct ? "SELECT DISTINCT " : "SELECT ");

        List<ColumnValue> values = new ArrayList<>();

        boolean useAlias = isUseAlias();

        append(
                Arrays.asList(
                        getColumnPrepareStatement(useAlias),
                        getFromPrepareStatement(useAlias),
                        getJoinOnPrepareStatement(useAlias),
                        getWherePrepareStatement(useAlias),
                        getGroupByPrepareStatement(useAlias),
                        getHavingPrepareStatement(useAlias),
                        getOrderByPrepareStatement(useAlias),
                        getLimitPrepareStatement()

                ), prepareSql, values
        );
        return new PrepareStatement(prepareSql.toString(), values);
    }

    @Override
    public CountSql toCountSql() {
        CountSqlImpl countSql = new CountSqlImpl();
        countSql.setDistinct(distinct);
        countSql.setFrom(from);
        countSql.setJoinOn(joinOn);
        countSql.setWhere(where);
        countSql.setGroupBy(groupBy);
        countSql.setHaving(having);
        countSql.setColumn(column);
        return countSql;
    }
}
