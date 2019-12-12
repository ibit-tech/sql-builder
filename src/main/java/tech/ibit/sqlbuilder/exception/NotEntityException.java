package tech.ibit.sqlbuilder.exception;

/**
 * 非实体类异常
 *
 * @author IBIT-TECH
 * mailto: ibit_tech@aliyun.com
 */
public class NotEntityException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param clazzName 类名
     */
    public NotEntityException(String clazzName) {
        super("Class(" + clazzName + ") is not entity!");
    }
}
