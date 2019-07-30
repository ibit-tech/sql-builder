package tech.ibit.sqlbuilder;

import lombok.Data;

/**
 * Having字段信息
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
public class HavingItem {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 操作符
     */
    private Operator operator;

    /**
     * 值
     */
    private Object value;

    /**
     * 构造函数
     *
     * @param columnName 列名
     * @param operator   操作符
     * @param value      值
     */
    HavingItem(String columnName, Operator operator, Object value) {
        this.columnName = columnName;
        this.operator = operator;
        this.value = value;
    }

    /**
     * 获取预查询SQL
     *
     * @return 预查询SQL
     */
    public String getPrepareSql() {
        return columnName + " " + operator.getValue() + " ?";
    }
}
