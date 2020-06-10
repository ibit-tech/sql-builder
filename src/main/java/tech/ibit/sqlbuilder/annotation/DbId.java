package tech.ibit.sqlbuilder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键注解
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DbId {

    /**
     * 主键列名
     *
     * @return 主键列名
     */
    String name();

    /**
     * 是否为自增长
     *
     * @return 是否为自增长
     */
    boolean autoIncrease() default false;
}
