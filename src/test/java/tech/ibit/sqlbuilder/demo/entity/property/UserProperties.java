package tech.ibit.sqlbuilder.demo.entity.property;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.Table;

/**
 * Table for user
 *
 * @author iBit程序猿
 */
public interface UserProperties {

    /**
     * 表名
     */
    Table TABLE = new Table("user", "u");

    /**
     * 用户id
     */
    Column userId = new Column(TABLE, "user_id");

    /**
     * 用户名称
     */
    Column name = new Column(TABLE, "name");

    /**
     * 登录id
     */
    Column loginId = new Column(TABLE, "login_id");

    /**
     * 邮箱
     */
    Column email = new Column(TABLE, "email");

    /**
     * 密码
     */
    Column password = new Column(TABLE, "password");

    /**
     * 手机
     */
    Column mobilePhone = new Column(TABLE, "mobile_phone");

    /**
     * 用户类型
     */
    Column type = new Column(TABLE, "type");

    /**
     * 组织id
     */
    Column orgId = new Column(TABLE, "org_id");

    /**
     * 当前项目
     */
    Column currentProjectId = new Column(TABLE, "current_project_id");

    /**
     * 年龄
     */
    Column age = new Column(TABLE, "age");

    /**
     * 性别
     */
    Column gender = new Column(TABLE, "gender");

    /**
     * 头像
     */
    Column avatarId = new Column(TABLE, "avatar_id");

    /**
     * 登录次数
     */
    Column loginTimes = new Column(TABLE, "login_times");


}
