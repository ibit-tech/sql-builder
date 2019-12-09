package tech.ibit.sqlbuilder;

import org.apache.commons.lang.StringUtils;
import tech.ibit.sqlbuilder.exception.SqlNotSupportedException;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.*;

/**
 * 抽象SQL语句构造对象
 *
 * @author IBIT TECH
 * @version 1.0
 */
class AbstractSqlStatement {

    /**
     * 操作类型
     */
    enum StatementType {
        /**
         * 删除
         */
        DELETE,

        /**
         * 删除表
         */
        DELETE_TABLE,

        /**
         * 插入
         */
        INSERT,

        /**
         * 查询
         */
        SELECT,

        /**
         * 更新
         */
        UPDATE,

        /**
         * 计数
         */
        COUNT,

        /**
         * 批量插入
         */
        BATCH_INSERT,
    }


    /**
     * JOIN类型
     */
    enum JoinOnType {

        /**
         * 全连
         */
        FULL,

        /**
         * 左连
         */
        LEFT,

        /**
         * 右连
         */
        RIGHT,

        /**
         * 内连
         */
        INNER,
        ;
    }

    /**
     * 操作类型
     */
    private StatementType statementType;

    /**
     * 是否distinct
     */
    private boolean distinct;

    /**
     * 列列表
     */
    List<String> columns = new ArrayList<>();

    /**
     * join语句列表
     */
    List<String> joinOn = new ArrayList<>();

    /**
     * join语句参数
     */
    List<KeyValuePair> joinOnParams = new ArrayList<>();

    /**
     * 查询语句列表
     */
    List<String> from = new ArrayList<>();

    /**
     * set语句列表
     */
    List<String> sets = new ArrayList<>();

    /**
     * set值列表
     */
    List<KeyValuePair> setValues = new ArrayList<>();

    /**
     * value语句列表
     */
    List<KeyValuePair> values = new ArrayList<>();

    /**
     * where语句列表
     */
    List<String> where = new ArrayList<>();

    /**
     * where参数值列表
     */
    List<KeyValuePair> whereParams = new ArrayList<>();

    /**
     * order by语句列表
     */
    List<String> orderBy = new ArrayList<>();

    /**
     * order by参数值列表（这个主要存在于自定义排序）
     */
    List<KeyValuePair> orderByParams = new ArrayList<>();

    /**
     * group by语句列表
     */
    List<String> groupBy = new ArrayList<>();

    /**
     * having语句列表
     */
    List<String> having = new ArrayList<>();

    /**
     * having参数值列表
     */
    List<KeyValuePair> havingParams = new ArrayList<>();

    /**
     * 删除表
     */
    List<String> deleteTables = new ArrayList<>();

    /**
     * 自定义语句（支持写入框架不支持的语句）
     */
    String lastClause;

    /**
     * 查询开始，默认为0
     */
    private int start = -1;

    /**
     * 查询条数，默认为1000
     */
    private int limit = -1;

    /**
     * 设置操作类型
     *
     * @param statementType 操作类型
     */
    void setStatementType(StatementType statementType) {
        this.statementType = statementType;
    }

    /**
     * 设置是否distinct
     *
     * @param distinct 是否distinct
     */
    void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * 设置返回条数
     *
     * @param start 开始位置
     * @param limit 返回条数
     */
    void setLimit(int start, int limit) {
        this.start = start;
        this.limit = limit;
    }

    /**
     * 设置返回条数
     *
     * @param limit 返回条数
     */
    void setLimit(int limit) {
        setLimit(0, limit);
    }

