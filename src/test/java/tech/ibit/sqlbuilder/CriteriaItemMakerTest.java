package tech.ibit.sqlbuilder;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test cases for CriteriaItemMaker
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class CriteriaItemMakerTest {

    @Test
    public void isNull() {
        CriteriaItem item = CriteriaItemMaker.isNull(UserProperties.userId);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.IS_NULL);
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
        CriteriaItem item = CriteriaItemMaker.isNotNull(UserProperties.userId);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.IS_NOT_NULL);
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
    public void equalsTo() {
        CriteriaItem item = CriteriaItemMaker.equalsTo(UserProperties.userId, 1);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.EQUALS);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id = ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id = ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

    }

    @Test
    public void equalsTo1() {
        CriteriaItem item = CriteriaItemMaker.equalsTo(UserProperties.orgId, OrganizationProperties.orgId);
        Assert.assertEquals(item.getColumn(), UserProperties.orgId);
        assertEquals(item.getOperator(), Operator.EQUALS);
        Assert.assertEquals(item.getSecondColumn(), OrganizationProperties.orgId);
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
    public void notEqualsTo() {
        CriteriaItem item = CriteriaItemMaker.notEqualsTo(UserProperties.userId, 1);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.NOT_EQUALS);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id <> ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <> ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

    }

    @Test
    public void notEqualsTo1() {
        CriteriaItem item = CriteriaItemMaker.notEqualsTo(UserProperties.orgId, OrganizationProperties.orgId);
        Assert.assertEquals(item.getColumn(), UserProperties.orgId);
        assertEquals(item.getOperator(), Operator.NOT_EQUALS);
        Assert.assertEquals(item.getSecondColumn(), OrganizationProperties.orgId);
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
    public void greaterThan() {
        CriteriaItem item = CriteriaItemMaker.greaterThan(UserProperties.userId, 1);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.GREATER);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id > ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id > ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

    }

    @Test
    public void greaterThan1() {
        CriteriaItem item = CriteriaItemMaker.greaterThan(UserProperties.orgId, OrganizationProperties.orgId);
        Assert.assertEquals(item.getColumn(), UserProperties.orgId);
        assertEquals(item.getOperator(), Operator.GREATER);
        Assert.assertEquals(item.getSecondColumn(), OrganizationProperties.orgId);
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
    public void greaterThanOrEqualsTo() {
        CriteriaItem item = CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.userId, 1);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.GREATER_OR_EQUALS);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id >= ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id >= ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

    }

    @Test
    public void greaterThanOrEqualsTo1() {
        CriteriaItem item = CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.orgId, OrganizationProperties.orgId);
        Assert.assertEquals(item.getColumn(), UserProperties.orgId);
        assertEquals(item.getOperator(), Operator.GREATER_OR_EQUALS);
        Assert.assertEquals(item.getSecondColumn(), OrganizationProperties.orgId);
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
    public void lessThan() {
        CriteriaItem item = CriteriaItemMaker.lessThan(UserProperties.userId, 1);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.LESS);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id < ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id < ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

    }

    @Test
    public void lessThan1() {
        CriteriaItem item = CriteriaItemMaker.lessThan(UserProperties.orgId, OrganizationProperties.orgId);
        Assert.assertEquals(item.getColumn(), UserProperties.orgId);
        assertEquals(item.getOperator(), Operator.LESS);
        Assert.assertEquals(item.getSecondColumn(), OrganizationProperties.orgId);
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
    public void lessThanOrEqualTo() {
        CriteriaItem item = CriteriaItemMaker.lessThanOrEqualTo(UserProperties.userId, 1);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.LESS_OR_EQUALS);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id <= ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <= ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);
    }

    @Test
    public void lessThanOrEqualTo1() {
        CriteriaItem item = CriteriaItemMaker.lessThanOrEqualTo(UserProperties.orgId, OrganizationProperties.orgId);
        Assert.assertEquals(item.getColumn(), UserProperties.orgId);
        assertEquals(item.getOperator(), Operator.LESS_OR_EQUALS);
        Assert.assertEquals(item.getSecondColumn(), OrganizationProperties.orgId);
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
        CriteriaItem item = CriteriaItemMaker.in(UserProperties.userId, Arrays.asList(1, 2));
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.IN);
        assertNull(item.getSecondColumn());
        assertTrue(item.getValue() instanceof List<?>);
        assertEquals(((List) item.getValue()).get(0), 1);
        assertEquals(((List) item.getValue()).get(1), 2);
        assertEquals(((List) item.getValue()).size(), 2);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id IN(?, ?)", statement.getPrepareSql());
        assertEquals(statement.getValues().get(0), 1);
        assertEquals(statement.getValues().get(1), 2);
        assertEquals(statement.getValues().size(), 2);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id IN(?, ?)", statement.getPrepareSql());
        assertEquals(statement.getValues().get(0), 1);
        assertEquals(statement.getValues().get(1), 2);
        assertEquals(statement.getValues().size(), 2);


        //Only one
        item = CriteriaItemMaker.in(UserProperties.userId, Collections.singletonList(1));
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.EQUALS);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertNull(item.getSecondValue());

        statement = item.getPrepareStatement();
        assertEquals("user_id = ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id = ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

    }

    @Test
    public void notIn() {
        CriteriaItem item = CriteriaItemMaker.notIn(UserProperties.userId, Arrays.asList(1, 2));
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.NOT_IN);
        assertNull(item.getSecondColumn());
        assertTrue(item.getValue() instanceof List<?>);
        assertEquals(((List) item.getValue()).get(0), 1);
        assertEquals(((List) item.getValue()).get(1), 2);
        assertEquals(((List) item.getValue()).size(), 2);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id NOT IN(?, ?)", statement.getPrepareSql());
        assertEquals(statement.getValues().get(0), 1);
        assertEquals(statement.getValues().get(1), 2);
        assertEquals(statement.getValues().size(), 2);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id NOT IN(?, ?)", statement.getPrepareSql());
        assertEquals(statement.getValues().get(0), 1);
        assertEquals(statement.getValues().get(1), 2);
        assertEquals(statement.getValues().size(), 2);

        //Only one
        item = CriteriaItemMaker.notIn(UserProperties.userId, Collections.singletonList(1));
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.NOT_EQUALS);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertNull(item.getSecondValue());

        statement = item.getPrepareStatement();
        assertEquals("user_id <> ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <> ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), 1);

    }

    @Test
    public void between() {
        CriteriaItem item = CriteriaItemMaker.between(UserProperties.userId, 1, 2);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.BETWEEN);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertEquals(item.getSecondValue(), 2);

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(statement.getValues().get(0), 1);
        assertEquals(statement.getValues().get(1), 2);
        assertEquals(statement.getValues().size(), 2);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(statement.getValues().get(0), 1);
        assertEquals(statement.getValues().get(1), 2);
        assertEquals(statement.getValues().size(), 2);

    }

    @Test
    public void notBetween() {
        CriteriaItem item = CriteriaItemMaker.notBetween(UserProperties.userId, 1, 2);
        Assert.assertEquals(item.getColumn(), UserProperties.userId);
        assertEquals(item.getOperator(), Operator.NOT_BETWEEN);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), 1);
        assertEquals(item.getSecondValue(), 2);

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id NOT BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(statement.getValues().get(0), 1);
        assertEquals(statement.getValues().get(1), 2);
        assertEquals(statement.getValues().size(), 2);

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id NOT BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(statement.getValues().get(0), 1);
        assertEquals(statement.getValues().get(1), 2);
        assertEquals(statement.getValues().size(), 2);
    }

    @Test
    public void like() {
        CriteriaItem item = CriteriaItemMaker.like(UserProperties.name, "小%");
        Assert.assertEquals(item.getColumn(), UserProperties.name);
        assertEquals(item.getOperator(), Operator.LIKE);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), "小%");
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("name LIKE ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), "小%");

        statement = item.getPrepareStatement(true);
        assertEquals("u.name LIKE ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), "小%");
    }

    @Test
    public void notLike() {
        CriteriaItem item = CriteriaItemMaker.notLike(UserProperties.name, "小%");
        Assert.assertEquals(item.getColumn(), UserProperties.name);
        assertEquals(item.getOperator(), Operator.NOT_LIKE);
        assertNull(item.getSecondColumn());
        assertEquals(item.getValue(), "小%");
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("name NOT LIKE ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), "小%");

        statement = item.getPrepareStatement(true);
        assertEquals("u.name NOT LIKE ?", statement.getPrepareSql());
        assertEquals(statement.getValues().size(), 1);
        assertEquals(statement.getValues().get(0), "小%");
    }

}