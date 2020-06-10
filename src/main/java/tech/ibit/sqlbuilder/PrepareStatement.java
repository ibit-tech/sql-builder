package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预查询SQL对象
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class PrepareStatement {

    /**
     * 预查询SQL
     */
    private String prepareSql;

    /**
     * 插入值列表
     */
    private List<ColumnValue> values;

    /**
     * 获取空的PrepareStatement
     *
     * @return PrepareStatement
     */
    public static PrepareStatement empty() {
        return new PrepareStatement("", Collections.emptyList());
    }

    /**
     * 获取参数列表
     *
     * @return 参数列表
     */
    public List<Object> getParams() {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return values.stream().map(ColumnValue::getValue).collect(Collectors.toList());
    }
}
