package tech.ibit.sqlbuilder.demo.entity;

import tech.ibit.sqlbuilder.annotation.DbColumn;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;

/**
 * Entity for user
 *
 * @author iBit程序猿
 */
@DbTable(name = "user", alias = "u")
public class User {

    /**
     * 用户id
     * INT(10, 0)
     */
    @DbId(name = "user_id", autoIncrease = true)
    private Integer userId;

    /**
     * 登录id
     * VARCHAR(32)
     */
    @DbColumn(name = "login_id", nullable = true)
    private String loginId;

    /**
     * 用户名称
     * VARCHAR(64)
     */
    @DbColumn(name = "name")
    private String name;

    /**
     * 邮箱
     * VARCHAR(200)
     */
    @DbColumn(name = "email")
    private String email;

    /**
     * 密码
     * VARCHAR(64)
     */
    @DbColumn(name = "password")
    private String password;

    /**
     * 手机
     * VARCHAR(16)
     */
    @DbColumn(name = "mobile_phone")
    private String mobilePhone;

    /**
     * 用户类型
     * INT(10, 0)
     */
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
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * <p>You can use getName() to get the value of name</p>
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * <p>You can use getPassword() to get the value of password</p>
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
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
