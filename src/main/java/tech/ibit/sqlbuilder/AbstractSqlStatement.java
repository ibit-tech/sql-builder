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

        /**
         * 批量插入2
         */
        BATCH_INSERT2
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
    List<Object> joinOnParams = new ArrayList<>();

    /**
     * 查询语句列表
     */
    List<String> from = new ArrayList<>();

    /**
     * set语句列表
     */
    List<String> sets = new ArrayList<>();

    /**
     * value语句列表
     */
    List<Object> values = new ArrayList<>();

    /**
     * where语句列表
     */
    List<String> where = new ArrayList<>();

    /**
     * where参数值列表
     */
    List<Object> whereParams = new ArrayList<>();

    /**
     * order by语句列表
     */
    List<String> orderBy = new ArrayList<>();

    /**
     * order by参数值列表（这个主要存在于自定义排序）
     */
    List<Object> orderByParams = new ArrayList<>();

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
    List<Object> havingParams = new ArrayList<>();

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
    SqlParams getSQLParams() {
        if (null == statementType) {
            return null;
        }
        switch (statementType) {
            case SELECT:
                return getSelectSQLParams();
            case COUNT:
                return getCountSQLParams();
            case DELETE:
                return getDeleteSQLParams();
            case DELETE_TABLE:
                return getDeleteTableSQLParams();
            case UPDATE:
                return getUpdateSQLParams();
            case INSERT:
                return getInsertSQLParams();
            case BATCH_INSERT:
                return getBatchInsertSQLParams();
            case BATCH_INSERT2:
                return getBatchInsert2SQLParams();
            default:
                return null;
        }
    }

    /**
     * 获取`COUNT` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    SqlParams countSQLParams() {
        if (null == statementType) {
            throw new SqlNotSupportedException("No statement type!");
        }
        switch (statementType) {
            case COUNT:
            case SELECT:
                return getCountSQLParams();
            default:
                throw new SqlNotSupportedException("Not supported statement type: " + statementType);
        }
    }

    /**
     * 获取`select` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    private SqlParams getSelectSQLParams() {
        StringBuilder sql = new StringBuilder();
        appendSQL(sql, distinct ? "SELECT DISTINCT " : "SELECT ", columns, ", ", false);
        appendSQL(sql, " FROM ", from, ", ", false);
        appendSQL(sql, " ", joinOn, " ", true);
        appendSQL(sql, " WHERE ", where, " ", true);
        appendSQL(sql, " GROUP BY ", groupBy, ", ", true);
        if (!groupBy.isEmpty()) {
            appendSQL(sql, " HAVING ", having, " ", true);
        }
        appendSQL(sql, " ORDER BY ", orderBy, ", ", true);

        List<Object> limitParams = new ArrayList<>(2);
        if (start >= 0) {
            sql.append(" LIMIT ?, ?");
            limitParams.add(start);
            limitParams.add(limit);
        }

        return getSQLParams(sql, Arrays.asList(joinOnParams, whereParams, groupBy.isEmpty() ? Collections.emptyList() : havingParams, orderByParams, limitParams));
    }


    /**
     * 获取`COUNT` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    public SqlParams getCountSQLParams() {
        if (columns.isEmpty() && distinct) {
            throw new SqlNotSupportedException("Columns cannot be empty while at distinct statement!");
        }
        String columnStr = distinct ? StringUtils.join(columns, ", ") : "*";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(").append(distinct ? "DISTINCT " : "").append(columnStr).append(")");
        appendSQL(sql, " FROM ", from, ", ", false);
        appendSQL(sql, " ", joinOn, " ", true);
        appendSQL(sql, " WHERE ", where, " ", true);
        appendSQL(sql, " GROUP BY ", groupBy, ", ", true);
        if (!groupBy.isEmpty()) {
            appendSQL(sql, " HAVING ", having, " ", true);
        }
        return getSQLParams(sql, Arrays.asList(joinOnParams, whereParams, groupBy.isEmpty() ? Collections.emptyList() : havingParams));
    }

    /**
     * 获取`DELETE` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    private SqlParams getDeleteSQLParams() {
        if (where.isEmpty()) {
            throw new RuntimeException("Where cannot be empty when do deleting!");
        }
        StringBuilder sql = new StringBuilder();
        appendSQL(sql, "DELETE FROM ", from, ", ", false);
        appendSQL(sql, " WHERE ", where, " ", true);
        return getSQLParams(sql, Collections.singletonList(whereParams));
    }


    /**
     * 获取`DELETE` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    @SuppressWarnings("unchecked")
    private SqlParams getDeleteTableSQLParams() {
        if (where.isEmpty()) {
            throw new RuntimeException("Where cannot be empty when do deleting!");
        }
        StringBuilder sql = new StringBuilder();
        appendSQL(sql, "DELETE ", deleteTables, ", ", true);
        appendSQL(sql, " FROM ", from, ", ", false);
        appendSQL(sql, " ", joinOn, " ", true);
        appendSQL(sql, " WHERE ", where, " ", true);
        return getSQLParams(sql, Arrays.asList(joinOnParams, whereParams));
    }

    /**
     * 获取`update` SQL语句-参数对象
     *
     * @return SQL语句-参数对象
     */
    @SuppressWarnings("unchecked")
    private SqlParams getUpdateSQLParams() {

        if (where.isEmpty()) {
            throw new RuntimeException("Where cannot be empty when do updating!");
        }

        if (sets.isEmpty()) {
            throw new RuntimeException("Set cannot be empty when do updating!");
        }
        StringBuilder sql = new StringBuilder();
        appendSQL(sql, "UPDATE ", from, ", ", false);
        appendSQL(sql, " ", joinOn, " ", true);
        appendSQL(sql, " SET ", sets, ", ", true);
        appendSQL(sql, " WHERE ", where, " ", true);
        appendLastClause(sql);
        return getSQLParams(sql, Arrays.asList(joinOnParams, values, whereParams));
    }

    @SuppressWarnings("unchecked")
    private SqlParams getInsertSQLParams() {
        StringBuilder sql = new StringBuilder();
        appendSQL(sql, "INSERT INTO ", from, ", ", false);
        appendSQL(sql, "(", columns, ", ", ")", true);
        sql.append(" VALUES(").append(CriteriaMaker.getIn(values.size())).append(")");
        appendLastClause(sql);
        return getSQLParams(sql, Collections.singletonList(values));
    }

    /**
     * 获取批量`INSERT` SQL语句-参数对象
     * <p>
     * INSERT INTO TABLE_NAME(COLUMN1[, COLUMN2[,...]]) VAULES(?[, ? [, ...]]), (?[, ? [, ...]]);
     *
     * @return SQL语句-参数对象
     */
    @SuppressWarnings("unchecked")
    private SqlParams getBatchInsertSQLParams() {
        StringBuilder sql = new StringBuilder();
        appendSQL(sql, "INSERT INTO ", from, ", ", false);
        appendSQL(sql, "(", columns, ", ", ")", false);
        if (!columns.isEmpty()) {
            appendSQL(sql, " VALUES", getValueIns(columns.size(), values.size()), ", ", true);
        }
        return getSQLParams(sql, Collections.singletonList(values));
    }

    /**
     * 获取批量`INSERT` SQL语句-参数对象
     * <p>
     * 多次 INSERT INTO TABLE_NAME(COLUMN1[, COLUMN2[,...]]) VAULES(?[, ? [, ...]]);
     *
     * @return SQL语句-参数对象
     */
    @SuppressWarnings("unchecked")
    private SqlParams getBatchInsert2SQLParams() {
        StringBuilder sql = new StringBuilder();
        appendSQL(sql, "INSERT INTO ", from, ", ", false);
        appendSQL(sql, "(", columns, ", ", ")", false);
        if (!columns.isEmpty()) {
            sql.append(" VALUES(").append(CriteriaMaker.getIn(columns.size())).append(")");
        }
        List<Object> params = new ArrayList<>();
        int columnSize = columns.size();
        for (int i = 0; i < values.size(); i += columnSize) {
            params.add(values.subList(i, i + columnSize));
        }
        return getSQLParams(sql, Collections.singletonList(params));
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
    private SqlParams getSQLParams(StringBuilder sql, List<List<Object>> paramsList) {
        if (CollectionUtils.isEmpty(paramsList)) {
            return new SqlParams(sql.toString().trim(), Collections.emptyList());
        }
        List<Object> allParams = new ArrayList<>();
        for (List<Object> params : paramsList) {
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
    private void appendSQL(StringBuilder sql, String hook, Collection<String> clauses, String separator
            , boolean checkClauses) {
        appendSQL(sql, hook, clauses, separator, "", checkClauses);
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
    private void appendSQL(StringBuilder sql, String hook
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
            this.values.addAll(values);
        }
    }

    /**
     * 增加值
     *
     * @param value 值
     */
    void addValue(Object value) {
        values.add(value);
    }


}
