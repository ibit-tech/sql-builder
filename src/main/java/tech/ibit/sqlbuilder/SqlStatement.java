package tech.ibit.sqlbuilder;

import org.apache.commons.lang.StringUtils;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * SQL语句构造对象
 *
 * @author IBIT TECH
 * @version 1.0
 */
class SqlStatement extends AbstractSqlStatement {

    /**
     * 是否使用别名
     */
    private boolean useAlias = true;

    /**
     * 设置是否使用别名
     *
     * @param useAlias 是否使用别名
     */
    void setUseAlias(boolean useAlias) {
        this.useAlias = useAlias;
    }

    /**
     * 增加`JOIN ON`语句
     *
     * @param type        `JOIN ON 类型`
     * @param table       表
     * @param columnPairs 列对（ON等号部分）
     */
    void addJoinOn(SqlStatement.JoinOnType type, Table table, List<Column> columnPairs) {
        StringBuilder sb = new StringBuilder();
        sb.append(null == type ? "" : (type.name() + " "))
                .append("JOIN ")
                .append(getTableName(table))
                .append(" ON ");
        for (int i = 0; i < columnPairs.size(); i += 2) {
            if (i != 0) {
                sb.append(" AND ");
            }
            sb.append(CriteriaMaker.equalsTo(getColumnName(columnPairs.get(i)), getColumnName(columnPairs.get(i + 1))));
        }
        joinOn.add(sb.toString());
    }

    /**
     * 增加列
     *
     * @param column 列
     */
    void addColumn(IColumn column) {
        this.columns.add(getColumnName(column));
    }

    /**
     * 增加列
     *
     * @param columns 列列表
     */
    void addColumns(List<? extends IColumn> columns) {
        columns.forEach(this::addColumn);
    }

    /**
     * 增加表
     *
     * @param table 表
     */
    void addFrom(Table table) {
        from.add(useAlias ? table.getNameWithAlias() : table.getName());
    }

    /**
     * 增加表
     *
     * @param tables 表列表
     */
    void addFroms(List<Table> tables) {
        tables.forEach(this::addFrom);
    }

    /**
     * 增加删除的表
     *
     * @param table
     */
    void addDeleteTable(Table table) {
        deleteTables.add((useAlias ? table.getAlias() : table.getName()) + ".*");
    }


    /**
     * 增加删除的表
     *
     * @param tables
     */
    void addDeleteTable(List<Table> tables) {
        tables.forEach(this::addDeleteTable);
    }

    /**
     * 增加`SET`值
     *
     * @param set 列-值对
     */
    void addSet(ColumnValue set) {
        sets.add(CriteriaMaker.equalsTo(getColumnName(set.getColumn())));
        values.add(set.getValue());
    }

    /**
     * 增加`SET`值
     *
     * @param sets 列-值对列表
     */
    void addSets(List<? extends ColumnValue> sets) {
        sets.forEach(this::addSet);
    }

    /**
     * 增加自增`SET`值
     *
     * @param set 列-值对
     */
    void addIncreaseSet(ColumnValue set) {
        String columnName = getColumnName(set.getColumn());
        sets.add(CriteriaMaker.equalsTo(columnName, columnName + " + ?"));
        values.add(set.getValue());
    }

    /**
     * 增加自增`SET`值
     *
     * @param sets 列-值对列表
     */
    void addIncreaseSets(List<? extends ColumnValue> sets) {
        sets.forEach(this::addIncreaseSet);
    }

    /**
     * 增加自减`SET`值
     *
     * @param set 列-值对
     */
    void addDecreaseSet(ColumnValue set) {
        String columnName = getColumnName(set.getColumn());
        sets.add(CriteriaMaker.equalsTo(columnName, columnName + " - ?"));
        values.add(set.getValue());
    }

    /**
     * 增加自减`SET`值
     *
     * @param sets 列-值对列表
     */
    void addDecreaseSets(List<? extends ColumnValue> sets) {
        sets.forEach(this::addDecreaseSet);
    }

