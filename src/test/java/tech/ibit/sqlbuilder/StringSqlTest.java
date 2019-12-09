package tech.ibit.sqlbuilder;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author IBIT TECH
 * @version 1.0
 */
public class StringSqlTest extends CommonTest {


    @Test
    public void select() {
        StringSql sql = new StringSql()
                .select("*")
                .from("user")
                .where(Arrays.asList("user_id > ?", "type = ?"), getKeyValuePairs("user_id", 10, "type", 1))
                .orderBy("user_id").limit(10);
        assertParamsEquals("SELECT * FROM user WHERE user_id > ? type = ? ORDER BY user_id LIMIT ?, ?",
                Arrays.asList("user_id", 10, "type", 1, "$start", 0, "$limit", 10), sql.getSqlParams());
        assertParamsEquals("SELECT COUNT(*) FROM user WHERE user_id > ? type = ?", Arrays.asList("user_id", 10, "type", 1), sql.countSqlParams());


        sql = new StringSql()
                .select(Arrays.asList("type", "COUNT(type) typeTotal"))
                .from("user").where(Collections.singletonList("user_id > ?"), getKeyValuePairs("user_id", 100)).groupBy("type");
        assertParamsEquals("SELECT type, COUNT(type) typeTotal FROM user WHERE user_id > ? GROUP BY type", Arrays.asList("user_id", 100)
                , sql.getSqlParams());
    }

    @Test
    public void count() {
        StringSql sql = new StringSql()
                .count()
                .from("user")
                .where(Collections.singletonList("user_id > ?"),  getKeyValuePairs("user_id", 100));
        assertParamsEquals("SELECT COUNT(*) FROM user WHERE user_id > ?", Arrays.asList("user_id", 100), sql.getSqlParams());
        assertParamsEquals("SELECT COUNT(*) FROM user WHERE user_id > ?", Arrays.asList("user_id", 100), sql.countSqlParams());
    }

    @Test
    public void countDistinct() {
        StringSql sql = new StringSql()
                .countDistinct("user_id")
                .from("user")
                .where(Collections.singletonList("user_id > ?"), getKeyValuePairs("user_id", 100));
        assertParamsEquals("SELECT COUNT(DISTINCT user_id) FROM user WHERE user_id > ?", Arrays.asList("user_id", 100), sql.getSqlParams());
        assertParamsEquals("SELECT COUNT(DISTINCT user_id) FROM user WHERE user_id > ?", Arrays.asList("user_id", 100), sql.countSqlParams());
    }

    @Test
    public void deleteFrom() {
        StringSql sql = new StringSql()
                .deleteFrom("user")
                .where(Collections.singletonList(CriteriaMaker.equalsTo("user_id")), getKeyValuePairs("user_id", 1));
        assertParamsEquals("DELETE FROM user WHERE user_id = ?", Arrays.asList("user_id", 1), sql.getSqlParams());
    }

    @Test
    public void insertInto() {
        StringSql sql = new StringSql()
                .insertInto("user")
                .values(Arrays.asList(1, "ben@ibit.tech", "ibit_tech@aliyun.com", "12345678", "188", 1));
        assertParamsEquals("INSERT INTO user VALUES(?, ?, ?, ?, ?, ?)", 
                Arrays.asList(null, 1, null, "ben@ibit.tech", null, "ibit_tech@aliyun.com", null, "12345678", null, "188", null, 1)
                , sql.getSqlParams());

        sql = new StringSql()
                .insertInto("user")
                .values(Arrays.asList("login_id", "email", "password", "mobile_phone", "type"),
                        Arrays.asList("ben@ibit.tech", "ibit_tech@aliyun.com", "12345678", "188", 1));
        assertParamsEquals("INSERT INTO user(login_id, email, password, mobile_phone, type) VALUES(?, ?, ?, ?, ?)"
                , Arrays.asList("login_id", "ben@ibit.tech", "email", "ibit_tech@aliyun.com", "password", "12345678", "mobile_phone", "188", "type", 1)
                , sql.getSqlParams());
    }

    @Test
    public void update() {
        StringSql sql = new StringSql()
                .update("user")
                .set(Collections.singletonList("email=?"), getKeyValuePairs("email", "ibittech@ibit.tech"))
                .where(Collections.singletonList("user_id=?"), getKeyValuePairs("user_id", 1));
        assertParamsEquals("UPDATE user SET email=? WHERE user_id=?",
                Arrays.asList("email", "ibittech@ibit.tech", "user_id", 1), sql.getSqlParams());
    }

    @Test
    public void batchInsertInto() {
        StringSql sql = new StringSql()
                .batchInsertInto("user")
                .values(Arrays.asList("email", "mobile_phone", "login_id"), Arrays.asList("ibit_tech@aliyun.com", "188", "login_id@ibit.tech"
                        , "ibit_tech@aliyun.com", "1881", "login_id1@ibit.tech"));
        assertParamsEquals("INSERT INTO user(email, mobile_phone, login_id) VALUES(?, ?, ?), (?, ?, ?)",
                Arrays.asList("email", "ibit_tech@aliyun.com", "mobile_phone", "188", "login_id", "login_id@ibit.tech",
                        "email", "ibit_tech@aliyun.com", "mobile_phone", "1881", "login_id", "login_id1@ibit.tech"),
                sql.getSqlParams());
    }


    @Test
    public void joinOn() {
        StringSql sql = new StringSql()
                .select(Arrays.asList("u.name", "u.email", "p.name"))
                .from("user u")
                .joinOn("LEFT JOIN project p ON u.current_project_id = p.project_id");
        assertParamsEquals("SELECT u.name, u.email, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id", Collections.emptyList(),
                sql.getSqlParams());


        sql = new StringSql()
                .select(Arrays.asList("u.name", "u.email", "p.name"))
                .from("user u")
                .joinOn("LEFT JOIN project p ON u.current_project_id = p.project_id AND p.project_id = ?", getKeyValuePairs("p.project_id", 1));
        assertParamsEquals("SELECT u.name, u.email, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id AND p.project_id = ?",
                Arrays.asList("p.project_id", 1), sql.getSqlParams());
    }

}
