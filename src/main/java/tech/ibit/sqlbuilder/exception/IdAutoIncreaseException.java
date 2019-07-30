package tech.ibit.sqlbuilder.exception;

/**
 * 自增长主键被插入异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class IdAutoIncreaseException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param table 表名
     * @param id    主键
     */
    public IdAutoIncreaseException(String table, String id) {
        super("Table(" + table + ")'s id(" + id + ") cannot be inserted!");
    }
}
