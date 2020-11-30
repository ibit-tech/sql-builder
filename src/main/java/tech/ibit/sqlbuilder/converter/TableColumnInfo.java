package tech.ibit.sqlbuilder.converter;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表字段信息类
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class TableColumnInfo {

    /**
     * 表
     */
    private Table table;

    /**
     * 列
     */
    private List<ColumnInfo> columnInfos;

    /**
     * 无参构造函数
     */
    public TableColumnInfo() {
    }

    /**
     * 构造函数
     *
     * @param table       表
     * @param columnInfos 列信息列表
     */
    public TableColumnInfo(Table table, List<ColumnInfo> columnInfos) {
        this.table = table;
        this.columnInfos = columnInfos;
    }

    /**
     * 获取非主键列
     *
     * @return 非主键列
     */
    public List<Column> getNotIdColumns() {
        if (CollectionUtils.isEmpty(columnInfos)) {
            return Collections.emptyList();
        }
        return columnInfos.stream().filter(c -> !c.isId())
                .map(ColumnInfo::getColumn).collect(Collectors.toList());
    }

    /**
     * 获取主键列
     *
     * @return 主键列
     */
    public List<Column> getIds() {
        if (CollectionUtils.isEmpty(columnInfos)) {
            return Collections.emptyList();
        }
        return columnInfos.stream().filter(ColumnInfo::isId)
                .map(ColumnInfo::getColumn).collect(Collectors.toList());
    }

    /**
     * 获取列
     *
     * @return 列
     */
    public List<Column> getColumns() {
        if (CollectionUtils.isEmpty(columnInfos)) {
            return Collections.emptyList();
        }
        return columnInfos.stream()
                .map(ColumnInfo::getColumn).collect(Collectors.toList());
    }

    /**
     * Gets the value of table
     *
     * @return the value of table
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets the table
     * <p>You can use getTable() to get the value of table</p>
     *
     * @param table table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Gets the value of columnInfos
     *
     * @return the value of columnInfos
     */
    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    /**
     * Sets the columnInfos
     * <p>You can use getColumnInfos() to get the value of columnInfos</p>
     *
     * @param columnInfos columnInfos
     */
    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }
}