    /**
     * 增加列-值
     *
     * @param columns 列列表
     * @param values  值列表
     */
    void addColumnValue(List<Column> columns, List<Object> values) {
        addColumns(columns);
        addValues(values);
    }

    /**
     * 增加列-值
     *
     * @param columnValue 列-值对
     */
    void addColumnValue(ColumnValue columnValue) {
        addColumn(columnValue.getColumn());
        addValue(columnValue.getValue());
    }

    /**
     * 增加列-值
     *
     * @param columnValues 列-值对列表
     */
    void addColumnValues(List<? extends ColumnValue> columnValues) {
        for (ColumnValue columnValue : columnValues) {
            addColumn(columnValue.getColumn());
            addValue(columnValue.getValue());
        }
    }

    /**
     * 增加`WHERE`语句
     *
     * @param criteria 条件
     */
    void addWhere(Criteria criteria) {
        StringBuilder whereSQL = new StringBuilder();
        if (!where.isEmpty()) {
            whereSQL.append(criteria.getLogical().name()).append(" ");
        }
        PrepareStatement prepareStatement = criteria.getPrepareStatement(useAlias);
        if (null != prepareStatement) {
            whereSQL.append(prepareStatement.getPrepareSql());
            where.add(whereSQL.toString());
            if (CollectionUtils.isNotEmpty(prepareStatement.getValues())) {
                whereParams.addAll(prepareStatement.getValues());
            }
        }

    }

    /**
     * 增加`WHERE`语句
     *
     * @param criterion 条件列表
     */
    void addWheres(List<Criteria> criterion) {
        criterion.forEach(this::addWhere);
    }

    /**
     * 增加`GROUP BY`语句
     *
     * @param groupBy 列
     */
    void addGroupBy(IColumn groupBy) {
        this.groupBy.add(getColumnName(groupBy));
    }

    /**
     * 增加`GROUP BY`语句
     *
     * @param groupBys 列列表
     */
    void addGroupBys(List<? extends IColumn> groupBys) {
        groupBys.forEach(this::addGroupBy);
    }

    /**
     * 增加`ORDER BY`语句
     *
     * @param orderBy 排序
     */
    void addOrderBy(IOrderBy orderBy) {
        PrepareStatement prepareStatement = orderBy.getPrepareStatement(useAlias);
        if (null != prepareStatement) {
            this.orderBy.add(prepareStatement.getPrepareSql());
            if (CollectionUtils.isNotEmpty(prepareStatement.getValues())) {
                orderByParams.addAll(prepareStatement.getValues());
            }
        }
    }

    /**
     * 增加`ORDER BY`语句
     *
     * @param orderBys 排序列表
     */
    void addOrderBys(List<? extends IOrderBy> orderBys) {
        orderBys.forEach(this::addOrderBy);
    }

    /**
     * 增加`HAVING`语句
     *
     * @param having having内容
     */
    void addHaving(Having having) {
        StringBuilder havingSQL = new StringBuilder();
        if (!this.having.isEmpty()) {
            havingSQL.append(having.getLogical().name()).append(" ");
        }
        PrepareStatement prepareStatement = having.getPrepareStatement();
        if (null != prepareStatement) {
            havingSQL.append(prepareStatement.getPrepareSql());
            this.having.add(havingSQL.toString());
            if (CollectionUtils.isNotEmpty(prepareStatement.getValues())) {
                havingParams.addAll(prepareStatement.getValues());
            }
        }
    }

    /**
     * 增加`HAVING`语句
     *
     * @param havings having内容列表
     */
    void addHavings(List<Having> havings) {
        havings.forEach(this::addHaving);
    }

    /**
     * 获取表名
     *
     * @param table 表
     * @return 表名
     */
    private String getTableName(Table table) {
        return useAlias ? table.getNameWithAlias() : table.getName();
    }

    /**
     * 获取列名
     *
     * @param column 列
     * @return 列名
     */
    private String getColumnName(IColumn column) {
        return (useAlias ? column.getNameWithTableAlias() : column.getName())
                + (StringUtils.isBlank(column.getNameAs()) ? "" : (" AS " + column.getNameAs()));
    }


}
