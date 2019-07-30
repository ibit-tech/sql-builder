package tech.ibit.sqlbuilder.exception;

/**
 * 列值不存在异常（插入没有设置任何值）
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class ColumnValueNotFoundException extends RuntimeException {

    /**
     * 构造函数
     */
    public ColumnValueNotFoundException() {
        super("Column value not found!");
    }
}
