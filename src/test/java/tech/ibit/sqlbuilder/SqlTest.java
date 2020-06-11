package tech.ibit.sqlbuilder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tech.ibit.sqlbuilder.sql.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author IBIT程序猿
 * @version 1.0
 */
public class SqlTest extends CommonTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void select() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name)
                )
                .from(UserProperties.TABLE);
        assertPrepareStatementEquals("SELECT u.user_id, u.name FROM user u", sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId.sum("user_id_sum"),
                                UserProperties.userId.avg("user_id_avg")
                        )
                ).from(UserProperties.TABLE)
                .groupBy(UserProperties.userId);
        assertPrepareStatementEquals(
                "SELECT SUM(u.user_id) AS user_id_sum, AVG(u.user_id) AS user_id_avg FROM user u GROUP BY u.user_id",
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId.sum("user_id_sum"),
                                UserProperties.userId.avg("user_id_avg"))
                ).from(UserProperties.TABLE)
                .groupBy(UserProperties.userId);
        assertPrepareStatementEquals(
                "SELECT SUM(u.user_id) AS user_id_sum, AVG(u.user_id) AS user_id_avg FROM user u GROUP BY u.user_id",
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Collections.singletonList(
                                UserProperties.userId.count("user_id_total")
                        )
                ).from(UserProperties.TABLE);
        assertPrepareStatementEquals(
                "SELECT COUNT(u.user_id) AS user_id_total FROM user u",
                sql.getPrepareStatement());
    }

    @Test
    public void selectDistinct() {
        SearchSql sql = SqlFactory.createSearch()
                .distinct()
                .column(UserProperties.email)
                .from(UserProperties.TABLE);
        assertPrepareStatementEquals(
                "SELECT DISTINCT u.email FROM user u",
                sql.getPrepareStatement());
    }

    @Test
    public void selectPo() {
        SearchSql sql = SqlFactory.createSearch()
                .columnPo(UserPo.class)
                .from(UserProperties.TABLE);
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.login_id, u.email, u.mobile_phone, u.type FROM user u",
                sql.getPrepareStatement());
    }

    @Test
    public void selectDistinctPo() {

        SearchSql sql = SqlFactory.createSearch()
                .distinct()
                .columnPo(UserPo.class)
                .from(UserProperties.TABLE).limit(1000);

        assertPrepareStatementEquals(
                "SELECT DISTINCT u.user_id, u.login_id, u.email, u.mobile_phone, u.type FROM user u LIMIT ?, ?",
                Arrays.asList(
                        getStartColumn().value(0),
                        getLimitColumn().value(1000)
                ), sql.getPrepareStatement());
    }

    @Test
    public void count() {
        CountSql sql = SqlFactory.createCount()
                .from(UserProperties.TABLE);
        assertPrepareStatementEquals("SELECT COUNT(*) FROM user u", sql.getPrepareStatement());
    }

    @Test
    public void countDistinct() {
        CountSql sql = SqlFactory.createCount()
                .distinct()
                .column(UserProperties.userId)
                .from(UserProperties.TABLE);
        assertPrepareStatementEquals(
                "SELECT COUNT(DISTINCT u.user_id) FROM user u",
                sql.getPrepareStatement());

        sql = SqlFactory.createCount()
                .distinct()
                .column(
                        Arrays.asList(
                                UserProperties.name,
                                UserProperties.email
                        )
                )
                .from(UserProperties.TABLE);
        assertPrepareStatementEquals(
                "SELECT COUNT(DISTINCT u.name, u.email) FROM user u",
                sql.getPrepareStatement());
    }

    @Test
    public void deleteFrom() {
        DeleteSql sql = SqlFactory.createDelete()
                .deleteFrom(UserProperties.TABLE);
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Where cannot be empty when do deleting!");
        sql.getPrepareStatement();
    }

    @Test
    public void deleteFrom2() {
        DeleteSql sql = SqlFactory.createDelete()
                .deleteFrom(UserProperties.TABLE)
                .andWhere(UserProperties.userId.eq(1));
        assertPrepareStatementEquals(
                "DELETE FROM user WHERE user_id = ?",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createDelete()
                .delete(UserProperties.TABLE)
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.userId.eq(1));
        assertPrepareStatementEquals(
                "DELETE FROM user WHERE user_id = ?",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createDelete()
                .deleteFrom(UserProperties.TABLE)
                .andWhere(UserProperties.userId.eq(1))
                .leftJoinOn(
                        OrganizationProperties.TABLE,
                        Arrays.asList(
                                UserProperties.orgId,
                                OrganizationProperties.orgId
                        )
                );
        assertPrepareStatementEquals(
                "DELETE u.* FROM user u LEFT JOIN organization o ON u.org_id = o.org_id WHERE u.user_id = ?",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createDelete()
                .delete(UserProperties.TABLE)
                .from(
                        Arrays.asList(
                                UserProperties.TABLE,
                                OrganizationProperties.TABLE
                        )
                )
                .andWhere(OrganizationProperties.orgId.eq(UserProperties.orgId))
                .andWhere(UserProperties.userId.eq(1));
        assertPrepareStatementEquals(
                "DELETE u.* FROM user u, organization o WHERE o.org_id = u.org_id AND u.user_id = ?",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());

    }

    @Test
    public void update() {
        UpdateSql sql = SqlFactory
                .createUpdate()
                .update(UserProperties.TABLE)
                .set(UserProperties.name.set("IBIT"));
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Where cannot be empty when do updating!");
        sql.getPrepareStatement();
    }

    @Test
    public void update2() {
        UpdateSql sql = SqlFactory
                .createUpdate()
                .update(UserProperties.TABLE)
                .set(UserProperties.name.set("IBIT"))
                .andWhere(UserProperties.userId.eq(1));

        assertPrepareStatementEquals(
                "UPDATE user u SET u.name = ? WHERE u.user_id = ?",
                Arrays.asList(
                        UserProperties.name.value("IBIT"),
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());
    }

    @Test
    public void insertInto() {
        InsertSql sql = SqlFactory
                .createInsert()
                .insert(UserProperties.TABLE)
                .values(
                        Arrays.asList(
                                UserProperties.name.value("IBIT"),
                                UserProperties.loginId.value("188"),
                                UserProperties.avatarId.value(null)
                        )
                );

        assertPrepareStatementEquals(
                "INSERT INTO user(name, login_id, avatar_id) VALUES(?, ?, ?)",
                Arrays.asList(
                        UserProperties.name.value("IBIT"),
                        UserProperties.loginId.value("188"),
                        UserProperties.avatarId.value(null)
                ),
                sql.getPrepareStatement());

    }

    @Test
    public void set() {
        UpdateSql sql = SqlFactory.createUpdate()
                .update(UserProperties.TABLE)
                .set(
                        Arrays.asList(
                                UserProperties.name.set("IBIT"),
                                UserProperties.loginId.set("188"),
                                UserProperties.avatarId.set(null)
                        )
                ).andWhere(UserProperties.userId.eq(1));

        assertPrepareStatementEquals(
                "UPDATE user u SET u.name = ?, u.login_id = ?, u.avatar_id = ? WHERE u.user_id = ?",
                Arrays.asList(
                        UserProperties.name.value("IBIT"),
                        UserProperties.loginId.value("188"),
                        UserProperties.avatarId.value(null),
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());
    }

    @Test
    public void values() {
        insertInto();
    }

    @Test
    public void from() {

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                ProjectProperties.projectId,
                                ProjectProperties.name
                        )
                )
                .from(ProjectProperties.TABLE);
        assertPrepareStatementEquals(
                "SELECT p.project_id, p.name FROM project p",
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .from(ProjectProperties.TABLE)
                .andWhere(UserProperties.currentProjectId.eq(ProjectProperties.projectId));
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u, project p WHERE u.current_project_id = p.project_id",
                sql.getPrepareStatement());


    }

    @Test
    public void joinOn() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .joinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId,
                                ProjectProperties.projectId
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u JOIN project p ON u.current_project_id = p.project_id",
                sql.getPrepareStatement());
    }

    @Test
    public void leftJoinOn() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .leftJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId,
                                ProjectProperties.projectId
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id",
                sql.getPrepareStatement());
    }

    @Test
    public void rightJoinOn() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .rightJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId,
                                ProjectProperties.projectId
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u RIGHT JOIN project p ON u.current_project_id = p.project_id",
                sql.getPrepareStatement());
    }

    @Test
    public void fullJoinOn() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .fullJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId,
                                ProjectProperties.projectId
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u FULL JOIN project p ON u.current_project_id = p.project_id",
                sql.getPrepareStatement());
    }

    @Test
    public void innerJoinOn() {
        Table region = new Table("region", "r");
        Column regionName = new Column(region, "name");
        Column regionParentCode = new Column(region, "parent_code");

        Table parentRegion = new Table("region", "pr");
        Column parentRegionCode = new Column(parentRegion, "code");
        Column parentRegionName = new Column(parentRegion, "name");

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                parentRegionName,
                                regionName
                        )
                )
                .from(region)
                .innerJoinOn(
                        parentRegion,
                        Arrays.asList(
                                regionParentCode,
                                parentRegionCode
                        )
                );
        assertPrepareStatementEquals(
                "SELECT pr.name, r.name FROM region r INNER JOIN region pr ON r.parent_code = pr.code",
                sql.getPrepareStatement());

    }


    @Test
    public void complexLeftJoinOn() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .complexLeftJoinOn(
                        ProjectProperties.TABLE,
                        Collections.singletonList(
                                UserProperties.currentProjectId.eq(ProjectProperties.projectId)
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id",
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .complexLeftJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId.eq(ProjectProperties.projectId),
                                ProjectProperties.name.like("小%")
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id AND p.name LIKE ?",
                Collections.singletonList(
                        ProjectProperties.name.value("小%")
                ),
                sql.getPrepareStatement());
    }

    @Test
    public void complexRightJoinOn() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .complexRightJoinOn(
                        ProjectProperties.TABLE,
                        Collections.singletonList(
                                UserProperties.currentProjectId.eq(ProjectProperties.projectId)
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u RIGHT JOIN project p ON u.current_project_id = p.project_id",
                sql.getPrepareStatement()
        );

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .complexRightJoinOn(ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId.eq(ProjectProperties.projectId),
                                ProjectProperties.name.like("小%"))
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u RIGHT JOIN project p ON u.current_project_id = p.project_id AND p.name LIKE ?",
                Collections.singletonList(
                        ProjectProperties.name.value("小%")
                ),
                sql.getPrepareStatement());
    }

    @Test
    public void complexFullJoinOn() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .complexFullJoinOn(ProjectProperties.TABLE,
                        Collections.singletonList(
                                UserProperties.currentProjectId.eq(ProjectProperties.projectId)
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u FULL JOIN project p ON u.current_project_id = p.project_id",
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .complexFullJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId.eq(ProjectProperties.projectId),
                                ProjectProperties.name.like("小%")
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u FULL JOIN project p ON u.current_project_id = p.project_id AND p.name LIKE ?",
                Collections.singletonList(
                        ProjectProperties.name.value("小%")
                ),
                sql.getPrepareStatement()
        );
    }


    @Test
    public void complexInnerJoinOn() {
        Table region = new Table("region", "r");
        Column regionName = new Column(region, "name");
        Column regionParentCode = new Column(region, "parent_code");

        Table parentRegion = new Table("region", "pr");
        Column parentRegionCode = new Column(parentRegion, "code");
        Column parentRegionName = new Column(parentRegion, "name");

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                parentRegionName,
                                regionName
                        )
                )
                .from(region)
                .complexInnerJoinOn(
                        parentRegion,
                        Collections.singletonList(
                                regionParentCode.eq(parentRegionCode)
                        )
                );
        assertPrepareStatementEquals(
                "SELECT pr.name, r.name FROM region r INNER JOIN region pr ON r.parent_code = pr.code",
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                parentRegionName,
                                regionName
                        )
                )
                .from(region)
                .complexInnerJoinOn(
                        parentRegion,
                        Arrays.asList(
                                regionParentCode.eq(parentRegionCode),
                                regionParentCode.eq("110")
                        )
                );
        assertPrepareStatementEquals(
                "SELECT pr.name, r.name FROM region r INNER JOIN region pr ON r.parent_code = pr.code AND r.parent_code = ?",
                Collections.singletonList(
                        regionParentCode.value("110")
                ),
                sql.getPrepareStatement());
    }


    @Test
    public void where() {

        List<CriteriaItem> xiaoLikeItems = Arrays.asList(
                UserProperties.name.like("小%"),
                UserProperties.email.like("xiao%"));

        CriteriaItem userIdItem = UserProperties.userId.gt(100);
        CriteriaItem type1Item = UserProperties.type.eq(1);
        CriteriaItem type2Item = UserProperties.type.eq(2);

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .where(
                        Criteria.ands(
                                Arrays.asList(
                                        Criteria.ors(xiaoLikeItems),
                                        userIdItem)
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?",
                Arrays.asList(
                        UserProperties.name.value("小%"),
                        UserProperties.email.value("xiao%"),
                        UserProperties.userId.value(100)
                ),
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                ).from(UserProperties.TABLE)
                .where(
                        Criteria.ands(
                                Arrays.asList(
                                        Criteria.ors(xiaoLikeItems),
                                        Criteria.ors(Collections.singletonList(userIdItem))
                                )
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?",
                Arrays.asList(
                        UserProperties.name.value("小%"),
                        UserProperties.email.value("xiao%"),
                        UserProperties.userId.value(100)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name)
                )
                .from(UserProperties.TABLE)
                .where(
                        Criteria.ors(Arrays.asList(
                                Criteria.ands(
                                        Arrays.asList(
                                                Criteria.ands(
                                                        Arrays.asList(
                                                                Criteria.ors(xiaoLikeItems),
                                                                Criteria.ors(Collections.singletonList(userIdItem)))
                                                ),
                                                type1Item)
                                ), type2Item))
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE (((u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?) AND u.type = ?) OR u.type = ?",
                Arrays.asList(
                        UserProperties.name.value("小%"),
                        UserProperties.email.value("xiao%"),
                        UserProperties.userId.value(100),
                        UserProperties.type.value(1),
                        UserProperties.type.value(2)
                ),
                sql.getPrepareStatement());
    }

    @Test
    public void andWhere() {

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.userId.eq(1))
                .limit(1);

        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .andWhere(
                        Criteria.ors(
                                Arrays.asList(
                                        UserProperties.name.like("小%"),
                                        UserProperties.email.like("xiao%"))
                        )
                )
                .limit(1);

        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.name.value("小%"),
                        UserProperties.email.value("xiao%"),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .andWhere(
                        Criteria.ors(
                                Arrays.asList(
                                        UserProperties.name.like("小%"),
                                        UserProperties.email.like("xiao%")
                                )
                        )
                )
                .andWhere(UserProperties.type.eq(1))
                .limit(1);
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) AND u.type = ? LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.name.value("小%"),
                        UserProperties.email.value("xiao%"),
                        UserProperties.type.value(1),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ), sql.getPrepareStatement());
    }

    @Test
    public void orWhere() {

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .orWhere(UserProperties.userId.eq(1))
                .limit(1);
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE u.user_id = ? LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                ).from(UserProperties.TABLE)
                .orWhere(
                        Criteria.ands(
                                Arrays.asList(
                                        UserProperties.name.like("小%"),
                                        UserProperties.email.like("xiao%")
                                )
                        )
                )
                .limit(1);
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? AND u.email LIKE ?) LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.name.value("小%"),
                        UserProperties.email.value("xiao%"),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .orWhere(
                        Criteria.ands(
                                Arrays.asList(
                                        UserProperties.name.like("小%"),
                                        UserProperties.email.like("xiao%"))
                        ))
                .orWhere(UserProperties.type.eq(1))
                .limit(1);
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? AND u.email LIKE ?) OR u.type = ? LIMIT ?, ?",
                Arrays.asList(
                        UserProperties.name.value("小%"),
                        UserProperties.email.value("xiao%"),
                        UserProperties.type.value(1),
                        getStartColumn().value(0),
                        getLimitColumn().value(1)),
                sql.getPrepareStatement());
    }

    @Test
    public void orderBy() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name)
                )
                .from(UserProperties.TABLE)
                .leftJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId,
                                ProjectProperties.projectId)
                )
                .orderBy(
                        Arrays.asList(
                                ProjectProperties.projectId.orderBy(),
                                UserProperties.userId.orderBy(true)
                        ))
                .limit(1000);
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id, u.user_id DESC LIMIT ?, ?",
                Arrays.asList(
                        getStartColumn().value(0),
                        getLimitColumn().value(1000)
                ),
                sql.getPrepareStatement());
    }


    @Test
    public void customerOrderBy() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name)
                )
                .from(UserProperties.TABLE)
                .leftJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId,
                                ProjectProperties.projectId
                        )
                )
                .orderBy(
                        Arrays.asList(
                                ProjectProperties.projectId.orderBy(),
                                UserProperties.userId.customerOrderBy(Arrays.asList(1, 2, 3), true)
                        )
                );
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                        + ", FIELD(u.user_id, ?, ?, ?) DESC",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        UserProperties.userId.value(2),
                        UserProperties.userId.value(3)
                ),
                sql.getPrepareStatement());
    }

    @Test
    public void nameOrderBy() {

        AggregateColumn minAge = UserProperties.age.min("min_age");
        AggregateColumn maxAge = UserProperties.age.max("max_age");

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                minAge,
                                maxAge,
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .andHaving(
                        minAge.egt(1)
                )
                .orderBy(
                        Arrays.asList(
                                UserProperties.gender.orderBy(),
                                minAge.orderBy(true)
                        )
                );
        assertPrepareStatementEquals(
                "SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? " +
                        "GROUP BY u.gender HAVING min_age >= ? ORDER BY u.gender, min_age DESC",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1)
                ),
                sql.getPrepareStatement());
    }

    @Test
    public void limit() {
        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .leftJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId,
                                ProjectProperties.projectId
                        )
                )
                .orderBy(
                        Arrays.asList(
                                ProjectProperties.projectId.orderBy(),
                                UserProperties.userId.orderBy(true)
                        )
                )
                .limit(10);
        assertPrepareStatementEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                        + ", u.user_id DESC LIMIT ?, ?",
                Arrays.asList(
                        getStartColumn().value(0),
                        getLimitColumn().value(10)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name,
                                ProjectProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .leftJoinOn(
                        ProjectProperties.TABLE,
                        Arrays.asList(
                                UserProperties.currentProjectId,
                                ProjectProperties.projectId)
                )
                .orderBy(
                        Arrays.asList(
                                ProjectProperties.projectId.orderBy(),
                                UserProperties.userId.orderBy(true)
                        )
                )
                .limit(10, 20);
        assertPrepareStatementEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
                        + ", u.user_id DESC LIMIT ?, ?",
                Arrays.asList(
                        getStartColumn().value(10),
                        getLimitColumn().value(20)
                ),
                sql.getPrepareStatement());
    }


    @Test
    public void increaseSet() {
        UpdateSql sql = SqlFactory.createUpdate()
                .update(UserProperties.TABLE)
                .set(UserProperties.loginTimes.increaseSet(2))
                .andWhere(UserProperties.userId.eq(1));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.login_times = u.login_times + ? WHERE u.user_id = ?",
                Arrays.asList(
                        UserProperties.loginTimes.value(2),
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());
    }


    @Test
    public void decreaseSet() {

        UpdateSql sql = SqlFactory.createUpdate()
                .update(UserProperties.TABLE)
                .set(UserProperties.loginTimes.decreaseSet(2))
                .andWhere(UserProperties.userId.eq(1));
        assertPrepareStatementEquals(
                "UPDATE user u SET u.login_times = u.login_times - ? WHERE u.user_id = ?",
                Arrays.asList(
                        UserProperties.loginTimes.value(2),
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());
    }

    @Test
    public void flag() {

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.userId.noFlgs(1));
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? = 0",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.userId.allFlgs(1));
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? = u.user_id",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.userId.anyFlgs(1));
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? <> 0",
                Collections.singletonList(
                        UserProperties.userId.value(1)
                ),
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.userId,
                                UserProperties.name
                        )
                )
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.userId.anyFlgs(1))
                .andWhere(UserProperties.name.like("%IBIT%"));
        assertPrepareStatementEquals(
                "SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? <> 0 AND u.name LIKE ?",
                Arrays.asList(
                        UserProperties.userId.value(1),
                        UserProperties.name.value("%IBIT%")
                ), sql.getPrepareStatement());

    }


    @Test
    public void having() {

        AggregateColumn minAge = UserProperties.age.min("min_age");
        AggregateColumn maxAge = UserProperties.age.max("max_age");

        SearchSql sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                minAge,
                                maxAge,
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .having(minAge.egt(1).and());
        assertPrepareStatementEquals(
                "SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ?",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1)
                ),
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                minAge,
                                maxAge,
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .andHaving(minAge.egt(1));
        assertPrepareStatementEquals(
                "SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ?",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1)
                ),
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                minAge,
                                maxAge,
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .having(
                        Arrays.asList(
                                minAge.egt(1).and(),
                                maxAge.egt(2).and()
                        ));
        assertPrepareStatementEquals(
                "SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ? AND max_age >= ?",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1),
                        maxAge.value(2)),
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                minAge,
                                maxAge,
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .having(
                        Criteria.ands(
                                Arrays.asList(
                                        minAge.egt(1),
                                        maxAge.egt(2)
                                )
                        )
                );
        assertPrepareStatementEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                        + "HAVING min_age >= ? AND max_age >= ?",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1),
                        maxAge.value(2)
                ),
                sql.getPrepareStatement());

        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                minAge,
                                maxAge,
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .having(
                        Arrays.asList(
                                minAge.egt(1).or(),
                                maxAge.egt(2).or()
                        )
                );
        assertPrepareStatementEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                        + "HAVING min_age >= ? OR max_age >= ?",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1),
                        maxAge.value(2)
                ),
                sql.getPrepareStatement());


        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                minAge,
                                maxAge,
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .having(
                        Criteria.ors(
                                Arrays.asList(
                                        minAge.egt(1),
                                        maxAge.egt(2)
                                )
                        ));
        assertPrepareStatementEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                        + "HAVING min_age >= ? OR max_age >= ?",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1),
                        maxAge.value(2)
                ),
                sql.getPrepareStatement());


        // 复杂的Having语句
        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                minAge,
                                maxAge,
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .andHaving(
                        Criteria.ors(
                                Arrays.asList(
                                        minAge.egt(1),
                                        maxAge.egt(2)
                                )
                        )
                )
                .andHaving(
                        Criteria.ors(
                                Arrays.asList(
                                        minAge.egt(3),
                                        maxAge.egt(4)
                                ))
                );
        assertPrepareStatementEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                        + "HAVING (min_age >= ? OR max_age >= ?) AND (min_age >= ? OR max_age >= ?)",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1),
                        maxAge.value(2),
                        minAge.value(3),
                        maxAge.value(4)
                ),
                sql.getPrepareStatement());


        // 复杂的Having语句
        sql = SqlFactory.createSearch()
                .column(
                        Arrays.asList(
                                UserProperties.age.min("min_age"),
                                UserProperties.age.max("max_age"),
                                UserProperties.gender
                        ))
                .from(UserProperties.TABLE)
                .andWhere(UserProperties.age.egt(0))
                .groupBy(UserProperties.gender)
                .orHaving(
                        Criteria.ands(
                                Arrays.asList(
                                        minAge.egt(1),
                                        maxAge.egt(2)
                                )
                        )
                )
                .orHaving(
                        Criteria.ands(
                                Arrays.asList(
                                        minAge.egt(3),
                                        maxAge.egt(4)
                                ))
                );
        assertPrepareStatementEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                        + "HAVING (min_age >= ? AND max_age >= ?) OR (min_age >= ? AND max_age >= ?)",
                Arrays.asList(
                        UserProperties.age.value(0),
                        minAge.value(1),
                        maxAge.value(2),
                        minAge.value(3),
                        maxAge.value(4)
                ),
                sql.getPrepareStatement());
    }
}