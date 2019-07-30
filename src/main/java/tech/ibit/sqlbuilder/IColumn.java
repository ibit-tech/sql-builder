package tech.ibit.sqlbuilder;

/**
 * 定义列接口
 *
 * @author IBIT TECH
 * @version 1.0
 */
public interface IColumn {

    /**
     * 获取列名称
     *
     * @return 列名称
     */
    String getName();

    /**
     * 获取带表别名的列名称
     *
     * @return 列名称
     */
    String getNameWithTableAlias();


    /**
     * 获取列后面的`as`名称
     *
     * @return 列后面的`as`名称
     */
    String getNameAs();

}
