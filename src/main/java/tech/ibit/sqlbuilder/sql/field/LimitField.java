package tech.ibit.sqlbuilder.sql.field;

/**
 * LimitField
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class LimitField {

    /**
     * 开始位置
     */
    private int start = -1;

    /**
     * 条数
     */
    private int limit = -1;

    /**
     * limit字段
     *
     * @param start 开始位置
     * @param limit 限制条数
     */
    public LimitField(int start, int limit) {
        this.start = start;
        this.limit = limit;
    }

    /**
     * limit字段
     */
    public LimitField() {
    }

    /**
     * 设置limit参数
     *
     * @param start 开始位置
     * @param limit 条数
     */
    public void limit(int start, int limit) {
        this.start = start;
        this.limit = limit;
    }

    /**
     * 设置limit参数
     *
     * @param limit 条数
     */
    public void limit(int limit) {
        limit(0, limit);
    }

    /**
     * Gets the value of start
     *
     * @return the value of start
     */
    public int getStart() {
        return start;
    }

    /**
     * Sets the start
     * <p>You can use getStart() to get the value of start</p>
     *
     * @param start start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Gets the value of limit
     *
     * @return the value of limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets the limit
     * <p>You can use getLimit() to get the value of limit</p>
     *
     * @param limit limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
