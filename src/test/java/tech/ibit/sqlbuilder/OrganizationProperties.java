package tech.ibit.sqlbuilder;

/**
 * Table for organization
 *
 * @author IBIT程序猿
 * @version 1.0
 */
public interface OrganizationProperties {

    Table TABLE = new Table("organization", "o");

    Column orgId = new Column(TABLE, "org_id");
    Column cityCode = new Column(TABLE, "city_code");
    Column name = new Column(TABLE, "name");
    Column type = new Column(TABLE, "type");
    Column phone = new Column(TABLE, "phone");

}
