package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * SQL参数对象
 *
 * @author IBIT TECH
 * @version 1.0
 */

@Data
@AllArgsConstructor
public class SqlParams {

    /**
     * SQL语句
     */
    private String sql;

    /**
     * 参数列表
     */
    private List<Object> params;
}
