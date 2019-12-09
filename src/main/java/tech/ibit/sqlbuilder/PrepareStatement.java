package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 预查询SQL对象
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class PrepareStatement<T> {

    /**
     * 预查询SQL
     */
    private String prepareSql;

    /**
     * 插入值列表
     */
    private List<T> values;
}
