package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.SimpleNameColumn;
import tech.ibit.sqlbuilder.sql.field.LimitField;

import java.util.Arrays;
import java.util.List;

/**
 * Limit Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface LimitSupport<T> extends SqlSupport<T> {

    /**
     * 获取limit相关参数
     *
     * @return limit相关参数
     */
    LimitField getLimit();

    /**
     * `LIMIT #{start}, #{limit}` 语句
     *
     * @param start 开始位置
     * @param limit 限制条数
     * @return SQL对象
     */
    default T limit(int start, int limit) {
        getLimit().limit(start, limit);
        return getSql();
    }

    /**
     * `LIMIT 0, #{limit}` 语句
     *
     * @param limit 限制条数
     * @return SQL对象
     */
    default T limit(int limit) {
        getLimit().limit(limit);
        return getSql();
    }


    /**
     * 获取预查询SQL对象
     *
     * @return 预查询SQL对象
     */
    default PrepareStatement getLimitPrepareStatement() {
        LimitField limitField = getLimit();
        int limit = limitField.getLimit();
        if (limit < 0) {
            return PrepareStatement.empty();
        }

        int start = limitField.getStart();

        String prepareSql = " LIMIT ?, ?";
        List<ColumnValue> values = Arrays.asList(
                new ColumnValue(new SimpleNameColumn("$start"), start),
                new ColumnValue(new SimpleNameColumn("$limit"), limit));
        return new PrepareStatement(prepareSql, values);
    }


}
