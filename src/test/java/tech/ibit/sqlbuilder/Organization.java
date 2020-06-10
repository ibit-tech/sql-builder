package tech.ibit.sqlbuilder;

import tech.ibit.sqlbuilder.annotation.DbColumn;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;

/**
 * @author IBIT程序猿
 * @version 1.0
 */
@DbTable(name = "organization", alias = "o")
public class Organization {

    @DbId(name = "city_code")
    private String cityCode;

    @DbId(name = "name")
    private String name;

    @DbColumn(name = "type")
    private Integer type;

    @DbColumn(name = "phone", nullable = true)
    private String phone;


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
