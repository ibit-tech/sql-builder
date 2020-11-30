package tech.ibit.sqlbuilder.sql.support.impl;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.SetItem;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.sql.support.SetSupport;

import java.util.List;

/**
 * SetSupport实现
 *
 * @author iBit程序猿
 */
public class SetSupportImpl<T> extends BaseSetSupportImpl<T> implements SetSupport<T> {

    /**
     * 构造函数
     *
     * @param sql sql对象
     */
    public SetSupportImpl(T sql) {
        super(sql, new ListField<>());
    }

    @Override
    public T set(SetItem item) {
        getSet().addItem(item);
        return getSql();
    }

    @Override
    public T set(List<SetItem> items) {
        getSet().addItems(items);
        return getSql();
    }

    @Override
    public T set(Column column, Object value) {
        return set(column.set(value));
    }

    @Override
    public T increaseSet(Column column, Number value) {
        return set(column.increaseSet(value));
    }

    @Override
    public T decreaseSet(Column column, Number value) {
        return set(column.decreaseSet(value));
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    public PrepareStatement getSetItemPrepareStatement(boolean useAlias) {
        return getSetItemPrepareStatement(" SET ", useAlias);
    }
}
