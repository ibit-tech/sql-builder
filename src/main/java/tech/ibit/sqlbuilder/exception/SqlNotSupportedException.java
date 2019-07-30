package tech.ibit.sqlbuilder.exception;

/**
 * SQL语法不支持异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class SqlNotSupportedException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常说明
     */
    public SqlNotSupportedException(String message) {
        super(message);
    }
}
