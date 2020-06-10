package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 自增长id Setter相关方法
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class AutoIncrementIdSetterMethod {

    /**
     * 设置类型
     */
    private Class<?> type;

    /**
     * 方法
     */
    private Method method;
}