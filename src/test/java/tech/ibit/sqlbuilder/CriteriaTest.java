package tech.ibit.sqlbuilder;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

/**
 * @author IBIT TECH
 * @version 1.0
 */
public class CriteriaTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createANDs() {

        List<Criteria> subCriterion = Arrays.asList(
                Criteria.or(CriteriaItemMaker.equalsTo(UserProperties.userId, 1)),
                Criteria.or(CriteriaItemMaker.like(UserProperties.name, "小%")));
        List<Criteria> ands = Criteria.ands(Arrays.asList(subCriterion, CriteriaItemMaker.equalsTo(UserProperties.type, 1)));
        Assert.assertEquals(ands.size(), 2);

        thrown.expect(ClassCastException.class);
        thrown.expectMessage("java.lang.Integer cannot be cast to java.util.List");
        Criteria.ands(Arrays.asList(subCriterion, CriteriaItemMaker.equalsTo(UserProperties.type, 1), 3));
    }

}