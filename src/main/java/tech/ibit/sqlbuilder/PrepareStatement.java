package tech.ibit.sqlbuilder;


import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 预查询SQL对象
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class PrepareStatement {

    /**
     * 预查询SQL
     */
    private String prepareSql;

    /**
     * 插入值列表
     */
    private List<ColumnValue> values;

    /**
     * 构造函数
     *
     * @param prepareSql 预查询SQL
     * @param values     插入值列表
     */
    public PrepareStatement(String prepareSql, List<ColumnValue> values) {
        this.prepareSql = prepareSql;
        this.values = values;
    }

    /**
     * 获取空的PrepareStatement
     *
     * @return PrepareStatement
     */
    public static PrepareStatement empty() {
        return new PrepareStatement("", Collections.emptyList());
    }

    /**
     * 获取参数列表
     *
     * @return 参数列表
     */
    public List<Object> getParams() {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return values.stream().map(ColumnValue::getValue).collect(Collectors.toList());
    }

    /**
     * Gets the value of prepareSql
     *
     * @return the value of prepareSql
     */
    public String getPrepareSql() {
        return prepareSql;
    }

    /**
     * Sets the prepareSql
     * <p>You can use getPrepareSql() to get the value of prepareSql</p>
     *
     * @param prepareSql prepareSql
     */
    public void setPrepareSql(String prepareSql) {
        this.prepareSql = prepareSql;
    }

    /**
     * Gets the value of values
     *
     * @return the value of values
     */
    public List<ColumnValue> getValues() {
        return values;
    }

    /**
     * Sets the values
     * <p>You can use getValues() to get the value of values</p>
     *
     * @param values values
     */
    public void setValues(List<ColumnValue> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PrepareStatement.class.getSimpleName() + "[", "]")
                .add("prepareSql='" + prepareSql + "'")
                .add("values=" + values)
                .toString();
    }
}
