package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.PrepareStatementSupplier;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 条件支持
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface PrepareStatementSupport {


    /**
     * 获取 PrepareStatement
     *
     * @param <T>              处理类型
     * @param hook             开始位置
     * @param clauses          语句列表
     * @param prepareSqlGetter prepareSql获取
     * @param valuesGetter     values获取
     * @param separator        分割符
     * @param close            结束字符
     * @return 新的PrepareStatement
     */
    default <T> PrepareStatement getPrepareStatement(String hook
            , List<T> clauses, Function<T, String> prepareSqlGetter, Function<T, List<ColumnValue>> valuesGetter
            , String separator, String close) {
        if (CollectionUtils.isEmpty(clauses)) {
            return PrepareStatement.empty();
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();

        prepareSql.append(hook);

        for (int i = 0; i < clauses.size(); i++) {
            T clause = clauses.get(i);
            if (i != 0) {
                prepareSql.append(separator);
            }

            prepareSql.append(prepareSqlGetter.apply(clause));
            if (null != valuesGetter) {
                values.addAll(valuesGetter.apply(clause));
            }
        }

        prepareSql.append(close);

        return new PrepareStatement(prepareSql.toString(), values);
    }


    /**
     * 获取 PrepareStatement
     *
     * @param <T>              处理类型
     * @param hook             开始位置
     * @param clauses          语句列表
     * @param prepareSqlGetter prepareSql获取
     * @param valuesGetter     values获取
     * @param separator        分割符
     * @return 新的PrepareStatement
     */
    default <T> PrepareStatement getPrepareStatement(String hook
            , List<T> clauses, Function<T, String> prepareSqlGetter, Function<T, List<ColumnValue>> valuesGetter
            , String separator) {
        return getPrepareStatement(hook, clauses, prepareSqlGetter, valuesGetter, separator, "");
    }


    /**
     * 获取 PrepareStatement
     *
     * @param hook      开始位置
     * @param clauses   语句列表
     * @param separator 分割符
     * @param close     结束字符
     * @param useAlias  是否使用别名
     * @return 新的PrepareStatement
     */
    default PrepareStatement getPrepareStatement(String hook
            , List<? extends PrepareStatementSupplier> clauses, String separator, String close, boolean useAlias) {
        if (CollectionUtils.isEmpty(clauses)) {
            return PrepareStatement.empty();
        }

        StringBuilder prepareSql = new StringBuilder();
        List<ColumnValue> values = new ArrayList<>();

        prepareSql.append(hook);

        for (int i = 0; i < clauses.size(); i++) {
            PrepareStatementSupplier clause = clauses.get(i);
            if (i != 0) {
                prepareSql.append(separator);
            }

            PrepareStatement clausePrepareStatement = clause.getPrepareStatement(useAlias);
            prepareSql.append(clausePrepareStatement.getPrepareSql());
            values.addAll(clausePrepareStatement.getValues());
        }

        prepareSql.append(close);

        return new PrepareStatement(prepareSql.toString(), values);
    }


    /**
     * 获取 PrepareStatement
     *
     * @param hook      开始位置
     * @param clauses   语句列表
     * @param separator 分割符
     * @param useAlias  是否使用别名
     * @return 新的PrepareStatement
     */
    default PrepareStatement getPrepareStatement(String hook
            , List<? extends PrepareStatementSupplier> clauses, String separator, boolean useAlias) {
        return getPrepareStatement(hook, clauses, separator, "", useAlias);
    }
}
