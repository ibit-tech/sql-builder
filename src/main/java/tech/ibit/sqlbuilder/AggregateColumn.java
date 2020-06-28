package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.ArrayUtils;
import tech.ibit.sqlbuilder.builder.IColumnCriteriaItemBuilder;
import tech.ibit.sqlbuilder.builder.IColumnOrderByBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 定义聚合函数列
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class AggregateColumn implements IColumn,
        IColumnOrderByBuilder, IColumnCriteriaItemBuilder {

    /**
     * 列分割符
     */
    private static final String COLUMN_SEPARATOR = ", ";

    /**
     * 全部列通配符
     */
    private static final String ALL_COLUMNS = "*";

    /**
     * 函数名称
     */
    private String functionName;

    /**
     * 定义统计的列数组
     */
    private IColumn[] columns;

    /**
     * 列别名
     */
    private String nameAs;

    /**
     * 是否distinct
     */
    private boolean distinct;

    /**
     * 构造函数
     *
     * @param functionName 函数名称
     * @param columns      列数组
     * @param nameAs       别名
     */
    public AggregateColumn(String functionName, IColumn[] columns, String nameAs) {
        this(functionName, columns, nameAs, false);
    }

    /**
     * 获取列名称
     *
     * @return 列名称
     */
    @Override
    public String getName() {
        return getColumnName(getColumnNames());
    }

    /**
     * 获取带表别名的列名称
     *
     * @return 列名称
     */
    @Override
    public String getNameWithTableAlias() {
        return getColumnName(getColumnNamesWithTableAlias());
    }

    /**
     * 获取列名称
     *
     * @param columnNames 列名称字串
     * @return 列名称
     */
    private String getColumnName(String columnNames) {
        return functionName + "(" + (distinct ? "DISTINCT " : "") + columnNames + ")";
    }

    /**
     * 获取列名
     */
    private String getColumnNames() {
        if (ArrayUtils.isEmpty(columns)) {
            return ALL_COLUMNS;
        }
        return Arrays.stream(columns).map(IColumn::getName).collect(Collectors.joining(COLUMN_SEPARATOR));
    }

    /**
     * 获取带表别名的列名
     */
    private String getColumnNamesWithTableAlias() {
        if (ArrayUtils.isEmpty(columns)) {
            return ALL_COLUMNS;
        }
        return Arrays.stream(columns).map(IColumn::getNameWithTableAlias).collect(Collectors.joining(COLUMN_SEPARATOR));
    }

    @Override
    public IColumn getColumn() {
        return this;
    }
}
