package tech.ibit.sqlbuilder;

import tech.ibit.sqlbuilder.column.support.IColumnCriteriaItemSupport;
import tech.ibit.sqlbuilder.column.support.IColumnOrderBySupport;
import tech.ibit.sqlbuilder.enums.FullTextModeEnum;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * MySQL全文索引列
 *
 * @author iBit程序猿
 * @since 2.6
 */
public class FullTextColumn implements IColumn, IColumnOrderBySupport, IColumnCriteriaItemSupport {

    /**
     * 列分割符
     */
    private static final String COLUMN_SEPARATOR = ", ";

    /**
     * 全文索引列
     */
    private final IColumn[] columns;

    /**
     * 全文值
     */
    private final String value;

    /**
     * 全文索引模式
     */
    private final FullTextModeEnum mode;

    /**
     * 列别名
     */
    private final String nameAs;

    /**
     * 构造函数
     *
     * @param columns 列列表
     * @param value   值
     * @param nameAs  列别名
     */
    public FullTextColumn(IColumn[] columns, String value, String nameAs) {
        this(columns, value, null, nameAs);
    }

    /**
     * 构造函数
     *
     * @param columns 列列表
     * @param value   值
     * @param mode    全文索引模式
     * @param nameAs  列别名
     */
    public FullTextColumn(IColumn[] columns, String value, FullTextModeEnum mode, String nameAs) {
        this.columns = columns;
        this.value = value;
        this.mode = mode;
        this.nameAs = nameAs;
    }

    @Override
    public String getName() {
        return getColumnName(getColumnNames());
    }

    @Override
    public String getNameWithTableAlias() {
        return getColumnName(getColumnNamesWithTableAlias());
    }

    @Override
    public String getNameAs() {
        return nameAs;
    }

    @Override
    public IColumn getColumn() {
        return this;
    }

    /**
     * 转为 ColumnValue
     *
     * @return ColumnValue
     */
    public ColumnValue value() {
        return new ColumnValue(this, value);
    }

    /**
     * 获取全文搜索条件
     *
     * @return 全文搜索条件
     */
    public FullTextCriteriaItem match() {
        return FullTextCriteriaItem.getInstance(this);
    }

    /**
     * 获取列名称
     *
     * @param columnNames 列名称字符串
     * @return 列名称
     */
    private String getColumnName(String columnNames) {
        return "MATCH(" + columnNames + ") AGAINST(?" + getModeClause() + ")";
    }

    /**
     * 获取模式表达式
     *
     * @return 模式表达式
     */
    private String getModeClause() {
        if (null == mode) {
            return "";
        }
        switch (mode) {
            case BOOLEAN:
                return " IN BOOLEAN MODE";
            case NATURAL_LANGUAGE:
                return " IN NATURAL LANGUAGE MODE";
            default:
                return "";
        }
    }

    /**
     * 获取列名
     *
     * @return 列名字符串
     */
    private String getColumnNames() {
        return Arrays.stream(columns)
                .map(IColumn::getName)
                .collect(Collectors.joining(COLUMN_SEPARATOR));
    }

    /**
     * 获取带表别名的列名
     *
     * @return 带表别名的列名字符串
     */
    private String getColumnNamesWithTableAlias() {
        return Arrays.stream(columns)
                .map(IColumn::getNameWithTableAlias)
                .collect(Collectors.joining(COLUMN_SEPARATOR));
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the value of mode
     *
     * @return the value of mode
     */
    public FullTextModeEnum getMode() {
        return mode;
    }

    /**
     * Gets the value of columns
     *
     * @return the value of columns
     */
    public IColumn[] getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FullTextColumn.class.getSimpleName() + "[", "]")
                .add("columns=" + Arrays.toString(columns))
                .add("value='" + value + "'")
                .add("mode=" + mode)
                .add("nameAs='" + nameAs + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FullTextColumn column = (FullTextColumn) o;
        return Arrays.equals(getColumns(), column.getColumns()) &&
                getValue().equals(column.getValue()) &&
                getMode() == column.getMode() &&
                Objects.equals(getNameAs(), column.getNameAs());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getValue(), getMode(), getNameAs());
        result = 31 * result + Arrays.hashCode(getColumns());
        return result;
    }
}
