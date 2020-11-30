package tech.ibit.sqlbuilder;

import java.util.Collections;

/**
 * 支持MYSQL全文索引条件
 *
 * @author iBit程序猿
 * @since 2.6
 */
public class FullTextCriteriaItem extends CriteriaItem {

    /**
     * 全文索引列
     */
    private final FullTextColumn column;

    /**
     * 构造函数
     *
     * @param column 全文索引列
     */
    private FullTextCriteriaItem(FullTextColumn column) {
        this.column = column;
    }

    /**
     * 获取全文索引条件实例
     *
     * @param column 全文索引列
     * @return 全文索引条件实例
     */
    public static FullTextCriteriaItem getInstance(FullTextColumn column) {
        return new FullTextCriteriaItem(column);
    }


    @Override
    public PrepareStatement getPrepareStatement(boolean useAlias) {
        return new PrepareStatement(
                (useAlias ? column.getNameWithTableAlias() : column.getName()),
                Collections.singletonList(column.value(column.getValue()))
        );
    }
}
