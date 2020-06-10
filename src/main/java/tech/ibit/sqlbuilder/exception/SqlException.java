package tech.ibit.sqlbuilder.exception;

/**
 * SQL语法不支持异常
 *
 * @author IBIT程序猿
 * @version 1.0
 */
public class SqlException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常说明
     */
    public SqlException(String message) {
        super(message);
    }


    /**
     * 列值不存在
     *
     * @return 错误信息
     */
    public static SqlException columnValueNotFound() {
        return new SqlException("Column value not found!");
    }

    /**
     * 主键不能被更新
     *
     * @param table 表
     * @param id    主键
     * @return 错误信息
     */
    public static SqlException idInvalidUpdate(String table, String id) {
        return new SqlException("Table(" + table + ")'s id(" + id + ") cannot be updated");
    }

    /**
     * id不存在
     *
     * @param table 表
     * @return 错误信息
     */
    public static SqlException idNotFound(String table) {
        return new SqlException("Table(" + table + ") id not found");
    }

    /**
     * id为空
     *
     * @param table 表
     * @param id    主键
     * @return 错误信息
     */
    public static SqlException idNullPointer(String table, String id) {
        return new SqlException("Table(" + table + ")'s id(" + id + ") is null!");
    }

    /**
     * i
     * id不存在
     *
     * @return 错误消息
     */
    public static SqlException idValueNotFound() {
        return new SqlException("Id value not found");
    }


    /**
     * 列为空
     *
     * @param table  表名
     * @param column 列名
     * @return 错误信息
     */
    public static SqlException columnNullPointer(String table, String column) {
        return new SqlException("Table(" + table + ")'s column(" + column + ") is null!");
    }

    /**
     * 自增长字段不能插入
     *
     * @param table 表名
     * @param id    列名
     * @return 错误信息
     */
    public static SqlException idAutoIncrease(String table, String id) {
        return new SqlException("Table(" + table + ")'s id(" + id + ") cannot be inserted!");
    }

    /**
     * 不支持多个id
     *
     * @param table 表
     * @return 错误信息
     */
    public static SqlException multiIdNotSupported(String table) {
        return new SqlException("Table(" + table + ") has more than one id");
    }


    /**
     * 表名不匹配
     *
     * @param exceptTable 预期表名
     * @param actualTable 实际表名
     * @return 错误信息
     */
    public static SqlException tableNotMatched(String exceptTable, String actualTable) {
        return new SqlException("Table not match, except[" + exceptTable + "], actual[" + actualTable + "]");
    }
}
