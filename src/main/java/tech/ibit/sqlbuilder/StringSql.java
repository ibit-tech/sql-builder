package tech.ibit.sqlbuilder;

/**
 * 字符串SQL构造对象
 *
 * @author IBIT TECH
 * @version 1.0
 */
public class StringSql extends AbstractStringSql<StringSql> {

    /**
     * 返回自身对象
     *
     * @return SQL构造对象
     */
    @Override
    StringSql getSql() {
        return this;
    }
}
