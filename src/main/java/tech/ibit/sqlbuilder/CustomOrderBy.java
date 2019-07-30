package tech.ibit.sqlbuilder;

import lombok.Data;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 自定义排序
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
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
    public CustomOrderBy(Column column, List<?> subOrders) {
        this(column, subOrders, false);
    }

    /**
     * 构造函数
     *
     * @param column    排序列
     * @param subOrders 排序序列
     * @param desc      是否倒叙
     */
    public CustomOrderBy(Column column, List<?> subOrders, boolean desc) {
        super(column, desc);
        this.subOrders = subOrders;
    }

    /**
     * 构造预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return BETWEEN值预查询SQL对象
     */
    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {
        if (CollectionUtils.isEmpty(subOrders)) {
            return null;
        }
        String columnName = useAlias ? getColumn().getNameWithTableAlias() : getColumn().getName();
        String prepareSql = "FIELD(" + columnName + ", " + CriteriaMaker.getIn(subOrders.size()) + ")" + (isDesc() ? " DESC" : "");
        return new PrepareStatement(prepareSql, Collections.unmodifiableList(subOrders));
    }
}
