package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SimpleNameColumn
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleNameColumn implements IColumn {

    /**
     * 名称
     */
    private String name;

    @Override
    public String getNameWithTableAlias() {
        return getName();
    }

    @Override
    public String getNameAs() {
        return getName();
    }
}
