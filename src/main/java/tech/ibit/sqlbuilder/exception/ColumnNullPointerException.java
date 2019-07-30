package tech.ibit.sqlbuilder.exception;

/**
 * 列为null异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class ColumnNullPointerException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param table  表名
     * @param column 列名
     */
    public ColumnNullPointerException(String table, String column) {
        super("Table(" + table + ")'s column(" + column + ") is null!");
    }
}
