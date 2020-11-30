package tech.ibit.sqlbuilder;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * SimpleNameColumn
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class SimpleNameColumn implements IColumn {

    /**
     * 名称
     */
    private String name;

    /**
     * 无参构造函数
     */
    public SimpleNameColumn() {
    }

    /**
     * 构造函数
     *
     * @param name 名称
     */
    public SimpleNameColumn(String name) {
        this.name = name;
    }

    @Override
    public String getNameWithTableAlias() {
        return getName();
    }

    @Override
    public String getNameAs() {
        return getName();
    }

    @Override
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

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SimpleNameColumn.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
