package tech.ibit.sqlbuilder.builder;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.SetItem;

/**
 * 列构造setItem
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface IColumnSetItemBuilder {


    /**
     * 获取列
     *
     * @return 列
     */
    IColumn getColumn();

    /**
     * 设置具体值
     *
     * @param value 值
     * @return 设置item
     */
    default SetItem set(Object value) {
        return SetItem.set((Column) getColumn(), value);
    }

    /**
     * 设置自增长
     *
     * @param value 值
     * @return 自增长item
     */
    default SetItem increaseSet(Number value) {
        return SetItem.increaseSet((Column) getColumn(), value);
    }

    /**
     * 设置自减
     *
     * @param value 值OL
     * @return 自减item
     */
    default SetItem decreaseSet(Number value) {
        return SetItem.decreaseSet((Column) getColumn(), value);
    }
}
