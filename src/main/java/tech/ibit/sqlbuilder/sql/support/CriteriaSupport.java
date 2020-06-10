package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.ColumnValue;
import tech.ibit.sqlbuilder.Criteria;
import tech.ibit.sqlbuilder.PrepareStatement;

import java.util.List;
import java.util.Optional;

/**
 * Criteria Support
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface CriteriaSupport {

    /**
     * 扩展prepareSql和values
     *
     * @param criterion  条件列表
     * @param useAlias   是否使用别名
     * @param prepareSql 预查询sql
     * @param values     值列表
     */
    default void append(List<Criteria> criterion
            , boolean useAlias, StringBuilder prepareSql, List<ColumnValue> values) {

        for (int i = 0; i < criterion.size(); i++) {
            Criteria criteria = criterion.get(i);
            if (i != 0) {
                prepareSql.append(" ").append(criteria.getLogical().name()).append(" ");
            }
            Optional<PrepareStatement> itemPrepareStatement = Optional.ofNullable(criteria.getPrepareStatement(useAlias));
            if (itemPrepareStatement.isPresent()) {
                prepareSql.append(itemPrepareStatement.get().getPrepareSql());
                values.addAll(itemPrepareStatement.get().getValues());
            }
        }

    }
}
