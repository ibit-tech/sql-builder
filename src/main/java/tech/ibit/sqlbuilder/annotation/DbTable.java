package tech.ibit.sqlbuilder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表注解
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface DbTable {

    /**
     * 表名
     *
     * @return 表名
     */
    String name();

    /**
     * 表别名
     *
     * @return 表别名
     */
    String alias();
}
