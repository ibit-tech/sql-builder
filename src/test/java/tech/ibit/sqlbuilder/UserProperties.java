package tech.ibit.sqlbuilder;

/**
 * Properties for User
 *
 * @author IBIT TECH
 * @version 1.0
 */
public interface UserProperties {

    Table TABLE_NAME = new Table("user", "u");

    Column userId = new Column(TABLE_NAME, "user_id");
    Column loginId = new Column(TABLE_NAME, "login_id");
    Column email = new Column(TABLE_NAME, "email");
    Column password = new Column(TABLE_NAME, "password");
    Column mobilePhone = new Column(TABLE_NAME, "mobile_phone");
    Column type = new Column(TABLE_NAME, "type");
    Column name = new Column(TABLE_NAME, "name");
    Column avatarId = new Column(TABLE_NAME, "avatar_id");
    Column currentProjectId = new Column(TABLE_NAME, "current_project_id");
    Column loginTimes = new Column(TABLE_NAME, "login_times");

    Column gender = new Column(TABLE_NAME, "gender");
    Column age = new Column(TABLE_NAME, "age");
    Column orgId = new Column(TABLE_NAME, "org_id");

}
