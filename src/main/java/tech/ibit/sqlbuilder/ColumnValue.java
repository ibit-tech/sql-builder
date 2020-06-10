package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 列-值对
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ColumnValue {

    /**
     * 列
     */
    private IColumn column;

    /**
     * 值
     */
    private Object value;
}
