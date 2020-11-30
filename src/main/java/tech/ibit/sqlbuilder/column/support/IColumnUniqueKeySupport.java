package tech.ibit.sqlbuilder.column.support;

import tech.ibit.sqlbuilder.UniqueKey;

/**
 * IColumnUniqueKeySupport
 *
 * @author ben
 */
public interface IColumnUniqueKeySupport extends IColumnSupport {

    /**
     * 获取unique键对象
     *
     * @param value 值
     * @return unique键对象
     */
    default UniqueKey uniqueKey(Object value) {
        return new UniqueKey(getColumn().value(value));
    }

}
