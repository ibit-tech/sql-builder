package tech.ibit.sqlbuilder;

/**
 * 定义OrderBy接口
 *
 * @author IBIT TECH
 * @version 1.0
 */
public interface IOrderBy {

    /**
     * 构造预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return BETWEEN值预查询SQL对象
     */
    PrepareStatement getPrepareStatement(boolean useAlias);
}
