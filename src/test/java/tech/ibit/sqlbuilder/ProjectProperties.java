package tech.ibit.sqlbuilder;

/**
 * DbTable for sz_project
 *
 * @author IBIT TECH
 * @version 1.0
 */
public interface ProjectProperties {

    Table TABLE_NAME = new Table("sz_project", "p");

    Column projectId = new Column(TABLE_NAME, "project_id");
    Column name = new Column(TABLE_NAME, "name");

}
