package tech.ibit.sqlbuilder.exception;

/**
 * 主键未找到异常（用主键搜索，没传主键）
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class IdValueNotFoundException extends RuntimeException {

    /**
     * 构造函数
     */
    public IdValueNotFoundException() {
        super("Id value not found");
    }
}
