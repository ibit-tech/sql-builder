package tech.ibit.sqlbuilder;

/**
 * 列（带别名）
 *
 * @author iBit程序猿
 * @version 1.0
 */
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

    @Override
    public String getNameAs() {
        return nameAs;
    }

    /**
     * Sets the nameAs
     * <p>You can use getNameAs() to get the value of nameAs</p>
     *
     * @param nameAs nameAs
     */
    public void setNameAs(String nameAs) {
        this.nameAs = nameAs;
    }
}
