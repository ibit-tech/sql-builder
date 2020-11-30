package tech.ibit.sqlbuilder.demo.entity;

import tech.ibit.sqlbuilder.annotation.DbColumn;
import tech.ibit.sqlbuilder.annotation.DbTable;

import java.util.Date;

/**
 * Entity for user_login_record
 *
 * @author iBit程序猿
 */
@DbTable(name = "user_login_record", alias = "ulr")
public class UserLoginRecord {

    /**
     * 用户id
     * VARCHAR(16)
     */
    @DbColumn(name = "user_id")
    private Integer userId;

    /**
     * 组织名称
     * TIMESTAMP(19)
     */
    @DbColumn(name = "login_time")
    private Date loginTime;

    /**
     * Gets the value of userId
     *
     * @return the value of userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the userId
     * <p>You can use getUserId() to get the value of userId</p>
     *
     * @param userId userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the value of loginTime
     *
     * @return the value of loginTime
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * Sets the loginTime
     * <p>You can use getLoginTime() to get the value of loginTime</p>
     *
     * @param loginTime loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
