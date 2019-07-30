package tech.ibit.sqlbuilder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tech.ibit.sqlbuilder.aggregate.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author IBIT TECH
 * @version 1.0
 */
public class SqlTest extends CommonTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void select() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u", sql.getSqlParams());


        sql = new Sql().select(Arrays.asList(new SumColumn(UserProperties.userId, "user_id_sum")
                , new AvgColumn(UserProperties.userId, "user_id_avg")))
                .from(UserProperties.TABLE_NAME).groupBy(UserProperties.userId);
        assertParamsEquals("SELECT SUM(u.user_id) AS user_id_sum, AVG(u.user_id) AS user_id_avg FROM user u GROUP BY u.user_id"
                , sql.getSqlParams());


        sql = new Sql().select(Arrays.asList(new SumColumn(UserProperties.userId, "user_id_sum")
                , new AvgColumn(UserProperties.userId, "user_id_avg")))
                .from(UserProperties.TABLE_NAME).groupBy(UserProperties.userId);
        assertParamsEquals("SELECT SUM(u.user_id) AS user_id_sum, AVG(u.user_id) AS user_id_avg FROM user u GROUP BY u.user_id", sql.getSqlParams());

        sql = new Sql()
                .select(Collections.singletonList(new CountColumn(UserProperties.userId, "user_id_total")))
                .from(UserProperties.TABLE_NAME);
        assertParamsEquals("SELECT COUNT(u.user_id) AS user_id_total FROM user u", sql.getSqlParams());
    }

    @Test
    public void selectDistinct() {
        Sql sql = new Sql()
                .selectDistinct(UserProperties.email)
                .from(UserProperties.TABLE_NAME);
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT DISTINCT u.email FROM user u", Collections.emptyList(), sqlParams);
    }

    @Test
    public void selectPo() {
        Sql sql = new Sql()
                .selectPo(UserPO.class)
                .from(UserProperties.TABLE_NAME);
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.login_id, u.email, u.mobile_phone, u.type FROM user u",
                Collections.emptyList(), sqlParams);
    }

    @Test
    public void selectDistinctPo() {
        Sql sql = new Sql()
                .selectDistinctPo(UserPO.class)
                .from(UserProperties.TABLE_NAME).limit(1000);
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT DISTINCT u.user_id, u.login_id, u.email, u.mobile_phone, u.type FROM user u LIMIT ?, ?",
                Arrays.asList(0, 1000), sqlParams);
    }

    @Test
    public void COUNT() {
        Sql sql = new Sql()
                .count()
                .from(UserProperties.TABLE_NAME);
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT COUNT(*) FROM user u", sqlParams);
    }

    @Test
    public void countDistinct() {
        Sql sql = new Sql()
                .countDistinct(UserProperties.userId)
                .from(UserProperties.TABLE_NAME);
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT COUNT(DISTINCT u.user_id) FROM user u", sqlParams);

        sql = new Sql()
                .countDistinct(Arrays.asList(UserProperties.name, UserProperties.email))
                .from(UserProperties.TABLE_NAME);
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT COUNT(DISTINCT u.name, u.email) FROM user u", sqlParams);
    }

    @Test
    public void deleteFrom() {
        Sql sql = new Sql().deleteFrom(UserProperties.TABLE_NAME);
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Where cannot be empty when do deleting!");
        sql.getSqlParams();
    }

    @Test
    public void deleteFrom2() {
        Sql sql = new Sql()
                .deleteFrom(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("DELETE FROM user WHERE user_id = ?", Collections.singletonList(1), sqlParams);
    }


    @Test
    public void deleteTableFrom() {
        Sql sql = new Sql().deleteTableFrom(UserProperties.TABLE_NAME);
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Where cannot be empty when do deleting!");
        sql.getSqlParams();
    }


    @Test
    public void deleteTableFrom2() {
        Sql sql = new Sql()
                .deleteTableFrom(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("DELETE u.* FROM user u WHERE u.user_id = ?", Collections.singletonList(1), sqlParams);
    }


    @Test
    public void deleteTableFrom3() {
        Sql sql = new Sql()
                .deleteTableFrom(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1))
                .leftJoinOn(OrganizationProperties.TABLE, Arrays.asList(UserProperties.orgId, OrganizationProperties.orgId));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("DELETE u.* FROM user u LEFT JOIN organization o ON u.org_id = o.org_id WHERE u.user_id = ?",
                Collections.singletonList(1), sqlParams);
    }

    @Test
    public void update() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE_NAME)
                .set(new ColumnValue(UserProperties.name, "小马"));
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Where cannot be empty when do updating!");
        sql.getSqlParams();
    }

    @Test
    public void update2() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE_NAME)
                .set(new ColumnValue(UserProperties.name, "小马"))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("UPDATE user u SET u.name = ? WHERE u.user_id = ?",
                Arrays.asList("小马", 1), sqlParams);
    }

    @Test
    public void insertInto() {
        Sql sql = new Sql()
                .insertInto(UserProperties.TABLE_NAME)
                .values(Arrays.asList(new ColumnValue(UserProperties.name, "小马")
                        , new ColumnValue(UserProperties.loginId, "188")
                        , new ColumnValue(UserProperties.avatarId, null)));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("INSERT INTO user(name, login_id, avatar_id) VALUES(?, ?, ?)",
                Arrays.asList("小马", "188", null), sqlParams);

    }

    @Test
    public void set() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE_NAME)
                .set(Arrays.asList(new ColumnValue(UserProperties.name, "小马")
                        , new ColumnValue(UserProperties.loginId, "188")
                        , new ColumnValue(UserProperties.avatarId, null)))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));

        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("UPDATE user u SET u.name = ?, u.login_id = ?, u.avatar_id = ? WHERE u.user_id = ?",
                Arrays.asList("小马", "188", null, 1), sqlParams);
    }

    @Test
    public void values() {
        insertInto();
    }

    @Test
    public void from() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME).from(ProjectProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u, sz_project p WHERE u.current_project_id = p.project_id",
                Collections.emptyList(), sqlParams);

        sql = new Sql()
                .select(Arrays.asList(ProjectProperties.projectId, ProjectProperties.name))
                .from(ProjectProperties.TABLE_NAME);
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT p.project_id, p.name FROM sz_project p",
                Collections.emptyList(), sqlParams);
    }

    @Test
    public void joinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME)
                .joinOn(ProjectProperties.TABLE_NAME, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u JOIN sz_project p ON u.current_project_id = p.project_id",
                Collections.emptyList(), sqlParams);
    }

    @Test
    public void leftJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME)
                .leftJoinOn(ProjectProperties.TABLE_NAME, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN sz_project p ON u.current_project_id = p.project_id",
                Collections.emptyList(), sqlParams);
        assertTrue(sqlParams.getParams().isEmpty());
    }

    @Test
    public void rightJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME)
                .rightJoinOn(ProjectProperties.TABLE_NAME, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u RIGHT JOIN sz_project p ON u.current_project_id = p.project_id",
                Collections.emptyList(), sqlParams);
        assertTrue(sqlParams.getParams().isEmpty());
    }

    @Test
    public void fullJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME)
                .fullJoinOn(ProjectProperties.TABLE_NAME, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u FULL JOIN sz_project p ON u.current_project_id = p.project_id",
                Collections.emptyList(), sqlParams);
        assertTrue(sqlParams.getParams().isEmpty());
    }


    @Test
    public void innerJoinOn() {
        Table region = new Table("region", "r");
        Column regionName = new Column(region, "name");
        Column regionParentCode = new Column(region, "parent_code");

        Table parentRegion = new Table("region", "pr");
        Column parentRegionCode = new Column(parentRegion, "code");
        Column parentRegionName = new Column(parentRegion, "name");

        Sql sql = new Sql()
                .select(Arrays.asList(parentRegionName, regionName))
                .from(region)
                .innerJoinOn(parentRegion, Arrays.asList(regionParentCode, parentRegionCode));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT pr.name, r.name FROM region r INNER JOIN region pr ON r.parent_code = pr.code",
                Collections.emptyList(), sqlParams);

    }

    @Test
    public void where() {

        List<CriteriaItem> xiaoLikeItems = Arrays.asList(
                CriteriaItemMaker.like(UserProperties.name, "小%"),
                CriteriaItemMaker.like(UserProperties.email, "xiao%"));
        CriteriaItem userIdItem = CriteriaItemMaker.greaterThan(UserProperties.userId, 100);
        CriteriaItem type1Item = CriteriaItemMaker.equalsTo(UserProperties.type, 1);
        CriteriaItem type2Item = CriteriaItemMaker.equalsTo(UserProperties.type, 2);

        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .where(Criteria.ands(Arrays.asList(Criteria.ors(xiaoLikeItems), userIdItem)));
        SqlParams sqlParams = sql.getSqlParams();
        assertList(sqlParams.getParams(), 3, Arrays.asList("小%", "xiao%", 100));


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .where(
                        Criteria.ands(
                                Arrays.asList(Criteria.ors(xiaoLikeItems),
                                        Criteria.ors(Collections.singletonList(userIdItem)))
                        )
                );
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?",
                Arrays.asList("小%", "xiao%", 100), sqlParams);

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .where(
                        Criteria.ors(Arrays.asList(
                                Criteria.ands(
                                        Arrays.asList(Criteria.ands(
                                                Arrays.asList(Criteria.ors(xiaoLikeItems),
                                                        Criteria.ors(Collections.singletonList(userIdItem)))),
                                                type1Item)
                                ), type2Item))
                );
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (((u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?) AND u.type = ?) OR u.type = ?",
                Arrays.asList("小%", "xiao%", 100, 1, 2), sqlParams);
    }

    private void assertList(List<Object> actualList, int size, List<Object> expectList) {
        assertEquals(actualList.size(), size);
        for (int i = 0; i < actualList.size(); i++) {
            assertEquals(actualList.get(i), expectList.get(i));
        }
    }

    @Test
    public void andWhere() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1))
                .limit(1);
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id = ? LIMIT ?, ?"
                , Arrays.asList(1, 0, 1), sqlParams);

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .andWhere(Criteria.ors(
                        Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .limit(1);
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) LIMIT ?, ?"
                , Arrays.asList("小%", "xiao%", 0, 1), sqlParams);

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .andWhere(Criteria.ors(
                        Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.type, 1))
                .limit(1);
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) AND u.type = ? LIMIT ?, ?"
                , Arrays.asList("小%", "xiao%", 1, 0, 1), sqlParams);
    }

    @Test
    public void orWhere() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .orWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1))
                .limit(1);
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList(1, 0, 1), sqlParams);

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .orWhere(Criteria.ands(
                        Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .limit(1);
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? AND u.email LIKE ?) LIMIT ?, ?",
                Arrays.asList("小%", "xiao%", 0, 1), sqlParams);

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .orWhere(Criteria.ands(
                        Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .orWhere(CriteriaItemMaker.equalsTo(UserProperties.type, 1))
                .limit(1);
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? AND u.email LIKE ?) OR u.type = ? LIMIT ?, ?",
                Arrays.asList("小%", "xiao%", 1, 0, 1), sqlParams);
    }

    @Test
    public void orderBy() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME)
                .leftJoinOn(ProjectProperties.TABLE_NAME, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
                .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new OrderBy(UserProperties.userId, true)))
                .limit(1000);
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN sz_project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                + ", u.user_id DESC LIMIT ?, ?", Arrays.asList(0, 1000), sql.getSqlParams());
    }


    @Test
    public void customerOrderBy() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME)
                .leftJoinOn(ProjectProperties.TABLE_NAME, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
                .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new CustomOrderBy(UserProperties.userId, Arrays.asList(1, 2, 3), true)));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN sz_project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                + ", FIELD(u.user_id, ?, ?, ?) DESC", Arrays.asList(1, 2, 3), sqlParams);
    }

    @Test
    public void nameOrderBy() {
        Sql sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Having.and(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)))
                .orderBy(Arrays.asList(
                        new OrderBy(UserProperties.gender),
                        new NameOrderBy("min_age", true)));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? " +
                "GROUP BY u.gender HAVING min_age >= ? ORDER BY u.gender, min_age DESC", Arrays.asList(0, 1), sqlParams);
    }

    @Test
    public void limit() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME)
                .leftJoinOn(ProjectProperties.TABLE_NAME, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
                .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new OrderBy(UserProperties.userId, true)))
                .limit(10);
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN sz_project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                + ", u.user_id DESC LIMIT ?, ?", Arrays.asList(0, 10), sqlParams);

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE_NAME)
                .leftJoinOn(ProjectProperties.TABLE_NAME, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
                .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new OrderBy(UserProperties.userId, true)))
                .limit(10, 20);
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN sz_project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                + ", u.user_id DESC LIMIT ?, ?", Arrays.asList(10, 20), sqlParams);
    }


    @Test
    public void increaseSet() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE_NAME)
                .increaseSet(new ColumnValue(UserProperties.loginTimes, 2))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("UPDATE user u SET u.login_times = u.login_times + ? WHERE u.user_id = ?", Arrays.asList(2, 1), sqlParams);
    }


    @Test
    public void decreaseSet() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE_NAME)
                .decreaseSet(new ColumnValue(UserProperties.loginTimes, 2))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("UPDATE user u SET u.login_times = u.login_times - ? WHERE u.user_id = ?", Arrays.asList(2, 1), sqlParams);
    }

    @Test
    public void flag() {

        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.containsNoneFlags(UserProperties.userId, 1));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? = 0"
                , Collections.singletonList(1), sqlParams);

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.containsAllFlags(UserProperties.userId, 1));
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? = u.user_id"
                , Collections.singletonList(1), sqlParams);


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.containsAnyFlags(UserProperties.userId, 1));
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? <> 0",
                Collections.singletonList(1), sqlParams);


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.containsAnyFlags(UserProperties.userId, 1))
                .andWhere(CriteriaItemMaker.like(UserProperties.name, "%小马%"));
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? <> 0 AND u.name LIKE ?",
                Arrays.asList(1, "%小马%"), sqlParams);

    }


    @Test
    public void having() {

        Sql sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Having.and(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)));
        SqlParams sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ?",
                Arrays.asList(0, 1), sqlParams);


        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .andHaving(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1));
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ?",
                Arrays.asList(0, 1), sqlParams);


        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Arrays.asList(
                        Having.and(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)),
                        Having.and(HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                ));
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ? AND max_age >= ?",
                Arrays.asList(0, 1, 2), sqlParams);


        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Having.ands(
                        Arrays.asList(
                                HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                HavingItemMaker.greaterThanOrEqualsTo("max_age", 2)
                        )
                ));
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING min_age >= ? AND max_age >= ?", Arrays.asList(0, 1, 2), sqlParams);

        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Arrays.asList(
                        Having.or(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)),
                        Having.or(HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                ));
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING min_age >= ? OR max_age >= ?", Arrays.asList(0, 1, 2), sqlParams);


        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Having.ors(
                        Arrays.asList(
                                HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                HavingItemMaker.greaterThanOrEqualsTo("max_age", 2)
                        )
                ));
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING min_age >= ? OR max_age >= ?", Arrays.asList(0, 1, 2), sqlParams);


        // 复杂的Having语句
        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE_NAME)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .andHaving(
                        Having.ors(
                                Arrays.asList(
                                        HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                        HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                        )).andHaving(
                        Having.ors(
                                Arrays.asList(
                                        HavingItemMaker.greaterThanOrEqualsTo("min_age", 3),
                                        HavingItemMaker.greaterThanOrEqualsTo("max_age", 4)
                                ))
                );
        sqlParams = sql.getSqlParams();
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING (min_age >= ? OR max_age >= ?) AND (min_age >= ? OR max_age >= ?)", Arrays.asList(0, 1, 2, 3, 4), sqlParams);
    }
}