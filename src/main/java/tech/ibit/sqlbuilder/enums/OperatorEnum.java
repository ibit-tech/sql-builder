package tech.ibit.sqlbuilder.enums;

import lombok.Getter;

/**
 * 操作符号
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Getter
public enum OperatorEnum {

    /**
     * 为null
     */
    IS_NULL("IS NULL"),

    /**
     * 不为null
     */
    IS_NOT_NULL("IS NOT NULL"),

    /**
     * 为空字符串
     */
    IS_EMPTY("= ''"),

    /**
     * 不为空串
     */
    IS_NOT_EMPTY("<> ''"),

    /**
     * 相等
     */
    EQ("="),

    /**
     * 不相等
     */
    NEQ("<>"),

    /**
     * 大于
     */
    GT(">"),

    /**
     * 大于等于
     */
    EGT(">="),

    /**
     * 小于
     */
    LT("<"),

    /**
     * 小于等于
     */
    ELT("<="),

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

    OperatorEnum(String value) {
        this(value, null);
    }

    OperatorEnum(String value, String secondValue) {
        this.value = value;
        this.secondValue = secondValue;
    }
}
