package tech.ibit.sqlbuilder;

import lombok.Data;
import tech.ibit.sqlbuilder.enums.CriteriaLogicalEnum;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 查询条件（where 或 having）
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
public class Criteria implements PrepareStatementSupplier {

    /**
     * 条件关系
     */
    private CriteriaLogicalEnum logical;

    /**
     * 条件内容
     */
    private CriteriaItem item;

    /**
     * 字条件
     */
    private List<Criteria> subCriterion;

    /**
     * 构造函数
     *
     * @param logical      条件关系
     * @param subCriterion 子条件列表
     */
    private Criteria(CriteriaLogicalEnum logical, List<Criteria> subCriterion) {
        this.logical = logical;
        this.subCriterion = subCriterion;
    }

    /**
     * 构造函数
     *
     * @param logical 条件关系
     * @param item    条件内容
     */
    private Criteria(CriteriaLogicalEnum logical, CriteriaItem item) {
        this.logical = logical;
        this.item = item;
    }

    /**
     * 构造OR条件
     *
     * @param subCriterion 子条件
     * @return OR条件
     */
    public static Criteria or(List<Criteria> subCriterion) {
        return new Criteria(CriteriaLogicalEnum.OR, subCriterion);
    }

    /**
     * 构造OR条件
     *
     * @param item 条件内容
     * @return OR条件
     */
    public static Criteria or(CriteriaItem item) {
        return new Criteria(CriteriaLogicalEnum.OR, item);
    }

    /**
     * 构造AND条件
     *
     * @param subCriterion 子条件
     * @return AND条件
     */
    public static Criteria and(List<Criteria> subCriterion) {
        return new Criteria(CriteriaLogicalEnum.AND, subCriterion);
    }

    /**
     * 构造AND条件
     *
     * @param item 条件内容
     * @return AND条件
     */
    public static Criteria and(CriteriaItem item) {
        return new Criteria(CriteriaLogicalEnum.AND, item);
    }


    /**
     * 构造OR条件列表
     *
     * @param items 列表中的项目为CriteriaItem或者List&lt;Criteria&gt;
     * @return OR条件列表
     */
    @SuppressWarnings("unchecked")
    public static List<Criteria> ors(List items) {
        if (null == items || items.isEmpty()) {
            return Collections.emptyList();
        }
        List<Criteria> criterion = new ArrayList<>();
        for (Object item : items) {
            if (item instanceof CriteriaItem) {
                criterion.add(or((CriteriaItem) item));
            } else {
                criterion.add(or((List<Criteria>) item));
            }
        }
        return criterion;
    }


    /**
     * 构造AND条件列表
     *
     * @param items 列表中的项目为CriteriaItem或者List&lt;Criteria&gt;
     * @return AND条件列表
     */
    @SuppressWarnings("unchecked")
    public static List<Criteria> ands(List items) {
        if (null == items || items.isEmpty()) {
            return Collections.emptyList();
        }
        List<Criteria> criterion = new ArrayList<>();
        for (Object item : items) {
            if (item instanceof CriteriaItem) {
                criterion.add(and((CriteriaItem) item));
            } else {
                criterion.add(and((List<Criteria>) item));
            }
        }
        return criterion;
    }

    /**
     * 获取预查询SQL对象
     *
     * @param useAlias 是否使用别名
     * @return 预查询SQL对象
     */
    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {
        if (null == item && CollectionUtils.isEmpty(subCriterion)) {
            return null;
        }
        StringBuilder whereSql = new StringBuilder();
        List<ColumnValue> whereParams = new ArrayList<>();
        appendCriteria(this, whereSql, whereParams, useAlias);
        return new PrepareStatement(whereSql.toString(), whereParams);
    }


    /**
     * 扩展条件语句
     *
     * @param criteria    条件
     * @param criteriaSql `where`语句字串构造器
     * @param whereParams `where`参数列表
     */
    private void appendCriteria(Criteria criteria, StringBuilder criteriaSql, List<ColumnValue> whereParams, boolean useAlias) {
        if (null != criteria.getItem()) {

            CriteriaItem item = criteria.getItem();
            PrepareStatement statement = item.getPrepareStatement(useAlias);
            criteriaSql.append(statement.getPrepareSql());
            whereParams.addAll(statement.getValues());

        } else if (CollectionUtils.isNotEmpty(criteria.getSubCriterion())) {
            // 条件只有一个的时候优化
            if (1 == criteria.getSubCriterion().size()) {
                appendCriteria(criteria.getSubCriterion().get(0), criteriaSql, whereParams, useAlias);
            } else {
                criteriaSql.append("(");
                List<Criteria> subCriterion = criteria.getSubCriterion();
                for (int i = 0; i < subCriterion.size(); i++) {
                    Criteria subCriteria = subCriterion.get(i);
                    if (0 != i) {
                        criteriaSql.append(" ").append(subCriteria.getLogical().name()).append(" ");
                    }
                    if (null != subCriteria.getItem() || CollectionUtils.isNotEmpty(subCriteria.getSubCriterion())) {
                        appendCriteria(subCriteria, criteriaSql, whereParams, useAlias);
                    }
                }
                criteriaSql.append(")");
            }
        }
    }
}
