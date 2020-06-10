package tech.ibit.sqlbuilder.converter;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Method工具类
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@UtilityClass
class MethodUtils {

    /**
     * 获取指定类某个字段的Setter方法
     *
     * @param clazz 指定类
     * @param field 字段
     * @return 字段的Setter方法
     */
    @SuppressWarnings("unchecked")
    Method getSetterMethod(Class clazz, Field field) {
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            Method method = clazz.getDeclaredMethod(methodName, field.getType());
            if (method.getModifiers() != Modifier.PUBLIC) {
                return null;
            }
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定类某个字段的Getter方法
     *
     * @param clazz 指定类
     * @param field 字段
     * @return 字段的Setter方法
     */
    @SuppressWarnings("unchecked")
    Method getGetterMethod(Class clazz, Field field) {
        String prefix = (field.getType() == boolean.class || field.getType() == Boolean.class)
                ? "is" : "get";
        String fieldName = field.getName();
        String methodName = prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            if (method.getModifiers() != Modifier.PUBLIC) {
                return null;
            }
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
