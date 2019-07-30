package tech.ibit.sqlbuilder.exception;

/**
 * 不支持多主键异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class MultiIdNotSupportedException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param table 表
     */
    public MultiIdNotSupportedException(String table) {
        super("Table(" + table + ") has more than one id");
    }

}
