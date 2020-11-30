package tech.ibit.sqlbuilder;

import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 表对象定义
 *
 * @author iBit程序猿
 * @version 1.0
 */
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
     * 构造函数
     *
     * @param name  表名称
     * @param alias 表别名
     */
    public Table(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    /**
     * 获取带别名的名称
     *
     * @return 带别名名称
     */
    public String getNameWithAlias() {
        return name + " " + alias;
    }

    /**
     * 获取表名
     *
     * @param useAlias 是否使用别名
     * @return 表名
     */
    public String getTableName(boolean useAlias) {
        return useAlias ? getNameWithAlias() : getName();
    }

    /**
     * 获取查询名称
     *
     * @param userAlias 是否使用别名
     * @return 名称
     */
    public String getSelectTableName(boolean userAlias) {
        return (!userAlias || StringUtils.isBlank(alias)) ? getName() : getAlias();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Table table = (Table) o;
        return Objects.equals(getName(), table.getName())
                && Objects.equals(getAlias(), table.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAlias());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Table.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("alias='" + alias + "'")
                .toString();
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * <p>You can use getName() to get the value of name</p>
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of alias
     *
     * @return the value of alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the alias
     * <p>You can use getAlias() to get the value of alias</p>
     *
     * @param alias alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
}
