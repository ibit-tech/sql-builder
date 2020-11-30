package tech.ibit.sqlbuilder.demo.entity;

import tech.ibit.sqlbuilder.annotation.DbColumn;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;

/**
 * User po
 *
 * @author iBit程序猿
 */
@DbTable(name = "user", alias = "u")
public class UserPo {

    @DbId(name = "user_id", autoIncrease = true)
    private Integer userId;

    @DbColumn(name = "login_id", nullable = true)
    private String loginId;

    @DbColumn(name = "email")
    private String email;

    @DbColumn(name = "mobile_phone")
    private String mobilePhone;

    @DbColumn(name = "type")
    private Integer type;

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
     * Gets the value of loginId
     *
     * @return the value of loginId
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Sets the loginId
     * <p>You can use getLoginId() to get the value of loginId</p>
     *
     * @param loginId loginId
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * Gets the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email
     * <p>You can use getEmail() to get the value of email</p>
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the value of mobilePhone
     *
     * @return the value of mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Sets the mobilePhone
     * <p>You can use getMobilePhone() to get the value of mobilePhone</p>
     *
     * @param mobilePhone mobilePhone
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

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
}
