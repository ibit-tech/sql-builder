package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 列-值信息
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class TableColumnValues {

    /**
     * 表
     */
    private Table table;

    /**
     * 列-值对列表
     */
    private List<ColumnValue> columnValues;
}
