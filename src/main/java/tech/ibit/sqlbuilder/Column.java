package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.ibit.sqlbuilder.builder.IColumnAggregateBuilder;
import tech.ibit.sqlbuilder.builder.IColumnCriteriaItemBuilder;
import tech.ibit.sqlbuilder.builder.IColumnOrderByBuilder;
import tech.ibit.sqlbuilder.builder.IColumnSetItemBuilder;

/**
 * 列定义
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class Column implements IColumn,
        IColumnCriteriaItemBuilder, IColumnAggregateBuilder, IColumnSetItemBuilder, IColumnOrderByBuilder {

    /**
     * 表
     */
    private Table table;

    /**
     * 列名
     */
    private String name;

    /**
     * 重写equals方法
     *
     * @param o 比较对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Column column = (Column) o;

        if (!table.equals(column.table)) {
            return false;
        }
        return name.equals(column.name);
    }

    /**
     * 重写hashCode的计算
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    /**
     * 获取带表别名的列名
     *
     * @return 带表别名的列名
     */
    @Override
    public String getNameWithTableAlias() {
        return table.getAlias() + "." + name;
    }

    /**
     * 获取字段别名（as后面的名称）
     *
     * @return 别名
     */
    @Override
    public String getNameAs() {
        // 这里不定义别名
        return null;
    }

    @Override
    public IColumn getColumn() {
        return this;
    }
}
