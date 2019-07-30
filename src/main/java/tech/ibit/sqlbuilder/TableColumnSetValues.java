package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 列-值信息（带列额外信息）
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class TableColumnSetValues {

    /**
     * 表
     */
    private Table table;

    /**
     * 列-值对列表（带列额外信息）
     */
    private List<ColumnSetValue> columnValues;
}
