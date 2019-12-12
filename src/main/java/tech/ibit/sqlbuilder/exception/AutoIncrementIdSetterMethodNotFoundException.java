package tech.ibit.sqlbuilder.exception;

/**
 * 自增长id设置方法不存在
 *
 * @author IBIT-TECH
 * mailto: ibit_tech@aliyun.com
 */
public class AutoIncrementIdSetterMethodNotFoundException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param clazzName 类名称
     */
    public AutoIncrementIdSetterMethodNotFoundException(String clazzName) {
        super("Class(" + clazzName + ") auto increment setter method not found!");
    }
}
