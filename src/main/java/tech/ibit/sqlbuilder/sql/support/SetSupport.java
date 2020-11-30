package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.SetItem;

import java.util.List;

/**
 * SetSupport
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface SetSupport<T> {

    /**
     * 增加设置内容
     *
     * @param item 设置项
     * @return SQL对象
     */
    T set(SetItem item);

    /**
     * 批量增加设置内容
     *
     * @param items 设置项
     * @return SQL对象
     */
    T set(List<SetItem> items);

    /**
     * 设置具体值
     *
     * @param column 列
     * @param value  值
     * @return 设置item
     */
    T set(Column column, Object value);

    /**
     * 设置自增长
     *
     * @param column 列
     * @param value  值
     * @return 自增长item
     */
    T increaseSet(Column column, Number value);

    /**
     * 设置自减
     *
     * @param column 列
     * @param value  值
     * @return 自减item
     */
    T decreaseSet(Column column, Number value);

}
