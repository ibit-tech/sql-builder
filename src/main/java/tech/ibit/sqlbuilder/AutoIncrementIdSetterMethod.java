package tech.ibit.sqlbuilder;

import java.lang.reflect.Method;

/**
 * 自增长id Setter相关方法
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class AutoIncrementIdSetterMethod {

    /**
     * 设置类型
     */
    private Class<?> type;

    /**
     * 方法
     */
    private Method method;

    /**
     * 构造函数
     *
     * @param type   设置类型
     * @param method 方法
     */
    public AutoIncrementIdSetterMethod(Class<?> type, Method method) {
        this.type = type;
        this.method = method;
    }

    /**
     * Gets the value of type
     *
     * @return the value of type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Sets the type
     * <p>You can use getType() to get the value of type</p>
     *
     * @param type type
     */
    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
     * Gets the value of method
     *
     * @return the value of method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Sets the method
     * <p>You can use getMethod() to get the value of method</p>
     *
     * @param method method
     */
    public void setMethod(Method method) {
        this.method = method;
    }
}