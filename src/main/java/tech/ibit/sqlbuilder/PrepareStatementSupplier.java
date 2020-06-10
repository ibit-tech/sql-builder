package tech.ibit.sqlbuilder;


/**
 * PrepareStatement 生产者
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface PrepareStatementSupplier {

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    PrepareStatement getPrepareStatement(boolean useAlias);

    /**
     * 获取预查询SQL对象
     *
     * @return 预查询SQL对象
     */
    default PrepareStatement getPrepareStatement() {
        return getPrepareStatement(false);
    }
}
