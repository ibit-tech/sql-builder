package tech.ibit.sqlbuilder;

import org.apache.commons.lang.StringUtils;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * 字串SQL语句构造对象
 *
 * @author IBIT TECH
 * @version 1.0
 */
class StringSqlStatement extends AbstractSqlStatement {


    /**
     * 增加`JOIN ON`语句
     *
     * @param joinOn `JOIN ON`语句
     */
    void addJoinOn(String joinOn) {
        if (StringUtils.isNotBlank(joinOn)) {
            this.joinOn.add(joinOn);
        }
    }

    /**
     * 增加`JOIN ON`语句
     *
     * @param joinOns `JOIN ON`语句列表
     */
    void addJoinOns(List<String> joinOns) {
        for (String joinOn : joinOns) {
            addJoinOn(joinOn);
        }
    }


    /**
     * 增加`JOIN ON`语句
     *
     * @param joinOn       `JOIN ON`语句
     * @param joinOnParams ON条件
     */
    void addJoinOn(String joinOn, List<Object> joinOnParams) {
        if (StringUtils.isNotBlank(joinOn)) {
            this.joinOn.add(joinOn);
        }
        if (CollectionUtils.isNotEmpty(joinOnParams)) {
            this.joinOnParams.addAll(joinOnParams);
        }
    }

    /**
     * 增加`JOIN ON`语句
     *
     * @param joinOns      `JOIN ON`语句列表
     * @param joinOnParams ON条件
     */
    void addJoinOns(List<String> joinOns, List<Object> joinOnParams) {
        for (String joinOn : joinOns) {
            addJoinOn(joinOn);
        }
        if (CollectionUtils.isNotEmpty(joinOnParams)) {
            this.joinOnParams.addAll(joinOnParams);
        }
    }

    /**
     * 增加列
     *
     * @param column 列
     */
    void addColumn(String column) {
        this.columns.add(column);
    }

    /**
     * 增加列
     *
     * @param columns 列列表
     */
    void addColumns(List<String> columns) {
        if (null != columns) {
            this.columns.addAll(columns);
        }
    }

    /**
     * 增加表
     *
     * @param table 表
     */
    void addFrom(String table) {
        if (StringUtils.isNotBlank(table)) {
            from.add(table);
        }
    }

    /**
     * 增加表
     *
     * @param tables 表列表
     */
    void addFroms(List<String> tables) {
        for (String table : tables) {
            addFrom(table);
        }
    }

    /**
     * 增加`SET`
     *
     * @param set 列设置
     */
    void addSet(String set) {
        if (StringUtils.isNotBlank(set)) {
            this.sets.add(set);
        }
    }

    /**
     * 增加`SET`
     *
     * @param sets 列设置
     */
    void addSets(List<String> sets) {
        for (String set : sets) {
            if (StringUtils.isNotBlank(set)) {
                this.sets.add(set);
            }
        }
    }

    /**
     * 增加`SET`值
     *
     * @param sets   列设置率
     * @param values 值列表
     */
    void addSets(List<String> sets, List<Object> values) {
        if (null != sets) {
            addSets(sets);
        }
        if (null != values) {
            addValues(values);
        }
    }

    /**
     * 增加列-值
     *
     * @param columns 列列表
     * @param values  值列表
     */
    void addColumnValues(List<String> columns, List<Object> values) {
        addColumns(columns);
        addValues(values);
    }

    /**
     * 增加列-值
     *
     * @param columnValue 列-值对
     */
    void addColumnValue(KeyValuePair columnValue) {
        addColumn(columnValue.getKey());
        addValue(columnValue.getValue());
    }

    /**
     * 增加列-值
     *
     * @param columnValues 列-值对列表
     */
    void addColumnValues(List<KeyValuePair> columnValues) {
        for (KeyValuePair columnValue : columnValues) {
            addColumnValue(columnValue);
        }
    }

    /**
     * 增加`WHERE`语句
     *
     * @param criteria 条件
     */
    void addWhere(String criteria) {
        if (StringUtils.isNotBlank(criteria)) {
            where.add(criteria);
        }
    }

    /**
     * 增加`WHERE`语句
     *
     * @param criterion 条件列表
     */
    void addWheres(List<String> criterion) {
        for (String criteria : criterion) {
            if (StringUtils.isNotBlank(criteria)) {
                where.add(criteria);
            }
        }
    }

    /**
     * 增加`WHERE`语句
     *
     * @param criterion   条件列表
     * @param whereParams WHERE参数
     */
    void addWheres(List<String> criterion, List<Object> whereParams) {
        if (null != criterion) {
            addWheres(criterion);
        }
        if (null != whereParams) {
            this.whereParams.addAll(whereParams);
        }
    }

    /**
     * 增加`GROUP BY`语句
     *
     * @param groupBy 列
     */
    void addGroupBy(String groupBy) {
        this.groupBy.add(groupBy);
    }

    /**
     * 增加`GROUP BY`语句
     *
     * @param groupBys 列列表
     */
    void addGroupBys(List<String> groupBys) {
        if (null != groupBys) {
            groupBy.addAll(groupBys);
        }
    }


    /**
     * 增加`ORDER BY`语句
     *
     * @param orderBy 排序
     */
    void addOrderBy(String orderBy) {
        if (StringUtils.isNotBlank(orderBy)) {
            this.orderBy.add(orderBy);
        }
    }

    /**
     * 增加`ORDER BY`语句
     *
     * @param orderBys 排序列表
     */
    void addOrderBys(List<String> orderBys) {
        for (String orderBy : orderBys) {
            addOrderBy(orderBy);
        }
    }

    /**
     * 增加"HAVING"语句
     *
     * @param criteria HAVING条件
     */
    void addHaving(String criteria) {
        if (StringUtils.isNotBlank(criteria)) {
            having.add(criteria);
        }
    }

    /**
     * 增加"HAVING"语句
     *
     * @param criterion HAVING条件列表
     */
    void addHaving(List<String> criterion) {
        for (String criteria : criterion) {
            addHaving(criteria);
        }
    }

    /**
     * 增加"HAVING"语句
     *
     * @param criterion    HAVING条件列表
     * @param havingParams HAVING参数列表
     */
    void addHaving(List<String> criterion, List<Object> havingParams) {
        if (null != criterion) {
            addHaving(criterion);
        }
        if (null != havingParams) {
            this.havingParams.addAll(havingParams);
        }
    }

    /**
     * 增加自定义语句
     *
     * @param lastClause 自定义语句
     */
    void setLastClause(String lastClause) {
        this.lastClause = lastClause;
    }
}
