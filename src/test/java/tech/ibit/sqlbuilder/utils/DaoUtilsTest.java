package tech.ibit.sqlbuilder.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.exception.SqlException;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author IBIT程序猿
 * @version 1.0
 */
public class DaoUtilsTest extends CommonTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void getByIds() {
        PrepareStatement prepareStatement = DaoUtils.getByIds(User.class, Arrays.asList(1, 2, 3));
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id IN(?, ?, ?) LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3),
                        getStartColumn().value(0),
                        getLimitColumn().value(3)
                ), prepareStatement);

        prepareStatement = DaoUtils.getByIds(User.class, Collections.singletonList(1));
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ), prepareStatement);
    }

    @Test
    public void getById() {
        PrepareStatement prepareStatement = DaoUtils.getById(User.class, 1);
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ), prepareStatement);
    }

    @Test
    public void getByMultiIds() {
        UserMultiId uKey1 = new UserMultiId(1);
        UserMultiId uKey2 = new UserMultiId(2);
        PrepareStatement prepareStatement = DaoUtils.getByMultiIds(User.class, Arrays.asList(uKey1, uKey2));
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id IN(?, ?) LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        getStartColumn().value(0),
                        getLimitColumn().value(2)
                ), prepareStatement);


        OrganizationMultiId oKey1 = new OrganizationMultiId("001", "001");
        OrganizationMultiId oKey2 = new OrganizationMultiId("001", "002");
        prepareStatement = DaoUtils.getByMultiIds(Organization.class, Arrays.asList(oKey1, oKey2));
        assertPrepareStatementEquals(
                "SELECT o.city_code, o.name, o.type, o.phone FROM organization o WHERE (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?) LIMIT ?, ?",
                Arrays.asList(
                        OrganizationProperties.cityCode.value("001"),
                        OrganizationProperties.name.value("001"),
                        OrganizationProperties.cityCode.value("001"),
                        OrganizationProperties.name.value("002"),
                        getStartColumn().value(0),
                        getLimitColumn().value(2)
                ),
                prepareStatement);
    }

    @Test
    public void getByMultiId() {
        UserMultiId uKey1 = new UserMultiId(1);
        PrepareStatement prepareStatement = DaoUtils.getByMultiId(User.class, uKey1);
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.login_id, u.email, u.password, u.mobile_phone, u.type FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ), prepareStatement);

        OrganizationMultiId oKey1 = new OrganizationMultiId("001", "001");
        prepareStatement = DaoUtils.getByMultiId(Organization.class, oKey1);
        assertPrepareStatementEquals(
                "SELECT o.city_code, o.name, o.type, o.phone FROM organization o WHERE (o.city_code = ? AND o.name = ?) LIMIT ?, ?",
                Arrays.asList(
                        OrganizationProperties.cityCode.value("001"),
                        OrganizationProperties.name.value("001"),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ), prepareStatement);
    }

    @Test
    public void deleteByIds() {
        PrepareStatement prepareStatement = DaoUtils.deleteByIds(User.class, Arrays.asList(1, 2));
        assertPrepareStatementEquals(
                "DELETE FROM user WHERE user_id IN(?, ?)",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2)
                ), prepareStatement);

        prepareStatement = DaoUtils.deleteByIds(User.class, Collections.singletonList(1));
        assertPrepareStatementEquals(
                "DELETE FROM user WHERE user_id = ?",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ), prepareStatement);
    }

    @Test
    public void deleteById() {
        PrepareStatement prepareStatement = DaoUtils.deleteById(User.class, 1);
        assertPrepareStatementEquals(
                "DELETE FROM user WHERE user_id = ?",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ), prepareStatement);
    }

    @Test
    public void deleteByMultiIds() {
        UserMultiId uKey1 = new UserMultiId(1);
        UserMultiId uKey2 = new UserMultiId(2);

        PrepareStatement prepareStatement = DaoUtils.deleteByMultiIds(Collections.singletonList(uKey1));
        assertPrepareStatementEquals(
                "DELETE FROM user WHERE user_id = ?",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ), prepareStatement);

        prepareStatement = DaoUtils.deleteByMultiIds(Arrays.asList(uKey1, uKey2));
        assertPrepareStatementEquals(
                "DELETE FROM user WHERE user_id IN(?, ?)",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2)
                ), prepareStatement);

        OrganizationMultiId oKey1 = new OrganizationMultiId("001", "001");
        OrganizationMultiId oKey2 = new OrganizationMultiId("001", "002");

        prepareStatement = DaoUtils.deleteByMultiIds(Collections.singletonList(oKey1));
        assertPrepareStatementEquals(
                "DELETE FROM organization WHERE (city_code = ? AND name = ?)",
                Arrays.asList(
                        OrganizationProperties.cityCode.value("001"),
                        OrganizationProperties.name.value("001")
                ), prepareStatement);

        prepareStatement = DaoUtils.deleteByMultiIds(Arrays.asList(oKey1, oKey2));
        assertPrepareStatementEquals(
                "DELETE FROM organization WHERE (city_code = ? AND name = ?) OR (city_code = ? AND name = ?)",
                Arrays.asList(
                        OrganizationProperties.cityCode.value("001"),
                        OrganizationProperties.name.value("001"),
                        OrganizationProperties.cityCode.value("001"),
                        OrganizationProperties.name.value("002")
                ), prepareStatement);
    }

    @Test
    public void deleteByMultiId() {
        UserMultiId uKey1 = new UserMultiId(1);
        PrepareStatement prepareStatement = DaoUtils.deleteByMultiId(uKey1);
        assertPrepareStatementEquals(
                "DELETE FROM user WHERE user_id = ?",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ), prepareStatement);

        OrganizationMultiId oKey1 = new OrganizationMultiId("001", "001");
        prepareStatement = DaoUtils.deleteByMultiId(oKey1);
        assertPrepareStatementEquals(
                "DELETE FROM organization WHERE (city_code = ? AND name = ?)",
                Arrays.asList(
                        OrganizationProperties.cityCode.value("001"),
                        OrganizationProperties.name.value("001")
                ), prepareStatement);
    }

    @Test
    public void insertInto() {
        User user = new User();
        user.setLoginId("ibit_tech@aliyun.com");
        user.setEmail("ibit_tech@aliyun.com");
        user.setPassword("12345678");
        user.setMobilePhone("188");
        user.setType(1);

        PrepareStatement prepareStatement = DaoUtils.insertInto(user);
        assertPrepareStatementEquals("INSERT INTO user(login_id, email, password, mobile_phone, type) VALUES(?, ?, ?, ?, ?)",
                Arrays.asList(
                        UserProperties.loginId.value("ibit_tech@aliyun.com"),
                        UserProperties.email.value("ibit_tech@aliyun.com"),
                        UserProperties.password.value("12345678"),
                        UserProperties.mobilePhone.value("188"),
                        UserProperties.type.value(1)
                ), prepareStatement);
    }

    @Test
    public void insertInto1() {
        User user = new User();
        thrown.expect(SqlException.class);
        thrown.expectMessage("Table(user)'s column(email) is null!");
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

        thrown.expect(SqlException.class);
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

        thrown.expect(SqlException.class);
        thrown.expectMessage("Table(user)'s column(password) is null!");
        DaoUtils.batchInsertInto(Arrays.asList(user, user2), Arrays.asList(UserProperties.email, UserProperties.mobilePhone, UserProperties.type));
    }

    @Test
    public void batchInsertInto2() {
        User user = new User();
        user.setLoginId("ibit_tech@aliyun.com");
        user.setEmail("ibit_tech@aliyun.com");
        user.setPassword("12345678");
        user.setMobilePhone("188");
        user.setType(1);

        User user2 = new User();
        user2.setEmail("ibittech@ibit.tech");
        user2.setPassword("12345609");
        user2.setMobilePhone("100");
        user2.setType(2);


        PrepareStatement prepareStatement = DaoUtils.batchInsertInto(Arrays.asList(user, user2),
                Arrays.asList(UserProperties.email, UserProperties.mobilePhone, UserProperties.type, UserProperties.password));
        assertPrepareStatementEquals(
                "INSERT INTO user(email, mobile_phone, type, password) VALUES(?, ?, ?, ?), (?, ?, ?, ?)",
                Arrays.asList(
                        UserProperties.email.value("ibit_tech@aliyun.com"),
                        UserProperties.mobilePhone.value("188"),
                        UserProperties.type.value(1),
                        UserProperties.password.value("12345678"),
                        UserProperties.email.value("ibittech@ibit.tech"),
                        UserProperties.mobilePhone.value("100"),
                        UserProperties.type.value(2),
                        UserProperties.password.value("12345609")
                ), prepareStatement);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        user.setType(1);
        user.setUserId(1);

        PrepareStatement prepareStatement = DaoUtils.updateById(user);
        assertPrepareStatementEquals(
                "UPDATE user u SET u.email = ?, u.mobile_phone = ?, u.type = ? WHERE u.user_id = ?",
                Arrays.asList(
                        UserProperties.email.value("ibit_tech@aliyun.com"),
                        UserProperties.mobilePhone.value("188"),
                        UserProperties.type.value(1),
                        UserProperties.userId.value(1)
                ), prepareStatement);

        prepareStatement = DaoUtils.updateById(user, Arrays.asList(UserProperties.loginId, UserProperties.mobilePhone));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.login_id = ?, u.mobile_phone = ? WHERE u.user_id = ?",
                Arrays.asList(
                        UserProperties.loginId.value(null),
                        UserProperties.mobilePhone.value("188"),
                        UserProperties.userId.value(1)
                ), prepareStatement);

        Organization org = new Organization();
        org.setType(1);
        org.setCityCode("0001");
        org.setName("广州");
        prepareStatement = DaoUtils.updateById(org);
        assertPrepareStatementEquals("UPDATE organization o SET o.type = ? WHERE o.city_code = ? AND o.name = ?",
                Arrays.asList(
                        OrganizationProperties.type.value(1),
                        OrganizationProperties.cityCode.value("0001"),
                        OrganizationProperties.name.value("广州")
                ), prepareStatement);

    }

    //测试id为null的情况
    @Test
    public void updateById2() {
        User user = new User();
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        user.setType(1);

        thrown.expect(SqlException.class);
        thrown.expectMessage("Table(user)'s id(user_id) is null!");
        DaoUtils.updateById(user);
    }

    //多主键，某个主键为null
    @Test
    public void updateById3() {
        Organization org = new Organization();
        org.setType(1);
        org.setCityCode("0001");
        thrown.expect(SqlException.class);
        thrown.expectMessage("Table(organization)'s id(name) is null!");
        DaoUtils.updateById(org);
    }

    //某个不能为column设置为空
    @Test
    public void updateById4() {
        User user = new User();
        user.setUserId(1);

        thrown.expect(SqlException.class);
        thrown.expectMessage("Table(user)'s column(password) is null!");
        DaoUtils.updateById(user, Collections.singletonList(UserProperties.password));
    }


    @Test
    public void updateByIds() {

        User user = new User();
        user.setType(1);
        user.setPassword("12345678");
        PrepareStatement prepareStatement = DaoUtils.updateByIds(user, Arrays.asList(1, 2, 3));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.password = ?, u.type = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList(
                        UserProperties.password.value("12345678"),
                        UserProperties.type.value("1"),
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByIds(user, Collections.singletonList(1));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.password = ?, u.type = ? WHERE u.user_id = ?",
                Arrays.asList(
                        UserProperties.password.value("12345678"),
                        UserProperties.type.value("1"),
                        UserProperties.userId.value(1)
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByIds(user, Collections.singletonList(UserProperties.type), Arrays.asList(1, 2, 3));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.type = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList(
                        UserProperties.type.value("1"),
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ), prepareStatement);


        prepareStatement = DaoUtils.updateByIds(user, Arrays.asList(UserProperties.type, UserProperties.password), Arrays.asList(1, 2, 3));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.type = ?, u.password = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList(
                        UserProperties.type.value(1),
                        UserProperties.password.value("12345678"),
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByIds(user, Arrays.asList(UserProperties.type, UserProperties.password, UserProperties.loginId), Arrays.asList(1, 2, 3));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.type = ?, u.password = ?, u.login_id = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList(
                        UserProperties.type.value(1),
                        UserProperties.password.value("12345678"),
                        UserProperties.loginId.value(null),
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ), prepareStatement);
    }


    //测试没有id值的情况
    @Test
    public void updateByIds1() {
        User user = new User();
        user.setType(1);
        thrown.expect(SqlException.class);
        thrown.expectMessage("Id value not found");
        DaoUtils.updateByIds(user, Collections.emptyList());
    }

    //测试设置某个不允许为null的列为null
    @Test
    public void updateByIds2() {
        User user = new User();
        user.setType(1);
        thrown.expect(SqlException.class);
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
        PrepareStatement prepareStatement = DaoUtils.updateByMultiIds(user, Arrays.asList(uKey1, uKey2, uKey3));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.password = ?, u.type = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList(
                        UserProperties.password.value("12345678"),
                        UserProperties.type.value(1),
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByMultiIds(user, Collections.singletonList(uKey1));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.password = ?, u.type = ? WHERE u.user_id = ?",
                Arrays.asList(
                        UserProperties.password.value("12345678"),
                        UserProperties.type.value(1),
                        UserProperties.userId.value(1)
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByMultiIds(user, Collections.singletonList(UserProperties.type),
                Arrays.asList(uKey1, uKey2, uKey3));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.type = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList(
                        UserProperties.type.value(1),
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ), prepareStatement);


        prepareStatement = DaoUtils.updateByMultiIds(user, Arrays.asList(UserProperties.type, UserProperties.password),
                Arrays.asList(uKey1, uKey2, uKey3));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.type = ?, u.password = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList(
                        UserProperties.type.value(1),
                        UserProperties.password.value("12345678"),
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByMultiIds(user, Arrays.asList(UserProperties.type, UserProperties.password, UserProperties.loginId),
                Arrays.asList(uKey1, uKey2, uKey3));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.type = ?, u.password = ?, u.login_id = ? WHERE u.user_id IN(?, ?, ?)",
                Arrays.asList(
                        UserProperties.type.value(1),
                        UserProperties.password.value("12345678"),
                        UserProperties.loginId.value(null),
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ), prepareStatement);


        Organization org = new Organization();
        org.setType(1);

        OrganizationMultiId oKey1 = new OrganizationMultiId("0001", "广州市");
        OrganizationMultiId oKey2 = new OrganizationMultiId("0002", "深圳市");
        OrganizationMultiId oKey3 = new OrganizationMultiId("0003", "中山市");

        prepareStatement = DaoUtils.updateByMultiIds(org, Collections.singletonList(oKey1));
        assertPrepareStatementEquals(
                "UPDATE organization o SET o.type = ? WHERE (o.city_code = ? AND o.name = ?)",
                Arrays.asList(
                        OrganizationProperties.type.value(1),
                        OrganizationProperties.cityCode.value("0001"),
                        OrganizationProperties.name.value("广州市")
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByMultiIds(org,
                Arrays.asList(oKey1, oKey2, oKey3));
        assertPrepareStatementEquals(
                "UPDATE organization o SET o.type = ? WHERE (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?)",
                Arrays.asList(
                        OrganizationProperties.type.value(1),
                        OrganizationProperties.cityCode.value("0001"),
                        OrganizationProperties.name.value("广州市"),
                        OrganizationProperties.cityCode.value("0002"),
                        OrganizationProperties.name.value("深圳市"),
                        OrganizationProperties.cityCode.value("0003"),
                        OrganizationProperties.name.value("中山市")
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByMultiIds(org, Collections.singletonList(OrganizationProperties.type),
                Collections.singletonList(oKey1));
        assertPrepareStatementEquals(
                "UPDATE organization o SET o.type = ? WHERE (o.city_code = ? AND o.name = ?)",
                Arrays.asList(
                        OrganizationProperties.type.value(1),
                        OrganizationProperties.cityCode.value("0001"),
                        OrganizationProperties.name.value("广州市")
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByMultiIds(org, Arrays.asList(OrganizationProperties.phone, OrganizationProperties.type),
                Collections.singletonList(oKey1));
        assertPrepareStatementEquals(
                "UPDATE organization o SET o.phone = ?, o.type = ? WHERE (o.city_code = ? AND o.name = ?)",
                Arrays.asList(
                        OrganizationProperties.phone.value(null),
                        OrganizationProperties.type.value(1),
                        OrganizationProperties.cityCode.value("0001"),
                        OrganizationProperties.name.value("广州市")
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByMultiIds(org, Collections.singletonList(OrganizationProperties.type)
                , Arrays.asList(oKey1, oKey2, oKey3));
        assertPrepareStatementEquals("UPDATE organization o SET o.type = ? WHERE (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?)",
                Arrays.asList(
                        OrganizationProperties.type.value(1),
                        OrganizationProperties.cityCode.value("0001"),
                        OrganizationProperties.name.value("广州市"),
                        OrganizationProperties.cityCode.value("0002"),
                        OrganizationProperties.name.value("深圳市"),
                        OrganizationProperties.cityCode.value("0003"),
                        OrganizationProperties.name.value("中山市")
                ), prepareStatement);

        prepareStatement = DaoUtils.updateByMultiIds(org, Arrays.asList(OrganizationProperties.phone, OrganizationProperties.type)
                , Arrays.asList(oKey1, oKey2, oKey3));
        assertPrepareStatementEquals("UPDATE organization o SET o.phone = ?, o.type = ? WHERE (o.city_code = ? AND o.name = ?) "
                        + "OR (o.city_code = ? AND o.name = ?) OR (o.city_code = ? AND o.name = ?)",
                Arrays.asList(
                        OrganizationProperties.phone.value(null),
                        OrganizationProperties.type.value(1),
                        OrganizationProperties.cityCode.value("0001"),
                        OrganizationProperties.name.value("广州市"),
                        OrganizationProperties.cityCode.value("0002"),
                        OrganizationProperties.name.value("深圳市"),
                        OrganizationProperties.cityCode.value("0003"),
                        OrganizationProperties.name.value("中山市")
                ), prepareStatement);

    }

    //测试没有id值的情况
    @Test
    public void updateByMultiIds1() {
        Organization org = new Organization();
        org.setType(1);
        thrown.expect(SqlException.class);
        thrown.expectMessage("Id value not found");
        DaoUtils.updateByMultiIds(org, Collections.emptyList());
    }

    //测试设置某个不允许为null的列为null
    @Test
    public void updateByMultiIds2() {
        Organization org = new Organization();

        OrganizationMultiId oKey1 = new OrganizationMultiId("0001", "广州市");
        OrganizationMultiId oKey2 = new OrganizationMultiId("0002", "深圳市");
        thrown.expect(SqlException.class);
        thrown.expectMessage("Table(organization)'s column(type) is null!");
        DaoUtils.updateByMultiIds(org, Collections.singletonList(OrganizationProperties.type), Arrays.asList(oKey1, oKey2));
    }


}