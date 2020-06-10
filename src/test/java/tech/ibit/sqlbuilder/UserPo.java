package tech.ibit.sqlbuilder;

import tech.ibit.sqlbuilder.annotation.DbColumn;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;

/**
 * @author IBIT程序猿
 * @version 1.0
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
