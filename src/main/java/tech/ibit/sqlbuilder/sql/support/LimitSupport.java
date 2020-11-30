package tech.ibit.sqlbuilder.sql.support;

/**
 * Limit Support
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface LimitSupport<T> {

    /**
     * `LIMIT #{start}, #{limit}` 语句
     *
     * @param start 开始位置
     * @param limit 限制条数
     * @return SQL对象
     */
    T limit(int start, int limit);

    /**
     * `LIMIT 0, #{limit}` 语句
     *
     * @param limit 限制条数
     * @return SQL对象
     */
    T limit(int limit);

}
