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
                .from(UserProperties.TABLE);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u", sql.getSqlParams());


        sql = new Sql().select(Arrays.asList(new SumColumn(UserProperties.userId, "user_id_sum")
                , new AvgColumn(UserProperties.userId, "user_id_avg")))
                .from(UserProperties.TABLE).groupBy(UserProperties.userId);
        assertParamsEquals("SELECT SUM(u.user_id) AS user_id_sum, AVG(u.user_id) AS user_id_avg FROM user u GROUP BY u.user_id"
                , sql.getSqlParams());


        sql = new Sql().select(Arrays.asList(new SumColumn(UserProperties.userId, "user_id_sum")
                , new AvgColumn(UserProperties.userId, "user_id_avg")))
                .from(UserProperties.TABLE).groupBy(UserProperties.userId);
        assertParamsEquals("SELECT SUM(u.user_id) AS user_id_sum, AVG(u.user_id) AS user_id_avg FROM user u GROUP BY u.user_id", sql.getSqlParams());

        sql = new Sql()
                .select(Collections.singletonList(new CountColumn(UserProperties.userId, "user_id_total")))
                .from(UserProperties.TABLE);
        assertParamsEquals("SELECT COUNT(u.user_id) AS user_id_total FROM user u", sql.getSqlParams());
    }

    @Test
    public void selectDistinct() {
        Sql sql = new Sql()
                .selectDistinct(UserProperties.email)
                .from(UserProperties.TABLE);
        assertParamsEquals("SELECT DISTINCT u.email FROM user u", Collections.emptyList(), sql.getSqlParams());
    }

    @Test
    public void selectPo() {
        Sql sql = new Sql()
                .selectPo(UserPo.class)
                .from(UserProperties.TABLE);
        assertParamsEquals("SELECT u.user_id, u.login_id, u.email, u.mobile_phone, u.type FROM user u",
                Collections.emptyList(), sql.getSqlParams());
    }

    @Test
    public void selectDistinctPo() {
        Sql sql = new Sql()
                .selectDistinctPo(UserPo.class)
                .from(UserProperties.TABLE).limit(1000);
        assertParamsEquals("SELECT DISTINCT u.user_id, u.login_id, u.email, u.mobile_phone, u.type FROM user u LIMIT ?, ?",
                Arrays.asList("$start", 0, "$limit", 1000), sql.getSqlParams());
    }

    @Test
    public void count() {
        Sql sql = new Sql()
                .count()
                .from(UserProperties.TABLE);
        assertParamsEquals("SELECT COUNT(*) FROM user u", sql.getSqlParams());
    }

    @Test
    public void countDistinct() {
        Sql sql = new Sql()
                .countDistinct(UserProperties.userId)
                .from(UserProperties.TABLE);
        assertParamsEquals("SELECT COUNT(DISTINCT u.user_id) FROM user u", sql.getSqlParams());

        sql = new Sql()
                .countDistinct(Arrays.asList(UserProperties.name, UserProperties.email))
                .from(UserProperties.TABLE);
        assertParamsEquals("SELECT COUNT(DISTINCT u.name, u.email) FROM user u", sql.getSqlParams());
    }

    @Test
    public void deleteFrom() {
        Sql sql = new Sql().deleteFrom(UserProperties.TABLE);
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Where cannot be empty when do deleting!");
        sql.getSqlParams();
    }

    @Test
    public void deleteFrom2() {
        Sql sql = new Sql()
                .deleteFrom(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        assertParamsEquals("DELETE FROM user WHERE user_id = ?",
                Arrays.asList("user_id", 1), sql.getSqlParams());
    }


    @Test
    public void deleteTableFrom() {
        Sql sql = new Sql().deleteTableFrom(UserProperties.TABLE);
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Where cannot be empty when do deleting!");
        sql.getSqlParams();
    }


    @Test
    public void deleteTableFrom2() {
        Sql sql = new Sql()
                .deleteTableFrom(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        assertParamsEquals("DELETE u.* FROM user u WHERE u.user_id = ?",
                Arrays.asList("u.user_id", 1), sql.getSqlParams());
    }


    @Test
    public void deleteTableFrom3() {
        Sql sql = new Sql()
                .deleteTableFrom(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1))
                .leftJoinOn(OrganizationProperties.TABLE, Arrays.asList(UserProperties.orgId, OrganizationProperties.orgId));
        assertParamsEquals("DELETE u.* FROM user u LEFT JOIN organization o ON u.org_id = o.org_id WHERE u.user_id = ?",
                Arrays.asList("u.user_id", 1), sql.getSqlParams());
    }

    @Test
    public void update() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE)
                .set(new ColumnValue(UserProperties.name, "IBIT"));
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Where cannot be empty when do updating!");
        sql.getSqlParams();
    }

    @Test
    public void update2() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE)
                .set(new ColumnValue(UserProperties.name, "IBIT"))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        assertParamsEquals("UPDATE user u SET u.name = ? WHERE u.user_id = ?",
                Arrays.asList("u.name", "IBIT", "u.user_id", 1), sql.getSqlParams());
    }

    @Test
    public void insertInto() {
        Sql sql = new Sql()
                .insertInto(UserProperties.TABLE)
                .values(Arrays.asList(new ColumnValue(UserProperties.name, "IBIT")
                        , new ColumnValue(UserProperties.loginId, "188")
                        , new ColumnValue(UserProperties.avatarId, null)));
        assertParamsEquals("INSERT INTO user(name, login_id, avatar_id) VALUES(?, ?, ?)",
                Arrays.asList("name", "IBIT", "login_id", "188", "avatar_id", null), sql.getSqlParams());

    }

    @Test
    public void set() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE)
                .set(Arrays.asList(new ColumnValue(UserProperties.name, "IBIT")
                        , new ColumnValue(UserProperties.loginId, "188")
                        , new ColumnValue(UserProperties.avatarId, null)))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        assertParamsEquals("UPDATE user u SET u.name = ?, u.login_id = ?, u.avatar_id = ? WHERE u.user_id = ?",
                Arrays.asList("u.name", "IBIT", "u.login_id", "188", "u.avatar_id", null, "u.user_id", 1), sql.getSqlParams());
    }

    @Test
    public void values() {
        insertInto();
    }

    @Test
    public void from() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .from(ProjectProperties.TABLE)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId));
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u, project p WHERE u.current_project_id = p.project_id",
                Collections.emptyList(), sql.getSqlParams());

        sql = new Sql()
                .select(Arrays.asList(ProjectProperties.projectId, ProjectProperties.name))
                .from(ProjectProperties.TABLE);
        assertParamsEquals("SELECT p.project_id, p.name FROM project p",
                Collections.emptyList(), sql.getSqlParams());
    }

    @Test
    public void joinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .joinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u JOIN project p ON u.current_project_id = p.project_id", sql.getSqlParams());
    }

    @Test
    public void leftJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id", sql.getSqlParams());
    }

    @Test
    public void rightJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .rightJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u RIGHT JOIN project p ON u.current_project_id = p.project_id", sql.getSqlParams());
    }

    @Test
    public void fullJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .fullJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u FULL JOIN project p ON u.current_project_id = p.project_id", sql.getSqlParams());
    }


    @Test
    public void complexLeftJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .complexLeftJoinOn(ProjectProperties.TABLE, Collections.singletonList(CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId)));
        SqlParams sqlParams = sql.getSqlParams();
        assertEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id", sqlParams.getSql());
        assertTrue(sqlParams.getParamDetails().isEmpty());


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .complexLeftJoinOn(ProjectProperties.TABLE, Arrays.asList(
                        CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId),
                        CriteriaItemMaker.like(ProjectProperties.name, "小%")));
        sqlParams = sql.getSqlParams();
        assertEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p "
                + "ON u.current_project_id = p.project_id AND p.name LIKE ?", sqlParams.getSql());
        assertList(sqlParams.getParamDetails(), 1, Arrays.asList("p.name", "小%"));
    }

    @Test
    public void complexRightJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .complexRightJoinOn(ProjectProperties.TABLE, Collections.singletonList(CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId)));
        SqlParams sqlParams = sql.getSqlParams();
        assertEquals("SELECT u.user_id, u.name, p.name FROM user u RIGHT JOIN project p ON u.current_project_id = p.project_id", sqlParams.getSql());
        assertTrue(sqlParams.getParamDetails().isEmpty());


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .complexRightJoinOn(ProjectProperties.TABLE, Arrays.asList(
                        CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId),
                        CriteriaItemMaker.like(ProjectProperties.name, "小%")));
        sqlParams = sql.getSqlParams();
        assertEquals("SELECT u.user_id, u.name, p.name FROM user u RIGHT JOIN project p ON u.current_project_id = p.project_id AND p.name LIKE ?", sqlParams.getSql());
        assertList(sqlParams.getParamDetails(), 1, Arrays.asList("p.name", "小%"));
    }

    @Test
    public void complexFullJoinOn() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .complexFullJoinOn(ProjectProperties.TABLE, Collections.singletonList(CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId)));
        SqlParams sqlParams = sql.getSqlParams();
        assertEquals("SELECT u.user_id, u.name, p.name FROM user u FULL JOIN project p ON u.current_project_id = p.project_id", sqlParams.getSql());
        assertTrue(sqlParams.getParamDetails().isEmpty());


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .complexFullJoinOn(ProjectProperties.TABLE, Arrays.asList(
                        CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId),
                        CriteriaItemMaker.like(ProjectProperties.name, "小%")));
        sqlParams = sql.getSqlParams();
        assertEquals("SELECT u.user_id, u.name, p.name FROM user u FULL JOIN project p ON u.current_project_id = p.project_id AND p.name LIKE ?", sqlParams.getSql());
        assertList(sqlParams.getParamDetails(), 1, Arrays.asList("p.name", "小%"));
    }


    @Test
    public void complexInnerJoinOn() {
        Table region = new Table("region", "r");
        Column regionName = new Column(region, "name");
        Column regionParentCode = new Column(region, "parent_code");

        Table parentRegion = new Table("region", "pr");
        Column parentRegionCode = new Column(parentRegion, "code");
        Column parentRegionName = new Column(parentRegion, "name");

        Sql sql = new Sql()
                .select(Arrays.asList(parentRegionName, regionName))
                .from(region)
                .complexInnerJoinOn(parentRegion, Collections.singletonList(CriteriaItemMaker.equalsTo(regionParentCode, parentRegionCode)));
        SqlParams sqlParams = sql.getSqlParams();
        assertEquals("SELECT pr.name, r.name FROM region r INNER JOIN region pr ON r.parent_code = pr.code", sqlParams.getSql());
        assertTrue(sqlParams.getParamDetails().isEmpty());


        sql = new Sql()
                .select(Arrays.asList(parentRegionName, regionName))
                .from(region)
                .complexInnerJoinOn(parentRegion, Arrays.asList(
                        CriteriaItemMaker.equalsTo(regionParentCode, parentRegionCode),
                        CriteriaItemMaker.equalsTo(regionParentCode, "110")));
        sqlParams = sql.getSqlParams();
        assertEquals("SELECT pr.name, r.name FROM region r INNER JOIN region pr ON r.parent_code = pr.code AND r.parent_code = ?", sqlParams.getSql());
        assertList(sqlParams.getParamDetails(), 1, Arrays.asList("r.parent_code", "110"));

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
        assertParamsEquals("SELECT pr.name, r.name FROM region r INNER JOIN region pr ON r.parent_code = pr.code", sql.getSqlParams());

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
                .from(UserProperties.TABLE)
                .where(Criteria.ands(Arrays.asList(Criteria.ors(xiaoLikeItems), userIdItem)));
        assertList(sql.getSqlParams().getParamDetails(), 3, Arrays.asList("u.name", "小%", "u.email", "xiao%", "u.user_id", 100));


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .where(
                        Criteria.ands(
                                Arrays.asList(Criteria.ors(xiaoLikeItems),
                                        Criteria.ors(Collections.singletonList(userIdItem)))
                        )
                );
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?",
                Arrays.asList("u.name", "小%", "u.email", "xiao%", "u.user_id", 100), sql.getSqlParams());

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .where(
                        Criteria.ors(Arrays.asList(
                                Criteria.ands(
                                        Arrays.asList(Criteria.ands(
                                                Arrays.asList(Criteria.ors(xiaoLikeItems),
                                                        Criteria.ors(Collections.singletonList(userIdItem)))),
                                                type1Item)
                                ), type2Item))
                );
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (((u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?) AND u.type = ?) OR u.type = ?",
                Arrays.asList("u.name", "小%", "u.email", "xiao%", "u.user_id", 100, "u.type", 1, "u.type", 2), sql.getSqlParams());
    }

    private void assertList(List<KeyValuePair> actualList, int size, List<Object> expectKeyValuePairs) {
        assertEquals(actualList.size(), size);
        List<KeyValuePair> keyValuePairs = getKeyValuePairs(expectKeyValuePairs);
        for (int i = 0; i < actualList.size(); i++) {
            assertEquals(actualList.get(i), keyValuePairs.get(i));
        }
    }

    @Test
    public void andWhere() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1))
                .limit(1);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id = ? LIMIT ?, ?"
                , Arrays.asList("u.user_id", 1, "$start", 0, "$limit", 1), sql.getSqlParams());

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .andWhere(Criteria.ors(
                        Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .limit(1);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) LIMIT ?, ?"
                , Arrays.asList("u.name", "小%", "u.email", "xiao%", "$start", 0, "$limit", 1), sql.getSqlParams());

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .andWhere(Criteria.ors(
                        Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.type, 1))
                .limit(1);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) AND u.type = ? LIMIT ?, ?"
                , Arrays.asList("u.name", "小%", "u.email", "xiao%", "u.type", 1, "$start", 0, "$limit", 1), sql.getSqlParams());
    }

    @Test
    public void orWhere() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .orWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1))
                .limit(1);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList("u.user_id", 1, "$start", 0, "$limit", 1), sql.getSqlParams());

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .orWhere(Criteria.ands(
                        Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .limit(1);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? AND u.email LIKE ?) LIMIT ?, ?",
                Arrays.asList("u.name", "小%", "u.email", "xiao%", "$start", 0, "$limit", 1), sql.getSqlParams());

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .orWhere(Criteria.ands(
                        Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .orWhere(CriteriaItemMaker.equalsTo(UserProperties.type, 1))
                .limit(1);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? AND u.email LIKE ?) OR u.type = ? LIMIT ?, ?",
                Arrays.asList("u.name", "小%", "u.email", "xiao%", "u.type", 1, "$start", 0, "$limit", 1), sql.getSqlParams());
    }

    @Test
    public void orderBy() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
                .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new OrderBy(UserProperties.userId, true)))
                .limit(1000);
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id, u.user_id DESC LIMIT ?, ?",
                Arrays.asList("$start", 0, "$limit", 1000), sql.getSqlParams());
    }


    @Test
    public void customerOrderBy() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
                .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new CustomOrderBy(UserProperties.userId, Arrays.asList(1, 2, 3), true)));
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                + ", FIELD(u.user_id, ?, ?, ?) DESC", Arrays.asList("u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sql.getSqlParams());
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
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Having.and(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)))
                .orderBy(Arrays.asList(
                        new OrderBy(UserProperties.gender),
                        new NameOrderBy("min_age", true)));
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? " +
                "GROUP BY u.gender HAVING min_age >= ? ORDER BY u.gender, min_age DESC", Arrays.asList("u.age", 0, "min_age", 1), sql.getSqlParams());
    }

    @Test
    public void limit() {
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
                .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new OrderBy(UserProperties.userId, true)))
                .limit(10);
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                + ", u.user_id DESC LIMIT ?, ?", Arrays.asList("$start", 0, "$limit", 10), sql.getSqlParams());

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
                .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new OrderBy(UserProperties.userId, true)))
                .limit(10, 20);
        assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                + ", u.user_id DESC LIMIT ?, ?", Arrays.asList("$start", 10, "$limit", 20), sql.getSqlParams());
    }


    @Test
    public void increaseSet() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE)
                .increaseSet(new ColumnValue(UserProperties.loginTimes, 2))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        assertParamsEquals("UPDATE user u SET u.login_times = u.login_times + ? WHERE u.user_id = ?", Arrays.asList("u.login_times", 2, "u.user_id", 1), sql.getSqlParams());
    }


    @Test
    public void decreaseSet() {
        Sql sql = new Sql()
                .update(UserProperties.TABLE)
                .decreaseSet(new ColumnValue(UserProperties.loginTimes, 2))
                .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
        assertParamsEquals("UPDATE user u SET u.login_times = u.login_times - ? WHERE u.user_id = ?"
                , Arrays.asList("u.login_times", 2, "u.user_id", 1), sql.getSqlParams());
    }

    @Test
    public void flag() {

        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.containsNoneFlags(UserProperties.userId, 1));
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? = 0",
                Arrays.asList("u.user_id", 1), sql.getSqlParams());

        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.containsAllFlags(UserProperties.userId, 1));
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? = u.user_id",
                Arrays.asList("u.user_id", 1), sql.getSqlParams());


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.containsAnyFlags(UserProperties.userId, 1));
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? <> 0",
                Arrays.asList("u.user_id", 1), sql.getSqlParams());


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.containsAnyFlags(UserProperties.userId, 1))
                .andWhere(CriteriaItemMaker.like(UserProperties.name, "%IBIT%"));
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? <> 0 AND u.name LIKE ?",
                Arrays.asList("u.user_id", 1, "u.name", "%IBIT%"), sql.getSqlParams());

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
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Having.and(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)));
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ?",
                Arrays.asList("u.age", 0, "min_age", 1), sql.getSqlParams());


        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .andHaving(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1));
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ?",
                Arrays.asList("u.age", 0, "min_age", 1), sql.getSqlParams());


        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Arrays.asList(
                        Having.and(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)),
                        Having.and(HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                ));
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ? AND max_age >= ?",
                Arrays.asList("u.age", 0, "min_age", 1, "max_age", 2), sql.getSqlParams());


        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Having.ands(
                        Arrays.asList(
                                HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                HavingItemMaker.greaterThanOrEqualsTo("max_age", 2)
                        )
                ));
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING min_age >= ? AND max_age >= ?", Arrays.asList("u.age", 0, "min_age", 1, "max_age", 2), sql.getSqlParams());

        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Arrays.asList(
                        Having.or(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)),
                        Having.or(HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                ));
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING min_age >= ? OR max_age >= ?", Arrays.asList("u.age", 0, "min_age", 1, "max_age", 2), sql.getSqlParams());


        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .having(Having.ors(
                        Arrays.asList(
                                HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                HavingItemMaker.greaterThanOrEqualsTo("max_age", 2)
                        )
                ));
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING min_age >= ? OR max_age >= ?", Arrays.asList("u.age", 0, "min_age", 1, "max_age", 2), sql.getSqlParams());


        // 复杂的Having语句
        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .andHaving(
                        Having.ors(
                                Arrays.asList(
                                        HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                        HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                        ))
                .andHaving(
                        Having.ors(
                                Arrays.asList(
                                        HavingItemMaker.greaterThanOrEqualsTo("min_age", 3),
                                        HavingItemMaker.greaterThanOrEqualsTo("max_age", 4)
                                ))
                );
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                        + "HAVING (min_age >= ? OR max_age >= ?) AND (min_age >= ? OR max_age >= ?)",
                Arrays.asList("u.age", 0, "min_age", 1, "max_age", 2, "min_age", 3, "max_age", 4), sql.getSqlParams());


        // 复杂的Having语句
        sql = new Sql()
                .select(
                        Arrays.asList(
                                new MinColumn(UserProperties.age, "min_age"),
                                new MaxColumn(UserProperties.age, "max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
                .groupBy(UserProperties.gender)
                .orHaving(
                        Having.ands(
                                Arrays.asList(
                                        HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                        HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                        ))
                .orHaving(
                        Having.ands(
                                Arrays.asList(
                                        HavingItemMaker.greaterThanOrEqualsTo("min_age", 3),
                                        HavingItemMaker.greaterThanOrEqualsTo("max_age", 4)
                                ))
                );
        assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                        + "HAVING (min_age >= ? AND max_age >= ?) OR (min_age >= ? AND max_age >= ?)",
                Arrays.asList("u.age", 0, "min_age", 1, "max_age", 2, "min_age", 3, "max_age", 4), sql.getSqlParams());
    }
}