    /**
     * 获取SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    SqlParams getSqlParams() {
        if (null == statementType) {
            return null;
        }
        switch (statementType) {
            case SELECT:
                return getSelectSqlParams();
            case COUNT:
                return getCountSqlParams();
            case DELETE:
                return getDeleteSqlParams();
            case DELETE_TABLE:
                return getDeleteTableSqlParams();
            case UPDATE:
                return getUpdateSqlParams();
            case INSERT:
                return getInsertSqlParams();
            case BATCH_INSERT:
                return getBatchInsertSqlParams();
            default:
                return null;
        }
    }

    /**
     * 获取`COUNT` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    SqlParams countSqlParams() {
        if (null == statementType) {
            throw new SqlNotSupportedException("No statement type!");
        }
        switch (statementType) {
            case COUNT:
            case SELECT:
                return getCountSqlParams();
            default:
                throw new SqlNotSupportedException("Not supported statement type: " + statementType);
        }
    }

    /**
     * 获取`select` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    private SqlParams getSelectSqlParams() {
        StringBuilder sql = new StringBuilder();
        appendSql(sql, distinct ? "SELECT DISTINCT " : "SELECT ", columns, ", ", false);
        appendSql(sql, " FROM ", from, ", ", false);
        appendSql(sql, " ", joinOn, " ", true);
        appendSql(sql, " WHERE ", where, " ", true);
        appendSql(sql, " GROUP BY ", groupBy, ", ", true);
        if (!groupBy.isEmpty()) {
            appendSql(sql, " HAVING ", having, " ", true);
        }
        appendSql(sql, " ORDER BY ", orderBy, ", ", true);

        List<KeyValuePair> limitParams = new ArrayList<>(2);
        if (start >= 0) {
            sql.append(" LIMIT ?, ?");
            limitParams.add(new KeyValuePair("$start", start));
            limitParams.add(new KeyValuePair("$limit", limit));
        }

        return getSqlParams(sql, Arrays.asList(joinOnParams, whereParams, groupBy.isEmpty() ? Collections.emptyList() : havingParams, orderByParams, limitParams));
    }


    /**
     * 获取`COUNT` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    public SqlParams getCountSqlParams() {
        if (columns.isEmpty() && distinct) {
            throw new SqlNotSupportedException("Columns cannot be empty while at distinct statement!");
        }
        String columnStr = distinct ? StringUtils.join(columns, ", ") : "*";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(").append(distinct ? "DISTINCT " : "").append(columnStr).append(")");
        appendSql(sql, " FROM ", from, ", ", false);
        appendSql(sql, " ", joinOn, " ", true);
        appendSql(sql, " WHERE ", where, " ", true);
        appendSql(sql, " GROUP BY ", groupBy, ", ", true);
        if (!groupBy.isEmpty()) {
            appendSql(sql, " HAVING ", having, " ", true);
        }
        return getSqlParams(sql, Arrays.asList(joinOnParams, whereParams, groupBy.isEmpty() ? Collections.emptyList() : havingParams));
    }

    /**
     * 获取`DELETE` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    private SqlParams getDeleteSqlParams() {
        if (where.isEmpty()) {
            throw new RuntimeException("Where cannot be empty when do deleting!");
        }
        StringBuilder sql = new StringBuilder();
        appendSql(sql, "DELETE FROM ", from, ", ", false);
        appendSql(sql, " WHERE ", where, " ", true);
        return getSqlParams(sql, Collections.singletonList(whereParams));
    }


    /**
     * 获取`DELETE` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    @SuppressWarnings("unchecked")
    private SqlParams getDeleteTableSqlParams() {
        if (where.isEmpty()) {
            throw new RuntimeException("Where cannot be empty when do deleting!");
        }
        StringBuilder sql = new StringBuilder();
        appendSql(sql, "DELETE ", deleteTables, ", ", true);
        appendSql(sql, " FROM ", from, ", ", false);
        appendSql(sql, " ", joinOn, " ", true);
        appendSql(sql, " WHERE ", where, " ", true);
        return getSqlParams(sql, Arrays.asList(joinOnParams, whereParams));
    }

    /**
     * 获取`update` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    @SuppressWarnings("unchecked")
    private SqlParams getUpdateSqlParams() {

        if (where.isEmpty()) {
            throw new RuntimeException("Where cannot be empty when do updating!");
        }

        if (sets.isEmpty()) {
            throw new RuntimeException("Set cannot be empty when do updating!");
        }
        StringBuilder sql = new StringBuilder();
        appendSql(sql, "UPDATE ", from, ", ", false);
        appendSql(sql, " ", joinOn, " ", true);
        appendSql(sql, " SET ", sets, ", ", true);
        appendSql(sql, " WHERE ", where, " ", true);
        appendLastClause(sql);
        return getSqlParams(sql, Arrays.asList(joinOnParams, setValues, whereParams));
    }

    @SuppressWarnings("unchecked")
    private SqlParams getInsertSqlParams() {
        StringBuilder sql = new StringBuilder();
        appendSql(sql, "INSERT INTO ", from, ", ", false);
        appendSql(sql, "(", columns, ", ", ")", true);
        sql.append(" VALUES(").append(CriteriaMaker.getIn(values.size())).append(")");
        appendLastClause(sql);
        return getSqlParams(sql, Collections.singletonList(values));
    }

    /**
     * 获取批量`INSERT` SQL语句-参数对象
     * <p>
     * INSERT INTO TABLE_NAME(COLUMN1[, COLUMN2[,...]]) VAULES(?[, ? [, ...]]), (?[, ? [, ...]]);
     *
     * @return SQL语句-参数对象
     */
    @SuppressWarnings("unchecked")
    private SqlParams getBatchInsertSqlParams() {
        StringBuilder sql = new StringBuilder();
        appendSql(sql, "INSERT INTO ", from, ", ", false);
        appendSql(sql, "(", columns, ", ", ")", false);
        if (!columns.isEmpty()) {
            appendSql(sql, " VALUES", getValueIns(columns.size(), values.size()), ", ", true);
        }
        return getSqlParams(sql, Collections.singletonList(values));
    }

