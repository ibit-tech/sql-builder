package tech.ibit.sqlbuilder;

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
public class CriteriaItemMakerTest extends CommonTest {

    @Test
    public void isNull() {
        CriteriaItem item = CriteriaItemMaker.isNull(UserProperties.userId);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.IS_NULL, item.getOperator());
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
        assertEquals(item.getColumn(), UserProperties.userId);
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
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.EQUALS, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id = ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id = ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));

    }

    @Test
    public void equalsTo1() {
        CriteriaItem item = CriteriaItemMaker.equalsTo(UserProperties.orgId, OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(Operator.EQUALS, item.getOperator());
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
    public void notEqualsTo() {
        CriteriaItem item = CriteriaItemMaker.notEqualsTo(UserProperties.userId, 1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.NOT_EQUALS, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id <> ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <> ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));

    }

    @Test
    public void notEqualsTo1() {
        CriteriaItem item = CriteriaItemMaker.notEqualsTo(UserProperties.orgId, OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(Operator.NOT_EQUALS, item.getOperator());
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
    public void greaterThan() {
        CriteriaItem item = CriteriaItemMaker.greaterThan(UserProperties.userId, 1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.GREATER, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id > ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id > ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));

    }

    @Test
    public void greaterThan1() {
        CriteriaItem item = CriteriaItemMaker.greaterThan(UserProperties.orgId, OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(Operator.GREATER, item.getOperator());
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
    public void greaterThanOrEqualsTo() {
        CriteriaItem item = CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.userId, 1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.GREATER_OR_EQUALS, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id >= ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id >= ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));

    }

    @Test
    public void greaterThanOrEqualsTo1() {
        CriteriaItem item = CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.orgId, OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(Operator.GREATER_OR_EQUALS, item.getOperator());
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
    public void lessThan() {
        CriteriaItem item = CriteriaItemMaker.lessThan(UserProperties.userId, 1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.LESS, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id < ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id < ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));

    }

    @Test
    public void lessThan1() {
        CriteriaItem item = CriteriaItemMaker.lessThan(UserProperties.orgId, OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(Operator.LESS, item.getOperator());
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
    public void lessThanOrEqualTo() {
        CriteriaItem item = CriteriaItemMaker.lessThanOrEqualTo(UserProperties.userId, 1);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.LESS_OR_EQUALS, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id <= ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <= ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size(), 1);
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));
    }

    @Test
    public void lessThanOrEqualTo1() {
        CriteriaItem item = CriteriaItemMaker.lessThanOrEqualTo(UserProperties.orgId, OrganizationProperties.orgId);
        assertEquals(UserProperties.orgId, item.getColumn());
        assertEquals(Operator.LESS_OR_EQUALS, item.getOperator());
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
        CriteriaItem item = CriteriaItemMaker.in(UserProperties.userId, Arrays.asList(1, 2));
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.IN, item.getOperator());
        assertNull(item.getSecondColumn());
        assertTrue(item.getValue() instanceof List<?>);
        assertEquals(1, ((List) item.getValue()).get(0));
        assertEquals(2, ((List) item.getValue()).get(1));
        assertEquals(2, ((List) item.getValue()).size());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id IN(?, ?)", statement.getPrepareSql());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));
        assertEquals(new KeyValuePair("user_id", 2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id IN(?, ?)", statement.getPrepareSql());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));
        assertEquals(new KeyValuePair("u.user_id", 2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());


        //Only one
        item = CriteriaItemMaker.in(UserProperties.userId, Collections.singletonList(1));
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.EQUALS, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        statement = item.getPrepareStatement();
        assertEquals("user_id = ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id = ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));

    }

    @Test
    public void notIn() {
        CriteriaItem item = CriteriaItemMaker.notIn(UserProperties.userId, Arrays.asList(1, 2));
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.NOT_IN, item.getOperator());
        assertNull(item.getSecondColumn());
        assertTrue(item.getValue() instanceof List<?>);
        assertEquals(((List) item.getValue()).get(0), 1);
        assertEquals(((List) item.getValue()).get(1), 2);
        assertEquals(((List) item.getValue()).size(), 2);
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id NOT IN(?, ?)", statement.getPrepareSql());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));
        assertEquals(new KeyValuePair("user_id", 2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id NOT IN(?, ?)", statement.getPrepareSql());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));
        assertEquals(new KeyValuePair("u.user_id", 2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        //Only one
        item = CriteriaItemMaker.notIn(UserProperties.userId, Collections.singletonList(1));
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.NOT_EQUALS, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertNull(item.getSecondValue());

        statement = item.getPrepareStatement();
        assertEquals("user_id <> ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id <> ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));

    }

    @Test
    public void between() {
        CriteriaItem item = CriteriaItemMaker.between(UserProperties.userId, 1, 2);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.BETWEEN, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertEquals(2, item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));
        assertEquals(new KeyValuePair("user_id", 2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));
        assertEquals(new KeyValuePair("u.user_id", 2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

    }

    @Test
    public void notBetween() {
        CriteriaItem item = CriteriaItemMaker.notBetween(UserProperties.userId, 1, 2);
        assertEquals(UserProperties.userId, item.getColumn());
        assertEquals(Operator.NOT_BETWEEN, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals(1, item.getValue());
        assertEquals(2, item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("user_id NOT BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(new KeyValuePair("user_id", 1), statement.getValues().get(0));
        assertEquals(new KeyValuePair("user_id", 2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());

        statement = item.getPrepareStatement(true);
        assertEquals("u.user_id NOT BETWEEN ? AND ?", statement.getPrepareSql());
        assertEquals(new KeyValuePair("u.user_id", 1), statement.getValues().get(0));
        assertEquals(new KeyValuePair("u.user_id", 2), statement.getValues().get(1));
        assertEquals(2, statement.getValues().size());
    }

    @Test
    public void like() {
        CriteriaItem item = CriteriaItemMaker.like(UserProperties.name, "小%");
        assertEquals(UserProperties.name, item.getColumn());
        assertEquals(Operator.LIKE, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals("小%", item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("name LIKE ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("name", "小%"), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.name LIKE ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.name", "小%"), statement.getValues().get(0));
    }

    @Test
    public void notLike() {
        CriteriaItem item = CriteriaItemMaker.notLike(UserProperties.name, "小%");
        assertEquals(UserProperties.name, item.getColumn());
        assertEquals(Operator.NOT_LIKE, item.getOperator());
        assertNull(item.getSecondColumn());
        assertEquals("小%", item.getValue());
        assertNull(item.getSecondValue());

        PrepareStatement statement = item.getPrepareStatement();
        assertEquals("name NOT LIKE ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("name", "小%"), statement.getValues().get(0));

        statement = item.getPrepareStatement(true);
        assertEquals("u.name NOT LIKE ?", statement.getPrepareSql());
        assertEquals(1, statement.getValues().size());
        assertEquals(new KeyValuePair("u.name", "小%"), statement.getValues().get(0));
    }

}