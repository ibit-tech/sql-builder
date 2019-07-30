package tech.ibit.sqlbuilder;

import lombok.Data;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Having条件
 *
 * @author IBIT TECH
 * @version 1.0
 */
@Data
public class Having {

    /**
     * 条件关系
     */
    private CriteriaLogical logical;

    /**
     * 条件内容
     */
    private HavingItem item;


    /**
     * 字条件
     */
    private List<Having> subHavings;


    /**
     * 构造函数
     *
     * @param logical 条件关系
     * @param item    条件内容
     */
    public Having(CriteriaLogical logical, HavingItem item) {
        this.logical = logical;
        this.item = item;
    }

    /**
     * 构造函数
     *
     * @param logical    条件关系
     * @param subHavings 子having
     */
    public Having(CriteriaLogical logical, List<Having> subHavings) {
        this.logical = logical;
        this.subHavings = subHavings;
    }


    /**
     * 构造OR条件
     *
     * @param subHavings 子条件
     * @return OR条件
     */
    public static Having or(List<Having> subHavings) {
        return new Having(CriteriaLogical.OR, subHavings);
    }

    /**
     * 构造OR条件
     *
     * @param item 条件内容
     * @return OR条件
     */
    public static Having or(HavingItem item) {
        return new Having(CriteriaLogical.OR, item);
    }

    /**
     * 构造OR条件列表
     *
     * @param items 条件内容列表
     * @return OR条件列表
     */
    public static List<Having> ors(List<HavingItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items.stream().map(Having::or).collect(Collectors.toList());
    }


    /**
     * 构造AND条件
     *
     * @param subCriterion 子条件
     * @return AND条件
     */
    public static Having and(List<Having> subCriterion) {
        return new Having(CriteriaLogical.AND, subCriterion);
    }

    /**
     * 构造AND条件
     *
     * @param item 条件内容
     * @return AND条件
     */
    public static Having and(HavingItem item) {
        return new Having(CriteriaLogical.AND, item);
    }

    /**
     * 构造AND条件列表
     *
     * @param items 条件内容列表
     * @return AND条件列表
     */
    public static List<Having> ands(List<HavingItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items.stream().map(Having::and).collect(Collectors.toList());
    }


    /**
     * 获取预查询SQL对象
     *
     * @return 预查询SQL对象
     */
    public PrepareStatement getPrepareStatement() {
        if (null == item && CollectionUtils.isEmpty(subHavings)) {
            return null;
        }
        StringBuilder havingSQL = new StringBuilder();
        List<Object> havingParams = new ArrayList<>();
        appendHaving(this, havingSQL, havingParams);
        return new PrepareStatement(havingSQL.toString(), havingParams);
    }


    /**
     * 扩展`where`语句
     *
     * @param having       条件
     * @param havingSQL    `where`语句字串构造器
     * @param havingParams `where`参数列表
     */
    private void appendHaving(Having having, StringBuilder havingSQL, List<Object> havingParams) {
        if (null != having.getItem()) {
            HavingItem item = having.getItem();
            havingSQL.append(item.getPrepareSql());
            havingParams.add(item.getValue());
        } else if (CollectionUtils.isNotEmpty(having.getSubHavings())) {

            // 只有一个字having的时候优化
            if (1 == having.getSubHavings().size()) {
                appendHaving(having.getSubHavings().get(0), havingSQL, havingParams);
            } else {
                havingSQL.append("(");
                List<Having> subHavings = having.getSubHavings();
                for (int i = 0; i < subHavings.size(); i++) {
                    Having subHaving = subHavings.get(i);
                    if (0 != i) {
                        havingSQL.append(" ").append(subHaving.getLogical().name()).append(" ");
                    }
                    if (null != subHaving.getItem() || CollectionUtils.isNotEmpty(subHaving.getSubHavings())) {
                        appendHaving(subHaving, havingSQL, havingParams);
                    }
                }
                havingSQL.append(")");
            }
        }
    }
}
