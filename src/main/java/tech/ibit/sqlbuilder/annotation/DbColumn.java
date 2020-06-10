package tech.ibit.sqlbuilder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列注解
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DbColumn {

    /**
     * 名称
     *
     * @return 列名
     */
    String name();

    /**
     * 是否可为null
     *
     * @return 是否可以为null
     */
    boolean nullable() default false;
}
