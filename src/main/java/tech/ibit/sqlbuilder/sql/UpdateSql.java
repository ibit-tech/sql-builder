package tech.ibit.sqlbuilder.sql;

import tech.ibit.sqlbuilder.sql.support.*;

/**
 * UpdateSql
 *
 * @author IBIT程序猿
 * @version 2.0
 */
public interface UpdateSql extends UpdateTableSupport<UpdateSql>,
        JoinOnSupport<UpdateSql>,
        SetSupport<UpdateSql>,
        WhereSupport<UpdateSql>,
        UseAliasSupport {
}
