package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.SetItem;

import java.util.List;

/**
 * SetSupport
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface OnDuplicateKeyUpdateSupport<T> {

    /**
     * 增加设置内容
     *
     * @param item 设置项
     * @return SQL对象
     */
    T onDuplicateKeyUpdate(SetItem item);

    /**
     * 批量增加设置内容
     *
     * @param items 设置项
     * @return SQL对象
     */
    T onDuplicateKeyUpdate(List<SetItem> items);

}
