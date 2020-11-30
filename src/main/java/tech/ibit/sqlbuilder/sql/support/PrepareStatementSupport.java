package tech.ibit.sqlbuilder.sql.support;

import tech.ibit.sqlbuilder.PrepareStatement;

/**
 * PrepareStatementSupport
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface PrepareStatementSupport {

    /**
     * 获取预查询SQL
     *
     * @return 预查询SQL
     */
    PrepareStatement getPrepareStatement();

}
