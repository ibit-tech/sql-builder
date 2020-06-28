package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.converter.EntityConverter;
import tech.ibit.sqlbuilder.sql.field.ListField;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.List;

/**
 * Column支持
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface ColumnSupport<T> extends SqlSupport<T>, PrepareStatementSupport {

    /**
     * 获取列
     *
     * @return 列
     */
    ListField<IColumn> getColumn();

    /**
     * `t.column1, t.column2, ...`语句, "t": 为表的别名
     *
     * @param columns 查询字段对象
     * @return SQL对象
     * @see IColumn
     */
    default T column(List<? extends IColumn> columns) {
        getColumn().addItems(columns);
        return getSql();
    }

    /**
     * `t.column`语句, "t": 为表的别名
     *
     * @param column 查询字段对象
     * @return SQL对象
     * @see IColumn
     */
    default T column(IColumn column) {
        getColumn().addItem(column);
        return getSql();
    }

    /**
     * 传入实体类
     *
     * @param poClass 实体类
     * @return SQL对象
     */
    default T columnPo(Class poClass) {
        getColumn().addItems(EntityConverter.getColumns(poClass));
        return getSql();
    }


    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    default PrepareStatement getColumnPrepareStatement(boolean useAlias) {
        List<IColumn> columns = getColumn().getItems();
        if (CollectionUtils.isEmpty(columns)) {
            return PrepareStatement.empty();
        }

        return getPrepareStatement("", columns
                , (IColumn column) -> column.getSelectColumnName(useAlias), null, ", ");
    }


}
