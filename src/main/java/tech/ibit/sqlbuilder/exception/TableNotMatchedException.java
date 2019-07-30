package tech.ibit.sqlbuilder.exception;

/**
 * 表不匹配异常
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class TableNotMatchedException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param exceptTable 预期表名
     * @param actualTable 实际表名
     */
    public TableNotMatchedException(String exceptTable, String actualTable) {
        super("Table not match, except[" + exceptTable + "], actual[" + actualTable + "]");
    }
}
