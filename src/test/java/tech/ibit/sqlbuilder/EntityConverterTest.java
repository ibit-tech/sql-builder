package tech.ibit.sqlbuilder;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author IBIT TECH
 * @version 1.0
 */
public class EntityConverterTest {
    @Test
    public void getTableColumns() {
        TableColumnInfo entity = EntityConverter.getTableColumns(User.class);
        assert entity != null;
        assertColumns(entity.getColumns(),
                Arrays.asList(UserProperties.userId, UserProperties.loginId, UserProperties.email, UserProperties.password, UserProperties.mobilePhone, UserProperties.type));
        assertColumns(entity.getIds(), Collections.singletonList(UserProperties.userId));
        Assert.assertEquals(entity.getTable(), new Table("user", "u"));
    }

    private void assertColumns(List<Column> actualColumns, List<Column> expectedColumns) {
        assertEquals(actualColumns.size(), expectedColumns.size());
        for (int i = 0; i < actualColumns.size(); i++) {
            assertEquals(expectedColumns.get(i), actualColumns.get(i));
        }
    }

    @Test
    public void getColumns() {
        List<Column> columns = EntityConverter.getColumns(User.class);
        assertColumns(columns, Arrays.asList(UserProperties.userId, UserProperties.loginId, UserProperties.email, UserProperties.password, UserProperties.mobilePhone, UserProperties.type));
    }

    @Test
    public void getUpdateColumns() {
        List<Column> columns = EntityConverter.getUpdateColumns(User.class);
        assertColumns(columns, Arrays.asList(UserProperties.loginId, UserProperties.email, UserProperties.password, UserProperties.mobilePhone, UserProperties.type));

        columns = EntityConverter.getUpdateColumns(User.class, Arrays.asList(UserProperties.email, UserProperties.password));
        assertColumns(columns, Arrays.asList(UserProperties.loginId, UserProperties.mobilePhone, UserProperties.type));
    }

    @Test
    public void getTableColumnValues() {
        User user = getUser1();

        TableColumnSetValues entity = EntityConverter.getTableColumnValues(user, false);

        List<ColumnSetValue> columnSetValues = entity.getColumnValues();
        assertEquals(4, columnSetValues.size());
        assertColumnSetValue(columnSetValues.get(0), UserProperties.userId, 1, true, false);
        assertColumnSetValue(columnSetValues.get(1), UserProperties.loginId, "ibit_tech@aliyun.com", false, true);
        assertColumnSetValue(columnSetValues.get(2), UserProperties.email, "ibit_tech@aliyun.com", false, false);
        assertColumnSetValue(columnSetValues.get(3), UserProperties.mobilePhone, "188", false, false);

        entity = EntityConverter.getTableColumnValues(user, Arrays.asList(UserProperties.loginId, UserProperties.email, UserProperties.type));
        columnSetValues = entity.getColumnValues();
        assertEquals(3, columnSetValues.size());
        assertColumnSetValue(columnSetValues.get(0), UserProperties.loginId, "ibit_tech@aliyun.com", false, true);
        assertColumnSetValue(columnSetValues.get(1), UserProperties.email, "ibit_tech@aliyun.com", false, false);
        assertColumnSetValue(columnSetValues.get(2), UserProperties.type, null, false, false);


        entity = EntityConverter.getTableColumnValues(user, true);
        columnSetValues = entity.getColumnValues();
        assertEquals(6, columnSetValues.size());
        assertColumnSetValue(columnSetValues.get(0), UserProperties.userId, 1, true, false);
        assertColumnSetValue(columnSetValues.get(1), UserProperties.loginId, "ibit_tech@aliyun.com", false, true);
        assertColumnSetValue(columnSetValues.get(2), UserProperties.email, "ibit_tech@aliyun.com", false, false);
        assertColumnSetValue(columnSetValues.get(3), UserProperties.password, null, false, false);
        assertColumnSetValue(columnSetValues.get(4), UserProperties.mobilePhone, "188", false, false);
        assertColumnSetValue(columnSetValues.get(5), UserProperties.type, null, false, false);
    }


