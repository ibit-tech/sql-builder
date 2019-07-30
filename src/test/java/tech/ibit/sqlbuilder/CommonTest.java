package tech.ibit.sqlbuilder;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author IBIT TECH
 */
public class CommonTest {

    /**
     * 断言参数相等
     *
     * @param sql       sql
     * @param params    参数
     * @param sqlParams sql参数对象
     */
    public void assertParamsEquals(String sql, List<Object> params, SqlParams sqlParams) {
        assertEquals(new SqlParams(sql, params).toString(), sqlParams.toString());
    }


    /**
     * 断言参数相等
     *
     * @param sql       sql
     * @param sqlParams sql参数对象
     */
    public void assertParamsEquals(String sql, SqlParams sqlParams) {
        assertEquals(new SqlParams(sql, Collections.emptyList()).toString(), sqlParams.toString());
    }
}
