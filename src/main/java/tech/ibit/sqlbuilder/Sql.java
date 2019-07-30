package tech.ibit.sqlbuilder;

/**
 * SQL构造对象
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class Sql extends AbstractSql<Sql> {

    /**
     * 返回自身对象
     *
     * @return SQL构造对象
     */
    @Override
    Sql getSql() {
        return this;
    }
}
