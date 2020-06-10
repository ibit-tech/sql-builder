package tech.ibit.sqlbuilder;

import lombok.Data;

/**
 * 列（带别名）
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
public class ColumnAs extends Column {

    /**
     * 别名（as后面的名称）
     */
    private String nameAs;


    /**
     * 构造函数
     *
     * @param column 列
     * @param nameAs 别名（as后面的名称）
     */
    public ColumnAs(Column column, String nameAs) {
        super(column.getTable(), column.getName());
        this.nameAs = nameAs;
    }
}
