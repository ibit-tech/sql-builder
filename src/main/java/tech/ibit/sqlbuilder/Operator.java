package tech.ibit.sqlbuilder;

import lombok.Getter;

/**
 * 操作符号
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Getter
public enum Operator {

    /**
     * 为null
     */
    IS_NULL("IS NULL"),

    /**
     * 不为null
     */
    IS_NOT_NULL("IS NOT NULL"),

    /**
     * 相等
     */
    EQUALS("="),

    /**
     * 不相等
     */
    NOT_EQUALS("<>"),

    /**
     * 大于
     */
    GREATER(">"),

    /**
     * 大于等于
     */
    GREATER_OR_EQUALS(">="),

    /**
     * 小于
     */
    LESS("<"),

    /**
     * 小于等于
     */
    LESS_OR_EQUALS("<="),

    /**
     * IN
     */
    IN("IN"),

    /**
     * NOT IN
     */
    NOT_IN("NOT IN"),

    /**
     * BETWEEN AND
     */
    BETWEEN("BETWEEN", "AND"),

    /**
     * NOT BETWEEN AND
     */
    NOT_BETWEEN("NOT BETWEEN", "AND"),

    /**
     * LIKE
     */
    LIKE("LIKE"),

    /**
     * NOT LIKE
     */
    NOT_LIKE("NOT LIKE"),
    ;

    private String value;
    private String secondValue;

    Operator(String value) {
        this(value, null);
    }

    Operator(String value, String secondValue) {
        this.value = value;
        this.secondValue = secondValue;
    }
}
