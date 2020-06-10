package tech.ibit.sqlbuilder.sql;

import tech.ibit.sqlbuilder.sql.support.InsertTableSupport;
import tech.ibit.sqlbuilder.sql.support.UseAliasSupport;
import tech.ibit.sqlbuilder.sql.support.ValuesSupport;

/**
 * InsertSql
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface InsertSql extends InsertTableSupport<InsertSql>,
        ValuesSupport<InsertSql>,
        UseAliasSupport {
}
