package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.ibit.sqlbuilder.annotation.DbId;
import tech.ibit.sqlbuilder.annotation.DbTable;

/**
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
@AllArgsConstructor
@DbTable(name = "organization", alias = "o")
public class OrganizationMultiId implements MultiId {

    @DbId(name = "city_code")
    private String cityCode;

    @DbId(name = "name")
    private String name;

    public OrganizationMultiId() {
    }
}
