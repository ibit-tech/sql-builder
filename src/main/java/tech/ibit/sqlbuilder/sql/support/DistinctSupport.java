package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.sql.field.BooleanField;

/**
 * Distinct Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface DistinctSupport<T> extends SqlSupport<T> {

    /**
     * 获取distinct
     *
     * @return distinct
     */
    BooleanField getDistinct();

    /**
     * distinct 操作
     *
     * @return SQL对象
     */
    default T distinct() {
        return distinct(true);
    }


    /**
     * distinct 操作
     *
     * @param distinct 是否distinct
     * @return SQL对象
     */
    default T distinct(boolean distinct) {
        getDistinct().setValue(distinct);
        return getSql();
    }

}
