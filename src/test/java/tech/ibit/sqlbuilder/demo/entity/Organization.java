package tech.ibit.sqlbuilder.demo.entity;

import tech.ibit.sqlbuilder.annotation.DbColumn;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;

/**
 * Entity for organization
 *
 * @author iBit程序猿
 */
@DbTable(name = "organization", alias = "o")
public class Organization {

    /**
     * 城市编码
     * VARCHAR(16)
     */
    @DbId(name = "city_code")
    private String cityCode;

    /**
     * 组织名称
     * VARCHAR(32)
     */
    @DbId(name = "name")
    private String name;

    /**
     * 组织类型
     * INT(10, 0)
     */
    @DbColumn(name = "type")
    private Integer type;

    /**
     * 手机
     * VARCHAR(16)
     */
    @DbColumn(name = "phone", nullable = true)
    private String phone;

    /**
     * Gets the value of cityCode
     *
     * @return the value of cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * Sets the cityCode
     * <p>You can use getCityCode() to get the value of cityCode</p>
     *
     * @param cityCode cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
     * Gets the value of phone
     *
     * @return the value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone
     * <p>You can use getPhone() to get the value of phone</p>
     *
     * @param phone phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
