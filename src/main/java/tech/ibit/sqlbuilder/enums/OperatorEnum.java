package tech.ibit.sqlbuilder.enums;

/**
 * 操作符号
 *
 * @author iBit程序猿
 * @version 1.0
 */
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

    /**
     * 第一个值
     */
    private final String value;

    /**
     * 第二个值
     */
    private final String secondValue;

    OperatorEnum(String value) {
        this(value, null);
    }

    OperatorEnum(String value, String secondValue) {
        this.value = value;
        this.secondValue = secondValue;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the value of secondValue
     *
     * @return the value of secondValue
     */
    public String getSecondValue() {
        return secondValue;
    }
}
