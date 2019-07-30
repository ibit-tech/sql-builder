package tech.ibit.sqlbuilder.utils;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class CollectionUtils {

    private CollectionUtils() {

    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
