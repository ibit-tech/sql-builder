package tech.ibit.sqlbuilder.demo.entity;

/**
 * 用户类型统计
 *
 * @author iBit程序猿
 * mailto: ibit_tech@aliyun.com
 */
public class UserTypeTotal {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 总数
     */
    private Integer total;

    /**
     * Gets the value of type
     *
     * @return the value of type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets the type
     * <p>You can use getType() to get the value of type</p>
     *
     * @param type type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Gets the value of total
     *
     * @return the value of total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * Sets the total
     * <p>You can use getTotal() to get the value of total</p>
     *
     * @param total total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }
}
