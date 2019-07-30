package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 列-值对
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ColumnValue {

    /**
     * 列
     */
    private Column column;

    /**
     * 值
     */
    private Object value;
}
