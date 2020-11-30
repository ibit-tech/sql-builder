package tech.ibit.sqlbuilder.column.support;

import org.apache.commons.lang.ArrayUtils;
import tech.ibit.sqlbuilder.CriteriaItem;
import tech.ibit.sqlbuilder.FullTextColumn;
import tech.ibit.sqlbuilder.IColumn;
import tech.ibit.sqlbuilder.enums.FullTextModeEnum;

/**
 * 列全文搜索支持
 *
 * @author iBit程序猿
 * @since 2.6
 */
public interface IColumnFullTextSupport extends IColumnSupport {

    /**
     * 获取全文索引列
     *
     * @param value 索引值
     * @return 全文索引列
     */
    default FullTextColumn fullText(String value) {
        return fullText(value, (String) null);
    }

    /**
     * 获取全文索引列
     *
     * @param otherColumns 其他列
     * @param value        索引值
     * @return 全文索引列
     */
    default FullTextColumn fullText(IColumn[] otherColumns, String value) {
        return fullText(otherColumns, value, (String) null);
    }

    /**
     * 获取全文索引列
     *
     * @param value 索引值
     * @param mode  全文查询模式
     * @return 全文索引列
     */
    default FullTextColumn fullText(String value, FullTextModeEnum mode) {
        return fullText(value, mode, null);
    }

    /**
     * 获取全文索引列
     *
     * @param otherColumns 其他列
     * @param value        索引值
     * @param mode         全文查询模式
     * @return 全文索引列
     */
    default FullTextColumn fullText(IColumn[] otherColumns, String value, FullTextModeEnum mode) {
        return fullText(otherColumns, value, mode, null);
    }

    /**
     * 获取全文索引列
     *
     * @param value  索引值
     * @param nameAs 列别名
     * @return 全文索引列
     */
    default FullTextColumn fullText(String value, String nameAs) {
        return new FullTextColumn(new IColumn[]{getColumn()}, value, nameAs);
    }

    /**
     * 获取全文索引列
     *
     * @param otherColumns 其他列
     * @param value        索引值
     * @param nameAs       列别名
     * @return 全文索引列
     */
    default FullTextColumn fullText(IColumn[] otherColumns, String value, String nameAs) {
        return new FullTextColumn((IColumn[]) ArrayUtils.addAll(new IColumn[]{getColumn()}, otherColumns), value, nameAs);
    }

    /**
     * 获取全文索引列
     *
     * @param value  索引值
     * @param mode   全文查询模式
     * @param nameAs 列别名
     * @return 全文索引列
     */
    default FullTextColumn fullText(String value, FullTextModeEnum mode, String nameAs) {
        return new FullTextColumn(new IColumn[]{getColumn()}, value, mode, nameAs);
    }

    /**
     * 获取全文索引列
     *
     * @param otherColumns 其他列
     * @param value        索引值
     * @param mode         全文查询模式
     * @param nameAs       列别名
     * @return 全文索引列
     */
    default FullTextColumn fullText(IColumn[] otherColumns, String value, FullTextModeEnum mode, String nameAs) {
        return new FullTextColumn((IColumn[]) ArrayUtils.addAll(new IColumn[]{getColumn()}, otherColumns), value, mode, nameAs);
    }

    /**
     * 获取全文索引条件
     *
     * @param value 索引值
     * @return 全文索引条件
     */
    default CriteriaItem fullTextMatch(String value) {
        return fullText(value).match();
    }

    /**
     * 获取全文索引条件
     *
     * @param otherColumns 其他列
     * @param value        索引值
     * @return 全文索引条件
     */
    default CriteriaItem fullTextMatch(IColumn[] otherColumns, String value) {
        return fullText(otherColumns, value).match();
    }

    /**
     * 获取全文索引条件
     *
     * @param value 索引值
     * @param mode  全文查询模式
     * @return 全文索引条件
     */
    default CriteriaItem fullTextMatch(String value, FullTextModeEnum mode) {
        return fullText(value, mode).match();
    }

    /**
     * 获取全文索引条件
     *
     * @param otherColumns 其他列
     * @param value        索引值
     * @param mode         全文查询模式
     * @return 全文索引条件
     */
    default CriteriaItem fullTextMatch(IColumn[] otherColumns, String value, FullTextModeEnum mode) {
        return fullText(otherColumns, value, mode).match();
    }

}
