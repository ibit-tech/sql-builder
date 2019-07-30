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
                .where(Arrays.asList("user_id > ?", "type = ?"), Arrays.asList(10, 1))
                .orderBy("user_id").limit(10);
        assertParamsEquals("SELECT * FROM user WHERE user_id > ? type = ? ORDER BY user_id LIMIT ?, ?", Arrays.asList(10, 1, 0, 10), sql.getSqlParams());
        assertParamsEquals("SELECT COUNT(*) FROM user WHERE user_id > ? type = ?", Arrays.asList(10, 1), sql.countSqlParams());


        sql = new StringSql()
                .select(Arrays.asList("type", "COUNT(type) typeTotal"))
                .from("user").where(Collections.singletonList("user_id > ?"), Collections.singletonList(100)).groupBy("type");
        assertParamsEquals("SELECT type, COUNT(type) typeTotal FROM user WHERE user_id > ? GROUP BY type", Collections.singletonList(100)
                , sql.getSqlParams());
    }

    @Test
    public void count() {
        StringSql sql = new StringSql()
                .count()
                .from("user")
                .where(Collections.singletonList("user_id > ?"), Collections.singletonList(100));
        assertParamsEquals("SELECT COUNT(*) FROM user WHERE user_id > ?", Collections.singletonList(100), sql.getSqlParams());
        assertParamsEquals("SELECT COUNT(*) FROM user WHERE user_id > ?", Collections.singletonList(100), sql.countSqlParams());
    }

    @Test
    public void countDistinct() {
        StringSql sql = new StringSql()
                .countDistinct("user_id")
                .from("user")
                .where(Collections.singletonList("user_id > ?"), Collections.singletonList(100));
        assertParamsEquals("SELECT COUNT(DISTINCT user_id) FROM user WHERE user_id > ?", Collections.singletonList(100), sql.getSqlParams());
        assertParamsEquals("SELECT COUNT(DISTINCT user_id) FROM user WHERE user_id > ?", Collections.singletonList(100), sql.countSqlParams());
    }

    @Test
    public void deleteFrom() {
        StringSql sql = new StringSql()
                .deleteFrom("user")
                .where(Collections.singletonList(CriteriaMaker.equalsTo("user_id")), Collections.singletonList(1));
        assertParamsEquals("DELETE FROM user WHERE user_id = ?", Collections.singletonList(1), sql.getSqlParams());
    }

    @Test
    public void insertInto() {
        StringSql sql = new StringSql()
                .insertInto("user")
                .values(Arrays.asList(1, "ben@ibit.tech", "ibit_tech@aliyun.com", "12345678", "188", 1));
        assertParamsEquals("INSERT INTO user VALUES(?, ?, ?, ?, ?, ?)", Arrays.asList(1, "ben@ibit.tech", "ibit_tech@aliyun.com", "12345678", "188", 1)
                , sql.getSqlParams());

        sql = new StringSql()
                .insertInto("user")
                .values(Arrays.asList("loginId", "email", "password", "mobilePhone", "type"),
                        Arrays.asList("ben@ibit.tech", "ibit_tech@aliyun.com", "12345678", "188", 1));
        assertParamsEquals("INSERT INTO user(loginId, email, password, mobilePhone, type) VALUES(?, ?, ?, ?, ?)"
                , Arrays.asList("ben@ibit.tech", "ibit_tech@aliyun.com", "12345678", "188", 1)
                , sql.getSqlParams());
    }

    @Test
    public void update() {
        StringSql sql = new StringSql()
                .update("user")
                .set(Collections.singletonList("email=?"), Collections.singletonList("ibittech@ibit.tech"))
                .where(Collections.singletonList("user_id=?"), Collections.singletonList(1));
        assertParamsEquals("UPDATE user SET email=? WHERE user_id=?", Arrays.asList("ibittech@ibit.tech", 1), sql.getSqlParams());
    }

    @Test
    public void batchInsertInto() {
        StringSql sql = new StringSql()
                .batchInsertInto("user")
                .values(Arrays.asList("email", "mobilePhone", "loginId"), Arrays.asList("ibit_tech@aliyun.com", "188", "loginId@ibit.tech"
                        , "ibit_tech@aliyun.com", "1881", "login_id1@ibit.tech"));
        assertParamsEquals("INSERT INTO user(email, mobilePhone, loginId) VALUES(?, ?, ?), (?, ?, ?)",
                Arrays.asList("ibit_tech@aliyun.com", "188", "loginId@ibit.tech", "ibit_tech@aliyun.com", "1881", "login_id1@ibit.tech"),
                sql.getSqlParams());
    }

    @Test
    public void batchInsertInto2() {
        StringSql sql = new StringSql()
                .batchInsertInto2("user")
                .values(Arrays.asList("email", "mobilePhone", "loginId"),
                        Arrays.asList("ibit_tech@aliyun.com", "188", "loginId@ibit.tech", "ibit_tech@aliyun.com1", "1881", "login_id1@ibit.tech"));
        assertParamsEquals("INSERT INTO user(email, mobilePhone, loginId) VALUES(?, ?, ?)",
                Arrays.asList(Arrays.asList("ibit_tech@aliyun.com", "188", "loginId@ibit.tech"), Arrays.asList("ibit_tech@aliyun.com1", "1881", "login_id1@ibit.tech")),
                sql.getSqlParams());
    }
}
