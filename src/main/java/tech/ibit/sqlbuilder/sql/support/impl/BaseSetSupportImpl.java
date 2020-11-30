package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.SetItem;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.SqlSupport;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * SetSupport实现
 *
 * @param <T> sql对象模板类型
 * @author iBit程序猿
 */
public class BaseSetSupportImpl<T> implements SqlSupport<T>, PrepareStatementBuildSupport {

    /**
     * sql 对象
     */
    private final T sql;

    /**
     * set
     */
    private final ListField<SetItem> set;


    /**
     * 构造函数
     *
     * @param sql sql对象
     * @param set 设置对象
     */
    public BaseSetSupportImpl(T sql, ListField<SetItem> set) {
        this.sql = sql;
        this.set = set;
    }

    @Override
    public T getSql() {
        return sql;
    }

    /**
     * 获取设置内容
     *
     * @return 设置内容
     */
    public ListField<SetItem> getSet() {
        return set;
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getSetItemPrepareStatement(String hook, boolean useAlias) {
        List<SetItem> setItems = getSet().getItems();
        if (CollectionUtils.isEmpty(setItems)) {
            return new PrepareStatement("", Collections.emptyList());
        }

        return getPrepareStatement(hook, setItems, ", ", useAlias);
    }
}
