package tech.ibit.sqlbuilder.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.Table;

import java.util.List;

/**
 * 列-值信息
 *
 * @author IBIT程序猿
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
