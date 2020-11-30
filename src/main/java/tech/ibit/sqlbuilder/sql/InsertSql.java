package tech.ibit.sqlbuilder.sql;

import tech.ibit.sqlbuilder.sql.support.InsertTableSupport;
import tech.ibit.sqlbuilder.sql.support.OnDuplicateKeyUpdateSupport;
import tech.ibit.sqlbuilder.sql.support.PrepareStatementSupport;
import tech.ibit.sqlbuilder.sql.support.ValuesSupport;

/**
 * InsertSql
 *
 * @author iBit程序猿
 * @version 2.0
 */
public interface InsertSql extends InsertTableSupport<InsertSql>,
        ValuesSupport<InsertSql>,
        OnDuplicateKeyUpdateSupport<InsertSql>,
        PrepareStatementSupport {

}
