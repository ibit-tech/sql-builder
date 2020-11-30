package tech.ibit.sqlbuilder;

import java.util.Arrays;
import java.util.List;

/**
 * 定义 Unique 键
 *
 * @author ben
 */
public class UniqueKey {

    /**
     * 键-值
     */
    private List<ColumnValue> columnValues;

    public UniqueKey(ColumnValue... columnValues) {
        this.columnValues = Arrays.asList(columnValues);
    }

    /**
     * Gets the value of columnValues
     *
     * @return the value of columnValues
     */
    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }

    /**
     * Sets the columnValues
     * <p>You can use getColumnValues() to get the value of columnValues</p>
     *
     * @param columnValues columnValues
     */
    public void setColumnValues(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }
}
