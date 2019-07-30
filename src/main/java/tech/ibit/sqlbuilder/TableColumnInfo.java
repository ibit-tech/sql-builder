package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 表字段信息类
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class TableColumnInfo {

    /**
     * 表
     */
    private Table table;

    /**
     * 主键字段
     */
    private List<Column> ids;

    /**
     * 列字段
     */
    private List<Column> columns;

    /**
     * 获取非主键列
     *
     * @return 非主键列
     */
    public List<Column> getNotIdColumns() {
        if (CollectionUtils.isEmpty(ids)) {
            return columns;
        }
        if (CollectionUtils.isEmpty(columns)) {
            return Collections.emptyList();
        }
        Set<String> ignoreColumnAlias = ids.stream()
                .map(Column::getNameWithTableAlias)
                .collect(Collectors.toSet());
        return columns.stream()
                .filter(column -> !ignoreColumnAlias.contains(column.getNameWithTableAlias()))
                .collect(Collectors.toList());
    }
}
