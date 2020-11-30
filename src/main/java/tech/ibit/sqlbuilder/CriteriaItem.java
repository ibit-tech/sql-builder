package tech.ibit.sqlbuilder;


/**
 * 条件内容对象
 *
 * @author iBit程序猿
 * @version 1.0
 */
public abstract class CriteriaItem implements PrepareStatementSupplier {

    /**
     * 生成or条件
     *
     * @return or条件
     */
    public Criteria or() {
        return Criteria.or(this);
    }

    /**
     * 生成and条件
     *
     * @return and条件
     */
    public Criteria and() {
        return Criteria.and(this);
    }

}
