package tech.ibit.sqlbuilder.support;

import org.junit.Test;
import tech.ibit.sqlbuilder.DefaultCriteriaItem;
import tech.ibit.sqlbuilder.FlagCriteriaItem;
import tech.ibit.sqlbuilder.PrepareStatement;
import tech.ibit.sqlbuilder.demo.entity.property.OrganizationProperties;
import tech.ibit.sqlbuilder.demo.entity.property.UserProperties;
import tech.ibit.sqlbuilder.enums.OperatorEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 列默认方法测试用例
 *
 * @author iBit程序猿
 * @version 2.0
 */
public class IColumnCriteriaItemSupportTest {


    @Test
    public void isNull() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.isNull();
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.IS_NULL, item.getOperator());
        assertNull(item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id IS NULL", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id IS NULL", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

    }

    @Test
    public void isNotNull() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.isNotNull();
        assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), OperatorEnum.IS_NOT_NULL);
        assertNull(item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id IS NOT NULL", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id IS NOT NULL", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

    }


    @Test
    public void isEmpty() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.isEmpty();
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.IS_EMPTY, item.getOperator());
        assertNull(item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id = ''", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id = ''", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

    }

    @Test
    public void isNotEmpty() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.isNotEmpty();
        assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), OperatorEnum.IS_NOT_EMPTY);
        assertNull(item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id <> ''", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <> ''", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

    }

    @Test
    public void eq() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.eq(1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.EQ, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id = ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id = ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

    }

    @Test
    public void eq1() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.orgId.eq(OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(OperatorEnum.EQ, item.getOperator());
        assertEquals(OrganizationProperties.orgId, item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("org_id = org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.org_id = o.org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

    }

    @Test
    public void neq() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.neq(1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.NEQ, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id <> ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <> ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

    }

    @Test
    public void neq1() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.orgId.neq(OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(OperatorEnum.NEQ, item.getOperator());
        assertEquals(OrganizationProperties.orgId, item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("org_id <> org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.org_id <> o.org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

    }

    @Test
    public void gt() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.gt(1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.GT, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id > ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id > ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

    }

    @Test
    public void gt1() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.orgId.gt(OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(OperatorEnum.GT, item.getOperator());
        assertEquals(OrganizationProperties.orgId, item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("org_id > org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.org_id > o.org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());
    }

    @Test
    public void egt() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.egt(1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.EGT, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id >= ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id >= ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

    }

    @Test
    public void egt1() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.orgId.egt(OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(OperatorEnum.EGT, item.getOperator());
        assertEquals(OrganizationProperties.orgId, item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("org_id >= org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.org_id >= o.org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());
    }

    @Test
    public void lt() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.lt(1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.LT, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id < ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id < ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

    }

    @Test
    public void lt1() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.orgId.lt(OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(OperatorEnum.LT, item.getOperator());
        assertEquals(OrganizationProperties.orgId, item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("org_id < org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.org_id < o.org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());


    }

    @Test
    public void elt() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.elt(1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.ELT, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id <= ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <= ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size(), 1);
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
    }

    @Test
    public void elt1() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.orgId.elt(OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(OperatorEnum.ELT, item.getOperator());
        assertEquals(OrganizationProperties.orgId, item.getSecondColumn());
        assertNull(item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("org_id <= org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());

        statement = item.getPrepareStatement(true);
        assertEquals("u.org_id <= o.org_id", statement.getPrepareSql());
        assertTrue(statement.getValues().isEmpty());
    }

    @Test
    public void in() {

        //value more than one
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.in(Arrays.asList(1, 2));
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.IN, item.getOperator());
        assertNull(item.getSecondColumn());
        assertTrue(item.getValue() instanceof List<?>);
        assertEquals(1, ((List<?>) item.getValue()).get(0));
        assertEquals(2, ((List<?>) item.getValue()).get(1));
        assertEquals(2, ((List<?>) item.getValue()).size());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id IN(?, ?)", statement.getPrepareSql());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
        assertEquals(UserProperties.userId.value(2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id IN(?, ?)", statement.getPrepareSql());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
        assertEquals(UserProperties.userId.value(2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());


        //Only one
        item = (DefaultCriteriaItem) UserProperties.userId.in(Collections.singletonList(1));
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.EQ, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        statement = item.getPrepareStatement();
        assertEquals("user_id = ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id = ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

    }

    @Test
    public void notIn() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.notIn(Arrays.asList(1, 2));
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.NOT_IN, item.getOperator());
        assertNull(item.getSecondColumn());
        assertTrue(item.getValue() instanceof List<?>);
        assertEquals(((List<?>) item.getValue()).get(0), 1);
        assertEquals(((List<?>) item.getValue()).get(1), 2);
        assertEquals(((List<?>) item.getValue()).size(), 2);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id NOT IN(?, ?)", statement.getPrepareSql());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
        assertEquals(UserProperties.userId.value(2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id NOT IN(?, ?)", statement.getPrepareSql());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
        assertEquals(UserProperties.userId.value(2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        //Only one
        item = (DefaultCriteriaItem) UserProperties.userId.notIn(Collections.singletonList(1));
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.NEQ, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        statement = item.getPrepareStatement();
        assertEquals("user_id <> ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <> ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));

    }

    @Test
    public void between() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.between(1, 2);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.BETWEEN, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertEquals(2, item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
        assertEquals(UserProperties.userId.value(2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
        assertEquals(UserProperties.userId.value(2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

    }

    @Test
    public void notBetween() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.userId.notBetween(1, 2);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(OperatorEnum.NOT_BETWEEN, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertEquals(2, item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id NOT BETWEEN ? AND ?", statement.getPrepareSql());

        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
        assertEquals(UserProperties.userId.value(2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id NOT BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(UserProperties.userId.value(1), statement.getValues().get(0));
        assertEquals(UserProperties.userId.value(2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());
    }

    @Test
    public void like() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.name.like("小%");
        assertEquals(UserProperties.name, item.getColumn());
        assertEquals(OperatorEnum.LIKE, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals("小%", item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("name LIKE ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.name.value("小%"), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.name LIKE ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.name.value("小%"), statement.getValues().get(0));
    }

    @Test
    public void notLike() {
        DefaultCriteriaItem item = (DefaultCriteriaItem) UserProperties.name.notLike("小%");
        assertEquals(UserProperties.name, item.getColumn());
        assertEquals(OperatorEnum.NOT_LIKE, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals("小%", item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("name NOT LIKE ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.name.value("小%"), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.name NOT LIKE ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(UserProperties.name.value("小%"), statement.getValues().get(0));
    }

    @Test
    public void allFlgs() {
        FlagCriteriaItem criteriaItem = (FlagCriteriaItem) UserProperties.userId.allFlgs(1);
        assertEquals(UserProperties.userId, criteriaItem.getColumn());
        assertEquals(1L, criteriaItem.getValue());
        assertEquals(FlagCriteriaItem.ContainsType.CONTAINS_ALL, criteriaItem.getContainsType());
    }

    @Test
    public void noFlgs() {
        FlagCriteriaItem criteriaItem = (FlagCriteriaItem) UserProperties.userId.noFlgs(1);
        assertEquals(UserProperties.userId, criteriaItem.getColumn());
        assertEquals(1L, criteriaItem.getValue());
        assertEquals(FlagCriteriaItem.ContainsType.CONTAINS_NONE, criteriaItem.getContainsType());
    }

    @Test
    public void anyFlgs() {
        FlagCriteriaItem criteriaItem = (FlagCriteriaItem) UserProperties.userId.anyFlgs(1);
        assertEquals(UserProperties.userId, criteriaItem.getColumn());
        assertEquals(1L, criteriaItem.getValue());
        assertEquals(FlagCriteriaItem.ContainsType.CONTAINS_ANY, criteriaItem.getContainsType());
    }

}