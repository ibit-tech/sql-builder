package tech.ibit.sqlbuilder.exception;

/**
 * 主键被更新异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class IdInvalidUpdateException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param table 表
     * @param id    主键
     */
    public IdInvalidUpdateException(String table, String id) {
        super("Table(" + table + ")'s id(" + id + ") cannot be updated");
    }
}
