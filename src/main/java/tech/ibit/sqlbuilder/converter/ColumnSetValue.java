package tech.ibit.sqlbuilder.converter;

import lombok.Data;
import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.ColumnValue;

/**
 * 列-值对（带列额外信息）
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
public class ColumnSetValue extends ColumnValue {

    /**
     * 是否为主键
     */
    private boolean id;

    /**
     * 是否可为null
     */
    private boolean nullable;

    /**
     * 是否自增长
     */
    private boolean autoIncrease;

    public ColumnSetValue(Column column, Object value, boolean id
            , boolean nullable, boolean autoIncrease) {
        super(column, value);
        this.id = id;
        this.nullable = nullable;
        this.autoIncrease = autoIncrease;
    }
}
