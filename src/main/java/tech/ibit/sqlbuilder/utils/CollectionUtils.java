package tech.ibit.sqlbuilder.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@UtilityClass
public class CollectionUtils {


    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
