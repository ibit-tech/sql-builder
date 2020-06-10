package tech.ibit.sqlbuilder.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ibit.sqlbuilder.Column;

/**
 * 列信息
 *
 * @author IBIT-TECH
 * mailto: ibit_tech@aliyun.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo {

    /**
     * 列
     */
    private Column column;

    /**
     * 是否为id
     */
    private boolean isId;


    /**
     * 是否允许为空
     */
    private boolean nullable;
}
