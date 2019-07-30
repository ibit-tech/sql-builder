package tech.ibit.sqlbuilder.exception;

/**
 * 主键值为null异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class IdNullPointerException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param table 表
     * @param id    主键
     */
    public IdNullPointerException(String table, String id) {
        super("Table(" + table + ")'s id(" + id + ") is null!");
    }
}
