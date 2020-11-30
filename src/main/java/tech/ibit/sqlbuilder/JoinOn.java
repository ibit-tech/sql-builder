package tech.ibit.sqlbuilder;

import tech.ibit.sqlbuilder.enums.JoinOnTypeEnum;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义JoinOn
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class JoinOn implements PrepareStatementSupplier {

    /**
     * 列对大小
     */
    public static final int PAIRS_SIZE = 2;

    /**
     * JoinOn类型
     */
    private final JoinOnTypeEnum joinOnType;

    /**
     * join表
     */
    private final Table table;

    /**
     * on列对
     */
    private final List<Column> columnPairs;

    /**
     * on支持条件
     */
    private final List<CriteriaItem> criteriaItems;


    /**
     * 构造函数
     *
     * @param joinOnType    joinOn类型
     * @param table         join表
     * @param columnPairs   on列对
     * @param criteriaItems on条件
     */
    private JoinOn(JoinOnTypeEnum joinOnType, Table table
            , List<Column> columnPairs, List<CriteriaItem> criteriaItems) {
        this.joinOnType = joinOnType;
        this.table = table;
        this.columnPairs = columnPairs;
        this.criteriaItems = criteriaItems;
    }


    /**
     * join on
     *
     * @param table         join表
     * @param columnPairs   on列对
     * @param criteriaItems on条件
     * @return JoinOnItem实例
     */
    public static JoinOn none(Table table, List<Column> columnPairs, List<CriteriaItem> criteriaItems) {
        return getInstance(null, table, columnPairs, criteriaItems);
    }

    /**
     * join on
     *
     * @param table       join表
     * @param columnPairs on列对
     * @return JoinOnItem实例
     */
    public static JoinOn none(Table table, List<Column> columnPairs) {
        return getInstance(null, table, columnPairs, null);
    }


    /**
     * full join on
     *
     * @param table         join表
     * @param columnPairs   on列对
     * @param criteriaItems on条件
     * @return JoinOnItem实例
     */
    public static JoinOn full(Table table, List<Column> columnPairs, List<CriteriaItem> criteriaItems) {
        return getInstance(JoinOnTypeEnum.FULL, table, columnPairs, criteriaItems);
    }

    /**
     * full join on
     *
     * @param table       join表
     * @param columnPairs on列对
     * @return JoinOnItem实例
     */
    public static JoinOn full(Table table, List<Column> columnPairs) {
        return getInstance(JoinOnTypeEnum.FULL, table, columnPairs, null);
    }


    /**
     * left join on
     *
     * @param table         join表
     * @param columnPairs   on列对
     * @param criteriaItems on条件
     * @return JoinOnItem实例
     */
    public static JoinOn left(Table table, List<Column> columnPairs, List<CriteriaItem> criteriaItems) {
        return getInstance(JoinOnTypeEnum.LEFT, table, columnPairs, criteriaItems);
    }

    /**
     * left join on
     *
     * @param table       join表
     * @param columnPairs on列对
     * @return JoinOnItem实例
     */
    public static JoinOn left(Table table, List<Column> columnPairs) {
        return getInstance(JoinOnTypeEnum.LEFT, table, columnPairs, null);
    }

    /**
     * right join on
     *
     * @param table         join表
     * @param columnPairs   on列对
     * @param criteriaItems on条件
     * @return JoinOnItem实例
     */
    public static JoinOn right(Table table, List<Column> columnPairs, List<CriteriaItem> criteriaItems) {
        return getInstance(JoinOnTypeEnum.RIGHT, table, columnPairs, criteriaItems);
    }

    /**
     * right join on
     *
     * @param table       join表
     * @param columnPairs on列对
     * @return JoinOnItem实例
     */
    public static JoinOn right(Table table, List<Column> columnPairs) {
        return getInstance(JoinOnTypeEnum.RIGHT, table, columnPairs, null);
    }


    /**
     * inner join on
     *
     * @param table         join表
     * @param columnPairs   on列对
     * @param criteriaItems on条件
     * @return JoinOnItem实例
     */
    public static JoinOn inner(Table table, List<Column> columnPairs, List<CriteriaItem> criteriaItems) {
        return getInstance(JoinOnTypeEnum.INNER, table, columnPairs, criteriaItems);
    }

    /**
     * inner join on
     *
     * @param table       join表
     * @param columnPairs on列对
     * @return JoinOnItem实例
     */
    public static JoinOn inner(Table table, List<Column> columnPairs) {
        return getInstance(JoinOnTypeEnum.INNER, table, columnPairs, null);
    }


    /**
     * 获取实例
     *
     * @param joinOnType    joinOn类型
     * @param table         join表
     * @param columnPairs   on列对
     * @param criteriaItems on条件
     * @return JoinOnItem实例
     */
    private static JoinOn getInstance(JoinOnTypeEnum joinOnType, Table table
            , List<Column> columnPairs, List<CriteriaItem> criteriaItems) {
        return new JoinOn(joinOnType, table, columnPairs, criteriaItems);
    }

    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {

        List<ColumnValue> values = new ArrayList<>();

        StringBuilder prepareSql = new StringBuilder();
        prepareSql.append(null == joinOnType ? "" : (joinOnType.name() + " "))
                .append("JOIN ")
                .append(table.getTableName(useAlias))
                .append(" ON ");

        boolean hasOn = false;

        // 普通列对
        if (CollectionUtils.isNotEmpty(columnPairs)) {
            for (int i = 0; i < columnPairs.size(); i += PAIRS_SIZE) {
                if (hasOn) {
                    prepareSql.append(" AND ");
                }
                prepareSql.append(CriteriaMaker.eq(
                        columnPairs.get(i).getCompareColumnName(useAlias), columnPairs.get(i + 1).getCompareColumnName(useAlias)));
                hasOn = true;
            }
        }

        // 带复杂条件
        if (CollectionUtils.isNotEmpty(criteriaItems)) {
            for (CriteriaItem item : criteriaItems) {
                if (hasOn) {
                    prepareSql.append(" AND ");
                }
                // 合并
                PrepareStatement itemPrepareStatement = item.getPrepareStatement(useAlias);
                prepareSql.append(itemPrepareStatement.getPrepareSql());
                values.addAll(itemPrepareStatement.getValues());

                hasOn = true;
            }
        }

        return new PrepareStatement(prepareSql.toString(), values);
    }

    /**
     * Gets the value of joinOnType
     *
     * @return the value of joinOnType
     */
    public JoinOnTypeEnum getJoinOnType() {
        return joinOnType;
    }

    /**
     * Gets the value of table
     *
     * @return the value of table
     */
    public Table getTable() {
        return table;
    }

    /**
     * Gets the value of columnPairs
     *
     * @return the value of columnPairs
     */
    public List<Column> getColumnPairs() {
        return columnPairs;
    }

    /**
     * Gets the value of criteriaItems
     *
     * @return the value of criteriaItems
     */
    public List<CriteriaItem> getCriteriaItems() {
        return criteriaItems;
    }
}