    @Test
    public void getTableColumnValuesList() throws Exception {

        User user1 = getUser1();
        User user2 = getUser2();

        List<TableColumnSetValues> entities = EntityConverter.getTableColumnValuesList(Arrays.asList(user1, user2), false);
        assertEquals(entities.size(), 2);

        List<ColumnSetValue> columnSetValues = entities.get(0).getColumnValues();
        assertColumnSetValue(columnSetValues.get(0), UserProperties.userId, 1, true, false);
        assertColumnSetValue(columnSetValues.get(1), UserProperties.loginId, "ibit_tech@aliyun.com", false, true);
        assertColumnSetValue(columnSetValues.get(2), UserProperties.email, "ibit_tech@aliyun.com", false, false);
        assertColumnSetValue(columnSetValues.get(3), UserProperties.mobilePhone, "188", false, false);


        columnSetValues = entities.get(1).getColumnValues();
        assertColumnSetValue(columnSetValues.get(0), UserProperties.userId, 2, true, false);
        assertColumnSetValue(columnSetValues.get(1), UserProperties.loginId, "xiao2@ibit.tech", false, true);
        assertColumnSetValue(columnSetValues.get(2), UserProperties.email, "xiao2@ibit.tech", false, false);
        assertColumnSetValue(columnSetValues.get(3), UserProperties.mobilePhone, "199", false, false);

    }


    private void assertColumnSetValue(ColumnSetValue columnSetValue, Column column, Object value
            , boolean isId, boolean nullable) {
        assertEquals(column, columnSetValue.getColumn());
        assertEquals(value, columnSetValue.getValue());
        assertEquals(isId, columnSetValue.isId());
        assertEquals(nullable, columnSetValue.isNullable());
    }

    private User getUser1() {
        User user = new User();
        user.setUserId(1);
        user.setLoginId("ibit_tech@aliyun.com");
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        return user;
    }

    public User getUser2() {
        User user = new User();
        user.setUserId(2);
        user.setLoginId("xiao2@ibit.tech");
        user.setEmail("xiao2@ibit.tech");
        user.setMobilePhone("199");
        return user;
    }

    @Test
    public void getAutoIncrementIdSetterMethod() {
        AutoIncrementIdSetterMethod idSetterMethod = EntityConverter.getAutoIncrementIdSetterMethod(User.class);
        assert null != idSetterMethod;
        assertEquals(idSetterMethod.getType(), Integer.class);
        assertEquals(idSetterMethod.getMethod().getName(), "setUserId");
    }

    @Test
    public void copyColumns() {
        User user = new User();
        user.setUserId(1);
        user.setLoginId("ibit_tech@aliyun.com");
        user.setEmail("ibit_tech@aliyun.com");
        user.setMobilePhone("188");
        user.setPassword("12345678");

        UserPO po = EntityConverter.copyColumns(user, UserPO.class);
        assertEquals(user.getEmail(), po.getEmail());
        assertEquals(user.getLoginId(), po.getLoginId());
        assertEquals(user.getUserId(), po.getUserId());
        assertEquals(user.getMobilePhone(), po.getMobilePhone());


        User user1 = new User();
        user1.setUserId(2);
        user1.setLoginId("ben@ibit.tech");
        user1.setEmail("ben@ibit.tech");
        user1.setMobilePhone("199");
        user1.setPassword("12345678");

        List<UserPO> poList = EntityConverter.copyColumns(Arrays.asList(user, user1), UserPO.class);
        assertEquals(user.getEmail(), poList.get(0).getEmail());
        assertEquals(user.getLoginId(), poList.get(0).getLoginId());
        assertEquals(user.getUserId(), poList.get(0).getUserId());
        assertEquals(user.getMobilePhone(), poList.get(0).getMobilePhone());

        assertEquals(user1.getEmail(), poList.get(1).getEmail());
        assertEquals(user1.getLoginId(), poList.get(1).getLoginId());
        assertEquals(user1.getUserId(), poList.get(1).getUserId());
        assertEquals(user1.getMobilePhone(), poList.get(1).getMobilePhone());

    }

}