    /**
     * 构造`?`参数
     *
     * @param columnSize 列数量
     * @param totalSize  总参数数量
     * @return ? 列表
     */
    private List<String> getValueIns(int columnSize, int totalSize) {
        List<String> valueIns = new ArrayList<>();
        for (int i = 0; i < totalSize; i += columnSize) {
            valueIns.add("(" + CriteriaMaker.getIn(columnSize) + ")");
        }
        return valueIns;
    }

    /**
     * 扩展自定义语句
     *
     * @param sql SQL字符串构造器
     */
    private void appendLastClause(StringBuilder sql) {
        if (StringUtils.isNotBlank(lastClause)) {
            sql.append(" ").append(lastClause);
        }
    }

    /**
     * 获取SQL语句-参数对象
     *
     * @param sql        SQL字符串构造器
     * @param paramsList 参数列表数组
     * @return SQL语句-参数对象
     */
    private SqlParams getSqlParams(StringBuilder sql, List<List<KeyValuePair>> paramsList) {
        if (CollectionUtils.isEmpty(paramsList)) {
            return new SqlParams(sql.toString().trim(), Collections.emptyList());
        }
        List<KeyValuePair> allParams = new ArrayList<>();
        for (List<KeyValuePair> params : paramsList) {
            if (CollectionUtils.isNotEmpty(params)) {
                allParams.addAll(params);
            }
        }
        return new SqlParams(sql.toString().trim(), Collections.unmodifiableList(allParams));
    }

    /**
     * 扩展SQL字串
     *
     * @param sql          SQL字符串构造器
     * @param hook         开始位置
     * @param clauses      语句列表
     * @param separator    分割符
     * @param checkClauses 是否检查语句是否为空
     */
    private void appendSql(StringBuilder sql, String hook, Collection<String> clauses, String separator
            , boolean checkClauses) {
        appendSql(sql, hook, clauses, separator, "", checkClauses);
    }

    /**
     * 扩展SQL字串
     *
     * @param sql          SQL字符串构造器
     * @param hook         开始位置
     * @param clauses      语句列表
     * @param separator    分割符
     * @param close        结束字符
     * @param checkClauses 是否检查语句是否为空
     */
    private void appendSql(StringBuilder sql, String hook
            , Collection<String> clauses, String separator, String close
            , boolean checkClauses) {
        if (!checkClauses || CollectionUtils.isNotEmpty(clauses)) {
            sql.append(hook).append(StringUtils.join(clauses, separator)).append(close);
        }
    }

    /**
     * 增加值
     *
     * @param values 值列表
     */
    void addValues(List<Object> values) {
        if (null != values) {
            values.forEach(value -> this.values.add(new KeyValuePair(null, value)));
            adjustValues();
        }
    }

    /**
     * 增加值
     *
     * @param value 值
     */
    void addValue(Object value) {
        values.add(new KeyValuePair(null, value));
        adjustValues();
    }

    /**
     * 调整values的key
     */
    void adjustValues() {
        if (columns.isEmpty()) {
            values.forEach(value -> value.setKey(null));
            return;
        }
        int columnSize = columns.size();
        int valueSize = values.size();
        for (int i = 0; i < valueSize; i++) {
            values.get(i).setKey(columns.get(i % columnSize));
        }
    }


}
