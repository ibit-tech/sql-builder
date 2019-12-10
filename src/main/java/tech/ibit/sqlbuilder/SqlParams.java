package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
     * 参数列表（明细）
     */
    private List<KeyValuePair> paramDetails;

    /**
     * 获取参数列表
     *
     * @return 参数列表
     */
    public List<Object> getParams() {
        if (CollectionUtils.isEmpty(paramDetails)) {
            return Collections.emptyList();
        }
        return paramDetails.stream().map(KeyValuePair::getValue).collect(Collectors.toList());
    }
}
