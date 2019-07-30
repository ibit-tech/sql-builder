package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 表对象定义
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Table {

    /**
     * 表名称
     */
    private String name;

    /**
     * 表别名（as后面的名称）
     */
    private String alias;


    /**
     * 获取带别名的名称
     *
     * @return 带别名名称
     */
    public String getNameWithAlias() {
        return name + " " + alias;
    }


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

        Table table = (Table) o;

        return name.equals(table.name) && alias.equals(table.alias);
    }

    /**
     * 重写hashCode的计算
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + alias.hashCode();
        return result;
    }
}
