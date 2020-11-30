package tech.ibit.sqlbuilder.demo.entity.property;

import tech.ibit.sqlbuilder.Column;
import tech.ibit.sqlbuilder.Table;

/**
 * DbTable for sz_project
 *
 * @author iBit程序猿
 * @version 1.0
 */
public interface ProjectProperties {

    Table TABLE = new Table("project", "p");

    Column projectId = new Column(TABLE, "project_id");
    Column name = new Column(TABLE, "name");

}
