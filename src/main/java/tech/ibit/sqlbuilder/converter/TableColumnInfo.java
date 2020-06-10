package tech.ibit.sqlbuilder.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.Table;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表字段信息类
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableColumnInfo {

    /**
     * 表
     */
    private Table table;

    /**
     * 列
     */
    private List<ColumnInfo> columnInfoList;

    /**
     * 获取非主键列
     *
     * @return 非主键列
     */
    public List<Column> getNotIdColumns() {
        if (CollectionUtils.isEmpty(columnInfoList)) {
            return Collections.emptyList();
        }
        return columnInfoList.stream().filter(c -> !c.isId())
                .map(ColumnInfo::getColumn).collect(Collectors.toList());
    }

    /**
     * 获取主键列
     *
     * @return 主键列
     */
    public List<Column> getIds() {
        if (CollectionUtils.isEmpty(columnInfoList)) {
            return Collections.emptyList();
        }
        return columnInfoList.stream().filter(ColumnInfo::isId)
                .map(ColumnInfo::getColumn).collect(Collectors.toList());
    }

    /**
     * 获取列
     *
     * @return 列
     */
    public List<Column> getColumns() {
        if (CollectionUtils.isEmpty(columnInfoList)) {
            return Collections.emptyList();
        }
        return columnInfoList.stream()
                .map(ColumnInfo::getColumn).collect(Collectors.toList());
    }
}
