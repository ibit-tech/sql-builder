package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.Criteria;
import tech.ibit.sqlbuilder.CriteriaItem;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Having Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface HavingSupport<T> extends SqlSupport<T>, CriteriaSupport {

    /**
     * Having
     *
     * @return Having
     */
    ListField<Criteria> getHaving();


    /**
     * `HAVING`语句
     *
     * @param having having语句对象
     * @return SQL对象
     */
    default T having(Criteria having) {
        getHaving().addItem(having);
        return getSql();
    }

    /**
     * `HAVING`语句
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    default T having(List<Criteria> havings) {
        getHaving().addItems(havings);
        return getSql();
    }

    /**
     * `HAVING AND item`语句
     *
     * @param havingItem having语句对象
     * @return SQL对象
     */
    default T andHaving(CriteriaItem havingItem) {
        having(havingItem.and());
        return getSql();
    }

    /**
     * `HAVING AND (havings)`语句
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    default T andHaving(List<Criteria> havings) {
        having(Criteria.and(havings));
        return getSql();
    }


    /**
     * `HAVING OR item`语句
     *
     * @param havingItem having语句对象
     * @return SQL对象
     */
    default T orHaving(CriteriaItem havingItem) {
        having(havingItem.or());
        return getSql();
    }

    /**
     * `HAVING OR (havings)`语句（多个OR关系）
     *
     * @param havings having语句对象列表
     * @return SQL对象
     */
    default T orHaving(List<Criteria> havings) {
        having(Criteria.or(havings));
        return getSql();
    }


    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    default PrepareStatement getHavingPrepareStatement(boolean useAlias) {
        List<Criteria> criterion = getHaving().getItems();
        if (CollectionUtils.isEmpty(criterion)) {
            return PrepareStatement.empty();
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();
        prepareSql.append(" HAVING ");

        append(criterion, useAlias, prepareSql, values);

        return new PrepareStatement(prepareSql.toString(), values);
    }


}
