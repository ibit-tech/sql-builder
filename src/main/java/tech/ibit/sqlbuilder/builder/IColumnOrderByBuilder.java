package tech.ibit.sqlbuilder.builder;

import tech.ibit.sqlbuilder.CustomOrderBy;
import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.IOrderBy;
import tech.ibit.sqlbuilder.OrderBy;

import java.util.List;

/**
 * 列OrderBy构造
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface IColumnOrderByBuilder {

    /**
     * 获取列
     *
     * @return 列
     */
    IColumn getColumn();

    /**
     * 构造OrderBy
     *
     * @param desc 是否倒序
     * @return OrderBy
     */
    default IOrderBy orderBy(boolean desc) {
        return new OrderBy(getColumn(), desc);
    }

    /**
     * 构造OrderBy
     *
     * @return OrderBy
     */
    default IOrderBy orderBy() {
        return orderBy(false);
    }

    /**
     * 自定义排序
     *
     * @param subOrders 排序序列
     * @param desc      是否倒叙
     * @return OrderBy
     */
    default IOrderBy customerOrderBy(List<?> subOrders, boolean desc) {
        return new CustomOrderBy(getColumn(), subOrders, desc);
    }


    /**
     * 自定义排序
     *
     * @param subOrders 排序序列
     * @return OrderBy
     */
    default IOrderBy customerOrderBy(List<?> subOrders) {
        return customerOrderBy(subOrders, false);
    }


}
