package tech.ibit.sqlbuilder.demo.entity;


import tech.ibit.sqlbuilder.MultiId;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;

/**
 * Entity for organization
 *
 * @author iBit程序猿
 */
@DbTable(name = "organization", alias = "o")
public class OrganizationKey implements MultiId {

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

    public OrganizationKey(String cityCode, String name) {
        this.cityCode = cityCode;
        this.name = name;
    }

    public OrganizationKey() {
    }

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
}
