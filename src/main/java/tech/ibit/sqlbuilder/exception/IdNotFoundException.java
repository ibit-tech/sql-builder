package tech.ibit.sqlbuilder.exception;

/**
 * 主键不存在异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class IdNotFoundException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param table 表
     */
    public IdNotFoundException(String table) {
        super("Table(" + table + ") id not found");
    }
}
