package tech.ibit.sqlbuilder.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tech.ibit.sqlbuilder.CommonTest;
import tech.ibit.sqlbuilder.OrganizationProperties;
import tech.ibit.sqlbuilder.SqlParams;
import tech.ibit.sqlbuilder.UserProperties;
import tech.ibit.sqlbuilder.Organization;
import tech.ibit.sqlbuilder.OrganizationMultiId;
import tech.ibit.sqlbuilder.User;
import tech.ibit.sqlbuilder.UserMultiId;
import tech.ibit.sqlbuilder.exception.*;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author IBIT TECH
 * @version 1.0
 */
public class DaoUtilsTest extends CommonTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getByIds() {
        SqlParams sqlParams = DaoUtils.getByIds(User.class, Arrays.asList(1, 2, 3));
        assertParamsEquals("SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id IN(?, ?, ?) LIMIT ?, ?",
                Arrays.asList("u.user_id", 1, "u.user_id", 2, "u.user_id", 3, "$start", 0, "$limit", 3), sqlParams);

        sqlParams = DaoUtils.getByIds(User.class, Collections.singletonList(1));
        assertParamsEquals("SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList("u.user_id", 1, "$start", 0, "$limit", 1), sqlParams);
    }

    @Test
    public void getById() {
        SqlParams sqlParams = DaoUtils.getById(User.class, 1);
        assertParamsEquals("SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList("u.user_id", 1, "$start", 0, "$limit", 1), sqlParams);
    }

    @Test
    public void getByMultiIds() {
        UserMultiId uKey1 = new UserMultiId(1);
        UserMultiId uKey2 = new UserMultiId(2);
        SqlParams sqlParams = DaoUtils.getByMultiIds(User.class, Arrays.asList(uKey1, uKey2));
        assertParamsEquals("SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id IN(?, ?) LIMIT ?, ?",
                Arrays.asList("u.user_id", 1, "u.user_id", 2, "$start", 0, "$limit", 2), sqlParams);


        OrganizationMultiId oKey1 = new OrganizationMultiId("001", "001");
        OrganizationMultiId oKey2 = new OrganizationMultiId("001", "002");
        sqlParams = DaoUtils.getByMultiIds(Organization.class, Arrays.asList(oKey1, oKey2));
        assertParamsEquals("SELECT o.city_code, o.name, o.type, o.phone FROM organization o WHERE (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?) LIMIT ?, ?",
                Arrays.asList("o.city_code", "001", "o.name", "001", "o.city_code", "001", "o.name", "002", "$start", 0, "$limit", 2), sqlParams);
    }

    @Test
    public void getByMultiId() {
        UserMultiId uKey1 = new UserMultiId(1);
        SqlParams sqlParams = DaoUtils.getByMultiId(User.class, uKey1);
        assertParamsEquals("SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList("u.user_id", 1, "$start", 0, "$limit", 1), sqlParams);

        OrganizationMultiId oKey1 = new OrganizationMultiId("001", "001");
        sqlParams = DaoUtils.getByMultiId(Organization.class, oKey1);
        assertParamsEquals("SELECT o.city_code, o.name, o.type, o.phone FROM organization o WHERE (o.city_code = ? AND o.name = ?) LIMIT ?, ?",
                Arrays.asList("o.city_code", "001", "o.name", "001", "$start", 0, "$limit", 1), sqlParams);
    }

    @Test
    public void deleteByIds() {
        SqlParams sqlParams = DaoUtils.deleteByIds(User.class, Arrays.asList(1, 2));
        assertParamsEquals("DELETE FROM user WHERE user_id IN(?, ?)",
                Arrays.asList("user_id", 1, "user_id", 2), sqlParams);

        sqlParams = DaoUtils.deleteByIds(User.class, Collections.singletonList(1));
        assertParamsEquals("DELETE FROM user WHERE user_id = ?",
                Arrays.asList("user_id", 1), sqlParams);
    }

    @Test
    public void deleteById() {
        SqlParams sqlParams = DaoUtils.deleteById(User.class, 1);
        assertParamsEquals("DELETE FROM user WHERE user_id = ?",
                Arrays.asList("user_id", 1), sqlParams);
    }

    @Test
    public void deleteByMultiIds() {
        UserMultiId uKey1 = new UserMultiId(1);
        UserMultiId uKey2 = new UserMultiId(2);

        SqlParams sqlParams = DaoUtils.deleteByMultiIds(Collections.singletonList(uKey1));
        assertParamsEquals("DELETE FROM user WHERE user_id = ?",
                Arrays.asList("user_id", 1), sqlParams);

        sqlParams = DaoUtils.deleteByMultiIds(Arrays.asList(uKey1, uKey2));
        assertParamsEquals("DELETE FROM user WHERE user_id IN(?, ?)",
                Arrays.asList("user_id", 1, "user_id", 2), sqlParams);

        OrganizationMultiId oKey1 = new OrganizationMultiId("001", "001");
        OrganizationMultiId oKey2 = new OrganizationMultiId("001", "002");

        sqlParams = DaoUtils.deleteByMultiIds(Collections.singletonList(oKey1));
        assertParamsEquals("DELETE FROM organization WHERE (city_code = ? AND name = ?)",
                Arrays.asList("city_code", "001", "name", "001"), sqlParams);

        sqlParams = DaoUtils.deleteByMultiIds(Arrays.asList(oKey1, oKey2));
        assertParamsEquals("DELETE FROM organization WHERE (city_code = ? AND name = ?) OR (city_code = ? AND name = ?)",
                Arrays.asList("city_code", "001", "name", "001", "city_code", "001", "name", "002"), sqlParams);
    }

    @Test
    public void deleteByMultiId() {
        UserMultiId uKey1 = new UserMultiId(1);
        SqlParams sqlParams = DaoUtils.deleteByMultiId(uKey1);
        assertParamsEquals("DELETE FROM user WHERE user_id = ?",
                Arrays.asList("user_id", 1), sqlParams);

        OrganizationMultiId oKey1 = new OrganizationMultiId("001", "001");
        sqlParams = DaoUtils.deleteByMultiId(oKey1);
        assertParamsEquals("DELETE FROM organization WHERE (city_code = ? AND name = ?)",
                Arrays.asList("city_code", "001", "name", "001"), sqlParams);
    }

    @Test
    public void insertInto() {
        User user = new User();
        user.setLoginId("ibit_tech@aliyun.com");
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        user.setType(1);

        SqlParams sqlParams = DaoUtils.insertInto(user);
        assertParamsEquals("INSERT INTO user(login_id, email, mobile_phone, type) VALUES(?, ?, ?, ?)",
                Arrays.asList("login_id", "ibit_tech@aliyun.com", "email", "ibit_tech@aliyun.com", "mobile_phone", "188", "type", 1), sqlParams);
    }

    @Test
    public void insertInto1() {
        User user = new User();
        thrown.expect(ColumnValueNotFoundException.class);
        thrown.expectMessage("Column value not found!");
        DaoUtils.insertInto(user);
    }

    @Test
    public void insertInto2() {
        User user = new User();
        user.setLoginId("ibit_tech@aliyun.com");
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        user.setType(1);
        user.setUserId(1);

        thrown.expect(IdAutoIncreaseException.class);
        thrown.expectMessage("Table(user)'s id(user_id) cannot be inserted!");

        DaoUtils.insertInto(user);
    }

    @Test
    public void batchInsertInto() {
        User user = new User();
        user.setLoginId("ibit_tech@aliyun.com");
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        user.setType(1);

        User user2 = new User();
        user2.setEmail("ibittech@ibit.tech");
        user2.setType(2);
        SqlParams sqlParams = DaoUtils.batchInsertInto(Arrays.asList(user, user2), Arrays.asList(UserProperties.email, UserProperties.mobilePhone, UserProperties.type));
        assertParamsEquals("INSERT INTO user(email, mobile_phone, type) VALUES(?, ?, ?), (?, ?, ?)",
                Arrays.asList("email", "ibit_tech@aliyun.com", "mobile_phone", "188", "type", 1, "email", "ibittech@ibit.tech", "mobile_phone", null, "type", 2), sqlParams);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        user.setType(1);
        user.setUserId(1);

        SqlParams sqlParams = DaoUtils.updateById(user);
        assertParamsEquals( "UPDATE user u SET u.email = ?, u.mobile_phone = ?, u.type = ? WHERE u.user_id = ?",
                Arrays.asList("u.email", "ibit_tech@aliyun.com", "u.mobile_phone", "188", "u.type", 1, "u.user_id", 1), sqlParams);

        sqlParams = DaoUtils.updateById(user, Arrays.asList(UserProperties.loginId, UserProperties.mobilePhone));
        assertParamsEquals( "UPDATE user u SET u.login_id = ?, u.mobile_phone = ? WHERE u.user_id = ?",
                Arrays.asList("u.login_id", null, "u.mobile_phone", "188", "u.user_id", 1), sqlParams);

        Organization org = new Organization();
        org.setType(1);
        org.setCityCode("0001");
        org.setName("广州");
        sqlParams = DaoUtils.updateById(org);
        assertParamsEquals( "UPDATE organization o SET o.type = ? WHERE o.city_code = ? AND o.name = ?",
                Arrays.asList("o.type", 1, "o.city_code", "0001", "o.name", "广州"), sqlParams);

    }

    //测试id为null的情况
    @Test
    public void updateById2() {
        User user = new User();
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        user.setType(1);

        thrown.expect(IdNullPointerException.class);
        thrown.expectMessage("Table(user)'s id(user_id) is null!");
        DaoUtils.updateById(user);
    }

    //多主键，某个主键为null
    @Test
    public void updateById3() {
        Organization org = new Organization();
        org.setType(1);
        org.setCityCode("0001");
        thrown.expect(IdNullPointerException.class);
        thrown.expectMessage("Table(organization)'s id(name) is null!");
        DaoUtils.updateById(org);
    }

    //某个不能为column设置为空
    @Test
    public void updateById4() {
        User user = new User();
        user.setUserId(1);

        thrown.expect(ColumnNullPointerException.class);
        thrown.expectMessage("Table(user)'s column(password) is null!");
        DaoUtils.updateById(user, Collections.singletonList(UserProperties.password));
    }


    @Test
    public void updateByIds() {

        User user = new User();
        user.setType(1);
        user.setPassword("12345678");
        SqlParams sqlParams = DaoUtils.updateByIds(user, Arrays.asList(1, 2, 3));
        assertParamsEquals( "UPDATE user u SET u.password = ?, u.type = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList("u.password", "12345678", "u.type", 1, "u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sqlParams);

        sqlParams = DaoUtils.updateByIds(user, Collections.singletonList(1));
        assertParamsEquals( "UPDATE user u SET u.password = ?, u.type = ? WHERE u.user_id = ?",
                Arrays.asList("u.password", "12345678", "u.type", 1, "u.user_id", 1), sqlParams);

        sqlParams = DaoUtils.updateByIds(user, Collections.singletonList(UserProperties.type), Arrays.asList(1, 2, 3));
        assertParamsEquals( "UPDATE user u SET u.type = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList("u.type", 1, "u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sqlParams);


        sqlParams = DaoUtils.updateByIds(user, Arrays.asList(UserProperties.type, UserProperties.password), Arrays.asList(1, 2, 3));
        assertParamsEquals( "UPDATE user u SET u.type = ?, u.password = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList("u.type", 1, "u.password", "12345678", "u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sqlParams);

        sqlParams = DaoUtils.updateByIds(user, Arrays.asList(UserProperties.type, UserProperties.password, UserProperties.loginId), Arrays.asList(1, 2, 3));
        assertParamsEquals( "UPDATE user u SET u.type = ?, u.password = ?, u.login_id = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList("u.type", 1, "u.password", "12345678", "u.login_id", null, "u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sqlParams);
    }


    //测试没有id值的情况
    @Test
    public void updateByIds1() {
        User user = new User();
        user.setType(1);
        thrown.expect(IdValueNotFoundException.class);
        thrown.expectMessage("Id value not found");
        DaoUtils.updateByIds(user, Collections.emptyList());
    }

    //测试设置某个不允许为null的列为null
    @Test
    public void updateByIds2() {
        User user = new User();
        user.setType(1);
        thrown.expect(ColumnNullPointerException.class);
        thrown.expectMessage("Table(user)'s column(password) is null!");
        DaoUtils.updateByIds(user, Collections.singletonList(UserProperties.password), Arrays.asList(1, 2));
    }

    @Test
    public void updateByMultiIds() {
        User user = new User();
        user.setType(1);
        user.setPassword("12345678");

        UserMultiId uKey1 = new UserMultiId(1);
        UserMultiId uKey2 = new UserMultiId(2);
        UserMultiId uKey3 = new UserMultiId(3);
        SqlParams sqlParams = DaoUtils.updateByMultiIds(user, Arrays.asList(uKey1, uKey2, uKey3));
        assertParamsEquals( "UPDATE user u SET u.password = ?, u.type = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList("u.password", "12345678", "u.type", 1, "u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sqlParams);

        sqlParams = DaoUtils.updateByMultiIds(user, Collections.singletonList(uKey1));
        assertParamsEquals( "UPDATE user u SET u.password = ?, u.type = ? WHERE u.user_id = ?",
                Arrays.asList("u.password", "12345678", "u.type", 1, "u.user_id", 1), sqlParams);

        sqlParams = DaoUtils.updateByMultiIds(user, Collections.singletonList(UserProperties.type),
                Arrays.asList(uKey1, uKey2, uKey3));
        assertParamsEquals( "UPDATE user u SET u.type = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList("u.type", 1, "u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sqlParams);


        sqlParams = DaoUtils.updateByMultiIds(user, Arrays.asList(UserProperties.type, UserProperties.password),
                Arrays.asList(uKey1, uKey2, uKey3));
        assertParamsEquals( "UPDATE user u SET u.type = ?, u.password = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList("u.type", 1, "u.password", "12345678", "u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sqlParams);

        sqlParams = DaoUtils.updateByMultiIds(user, Arrays.asList(UserProperties.type, UserProperties.password, UserProperties.loginId),
                Arrays.asList(uKey1, uKey2, uKey3));
        assertParamsEquals( "UPDATE user u SET u.type = ?, u.password = ?, u.login_id = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList("u.type", 1, "u.password", "12345678", "u.login_id", null, "u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sqlParams);


        Organization org = new Organization();
        org.setType(1);

        OrganizationMultiId oKey1 = new OrganizationMultiId("0001", "广州市");
        OrganizationMultiId oKey2 = new OrganizationMultiId("0002", "深圳市");
        OrganizationMultiId oKey3 = new OrganizationMultiId("0003", "中山市");

        sqlParams = DaoUtils.updateByMultiIds(org, Collections.singletonList(oKey1));
        assertParamsEquals( "UPDATE organization o SET o.type = ? WHERE (o.city_code = ? AND o.name = ?)",
                Arrays.asList("o.type", 1, "o.city_code", "0001", "o.name", "广州市"), sqlParams);

        sqlParams = DaoUtils.updateByMultiIds(org,
                Arrays.asList(oKey1, oKey2, oKey3));
        assertParamsEquals( "UPDATE organization o SET o.type = ? WHERE (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?)",
                Arrays.asList("o.type", 1, "o.city_code", "0001", "o.name", "广州市", "o.city_code", "0002", "o.name", "深圳市", "o.city_code", "0003", "o.name", "中山市"), sqlParams);

        sqlParams = DaoUtils.updateByMultiIds(org, Collections.singletonList(OrganizationProperties.type),
                Collections.singletonList(oKey1));
        assertParamsEquals( "UPDATE organization o SET o.type = ? WHERE (o.city_code = ? AND o.name = ?)",
                Arrays.asList("o.type", 1, "o.city_code", "0001", "o.name", "广州市"), sqlParams);

        sqlParams = DaoUtils.updateByMultiIds(org, Arrays.asList(OrganizationProperties.phone, OrganizationProperties.type),
                Collections.singletonList(oKey1));
        assertParamsEquals( "UPDATE organization o SET o.phone = ?, o.type = ? WHERE (o.city_code = ? AND o.name = ?)",
                Arrays.asList("o.phone", null, "o.type", 1, "o.city_code", "0001", "o.name", "广州市"), sqlParams);

        sqlParams = DaoUtils.updateByMultiIds(org, Collections.singletonList(OrganizationProperties.type)
                , Arrays.asList(oKey1, oKey2, oKey3));
        assertParamsEquals( "UPDATE organization o SET o.type = ? WHERE (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?)",
                Arrays.asList("o.type", 1, "o.city_code", "0001", "o.name", "广州市", "o.city_code", "0002", "o.name", "深圳市", "o.city_code", "0003", "o.name", "中山市"), sqlParams);

        sqlParams = DaoUtils.updateByMultiIds(org, Arrays.asList(OrganizationProperties.phone, OrganizationProperties.type)
                , Arrays.asList(oKey1, oKey2, oKey3));
        assertParamsEquals( "UPDATE organization o SET o.phone = ?, o.type = ? WHERE (o.city_code = ? AND o.name = ?) "
                        + "OR (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?)",
                Arrays.asList("o.phone", null, "o.type", 1, "o.city_code", "0001", "o.name", "广州市", "o.city_code", "0002", "o.name", "深圳市", "o.city_code", "0003", "o.name", "中山市"), sqlParams);

    }

    //测试没有id值的情况
    @Test
    public void updateByMultiIds1() {
        Organization org = new Organization();
        org.setType(1);
        thrown.expect(IdValueNotFoundException.class);
        thrown.expectMessage("Id value not found");
        DaoUtils.updateByMultiIds(org, Collections.emptyList());
    }

    //测试设置某个不允许为null的列为null
    @Test
    public void updateByMultiIds2() {
        Organization org = new Organization();

        OrganizationMultiId oKey1 = new OrganizationMultiId("0001", "广州市");
        OrganizationMultiId oKey2 = new OrganizationMultiId("0002", "深圳市");
        thrown.expect(ColumnNullPointerException.class);
        thrown.expectMessage("Table(organization)'s column(type) is null!");
        DaoUtils.updateByMultiIds(org, Collections.singletonList(OrganizationProperties.type), Arrays.asList(oKey1, oKey2));
    }


}