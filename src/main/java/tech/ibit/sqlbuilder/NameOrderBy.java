package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 指定名称的OrderBy
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class NameOrderBy implements IOrderBy {

    /**
     * 名称
     */
    private String name;


    /**
     * 是否倒叙
     */
    private boolean desc;


    /**
     * 构造函数
     *
     * @param name 名称
     */
    public NameOrderBy(String name) {
        this(name, false);
    }

    /**
     * 构造预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return BETWEEN值预查询SQL对象
     */
    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {
        String prepareSql = name + (desc ? " DESC" : "");
        return new PrepareStatement(prepareSql, null);
    }
}
