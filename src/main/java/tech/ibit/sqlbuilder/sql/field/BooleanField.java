package tech.ibit.sqlbuilder.sql.field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Distinct 字段
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooleanField {

    /**
     * 是否
     */
    private boolean value;

}
