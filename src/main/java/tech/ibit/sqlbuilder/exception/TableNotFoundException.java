package tech.ibit.sqlbuilder.exception;

/**
 * 表不存在异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class TableNotFoundException extends RuntimeException {

    /**
     * 构造函数
     */
    public TableNotFoundException() {
        super("Table not found");
    }
}
