package tech.ibit.sqlbuilder;


import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义排序
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class CustomOrderBy extends OrderBy {

    /**
     * 排序序列
     */
    private List<?> subOrders;

    /**
     * 构造函数
     *
     * @param column    排序列
     * @param subOrders 排序序列
     */
    public CustomOrderBy(IColumn column, List<?> subOrders) {
        this(column, subOrders, false);
    }

    /**
     * 构造函数
     *
     * @param column    排序列
     * @param subOrders 排序序列
     * @param desc      是否倒叙
     */
    public CustomOrderBy(IColumn column, List<?> subOrders, boolean desc) {
        super(column, desc);
        this.subOrders = subOrders;
    }

    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {
        if (CollectionUtils.isEmpty(subOrders)) {
            return null;
        }
        String columnName = getColumn().getCompareColumnName(useAlias);
        String prepareSql = "FIELD(" + columnName + ", " + CriteriaMaker.getIn(subOrders.size()) + ")" + (isDesc() ? " DESC" : "");
        List<ColumnValue> subOrderKeyValuePairs = subOrders.stream().map(o -> new ColumnValue(getColumn(), o)).collect(Collectors.toList());
        return new PrepareStatement(prepareSql, subOrderKeyValuePairs);
    }

    /**
     * Gets the value of subOrders
     *
     * @return the value of subOrders
     */
    public List<?> getSubOrders() {
        return subOrders;
    }

    /**
     * Sets the subOrders
     * <p>You can use getSubOrders() to get the value of subOrders</p>
     *
     * @param subOrders subOrders
     */
    public void setSubOrders(List<?> subOrders) {
        this.subOrders = subOrders;
    }
}
