package tech.ibit.sqlbuilder.support;

import org.junit.Assert;
import org.junit.Test;
import tech.ibit.sqlbuilder.*;
import tech.ibit.sqlbuilder.demo.entity.property.OrganizationProperties;
import tech.ibit.sqlbuilder.enums.FullTextModeEnum;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * IColumnFullTextSupportTest
 *
 * @author iBit程序猿
 * @since 2.6
 */
public class IColumnFullTextSupportTest extends CommonTest {

    /**
     * 匹配值
     */
    private final String matchValue = "深圳iBit程序猿";

    /**
     * 查询列
     */
    private final Column testColumn = OrganizationProperties.name;

    /**
     * 其他列
     */
    private final IColumn[] testOtherColumns = new IColumn[]{
            OrganizationProperties.cityCode, OrganizationProperties.phone
    };

    /**
     * 测试所有字段
     */
    public final IColumn[] testAllColumns = new IColumn[]{
            testColumn,
            OrganizationProperties.cityCode,
            OrganizationProperties.phone
    };

    /**
     * 别名
     */
    private final String nameAs = "score";

    @Test
    public void fullText() {
        // 不指定mode
        FullTextColumn column = testColumn.fullText(matchValue, nameAs);
        assertEquals("MATCH(name) AGAINST(?)", column.getName());
        assertEquals("MATCH(o.name) AGAINST(?)", column.getNameWithTableAlias());
        assertEquals(nameAs, column.getNameAs());
        Assert.assertNull(column.getMode());
        Assert.assertArrayEquals(new IColumn[]{testColumn}, column.getColumns());

        // 指定boolean
        column = testColumn.fullText(matchValue, FullTextModeEnum.BOOLEAN, nameAs);
        assertEquals("MATCH(name) AGAINST(? IN BOOLEAN MODE)", column.getName());
        assertEquals("MATCH(o.name) AGAINST(? IN BOOLEAN MODE)", column.getNameWithTableAlias());
        assertEquals(nameAs, column.getNameAs());
        assertEquals(FullTextModeEnum.BOOLEAN, column.getMode());
        Assert.assertArrayEquals(new IColumn[]{testColumn}, column.getColumns());

        // 指定 natural language
        column = testColumn.fullText(matchValue, FullTextModeEnum.NATURAL_LANGUAGE, nameAs);
        assertEquals("MATCH(name) AGAINST(? IN NATURAL LANGUAGE MODE)", column.getName());
        assertEquals("MATCH(o.name) AGAINST(? IN NATURAL LANGUAGE MODE)", column.getNameWithTableAlias());
        assertEquals(nameAs, column.getNameAs());
        assertEquals(FullTextModeEnum.NATURAL_LANGUAGE, column.getMode());
        Assert.assertArrayEquals(new IColumn[]{testColumn}, column.getColumns());
    }

    @Test
    public void testFullText() {

        // 不指定mode
        FullTextColumn column = testColumn.fullText(
                testOtherColumns, matchValue, nameAs);
        assertEquals("MATCH(name, city_code, phone) AGAINST(?)", column.getName());
        assertEquals("MATCH(o.name, o.city_code, o.phone) AGAINST(?)", column.getNameWithTableAlias());
        assertEquals(nameAs, column.getNameAs());
        Assert.assertNull(column.getMode());
        Assert.assertArrayEquals(
                testAllColumns,
                column.getColumns()
        );

        // 指定boolean
        column = testColumn.fullText(
                testOtherColumns, matchValue, FullTextModeEnum.BOOLEAN, nameAs);
        assertEquals("MATCH(name, city_code, phone) AGAINST(? IN BOOLEAN MODE)", column.getName());
        assertEquals("MATCH(o.name, o.city_code, o.phone) AGAINST(? IN BOOLEAN MODE)", column.getNameWithTableAlias());
        assertEquals(nameAs, column.getNameAs());
        assertEquals(FullTextModeEnum.BOOLEAN, column.getMode());
        Assert.assertArrayEquals(
                testAllColumns,
                column.getColumns()
        );

        // 指定 natural language
        column = testColumn.fullText(
                testOtherColumns, matchValue, FullTextModeEnum.NATURAL_LANGUAGE, nameAs);
        assertEquals("MATCH(name, city_code, phone) AGAINST(? IN NATURAL LANGUAGE MODE)", column.getName());
        assertEquals("MATCH(o.name, o.city_code, o.phone) AGAINST(? IN NATURAL LANGUAGE MODE)", column.getNameWithTableAlias());
        assertEquals(nameAs, column.getNameAs());
        assertEquals(FullTextModeEnum.NATURAL_LANGUAGE, column.getMode());
        Assert.assertArrayEquals(
                testAllColumns,
                column.getColumns()
        );

    }

    @Test
    public void fullTextMatch() {

        // 不指定mode
        CriteriaItem criteriaItem = testColumn.fullTextMatch(matchValue);
        PrepareStatement statement = criteriaItem.getPrepareStatement(true);
        assertPrepareStatementEquals("MATCH(o.name) AGAINST(?)",
                Collections.singletonList(testColumn.fullText(matchValue).value()), statement
        );

        // boolean 模式
        criteriaItem = testColumn.fullTextMatch(matchValue, FullTextModeEnum.BOOLEAN);
        statement = criteriaItem.getPrepareStatement(true);
        assertPrepareStatementEquals("MATCH(o.name) AGAINST(? IN BOOLEAN MODE)",
                Collections.singletonList(testColumn.fullText(matchValue, FullTextModeEnum.BOOLEAN).value()), statement
        );

        // boolean 模式
        criteriaItem = testColumn.fullTextMatch(matchValue, FullTextModeEnum.NATURAL_LANGUAGE);
        statement = criteriaItem.getPrepareStatement(true);
        assertPrepareStatementEquals("MATCH(o.name) AGAINST(? IN NATURAL LANGUAGE MODE)",
                Collections.singletonList(testColumn.fullText(matchValue, FullTextModeEnum.NATURAL_LANGUAGE).value()), statement
        );
    }

    @Test
    public void testFullTextMatch() {
        // 不指定mode
        CriteriaItem criteriaItem = testColumn.fullTextMatch(testOtherColumns, matchValue);
        PrepareStatement statement = criteriaItem.getPrepareStatement(true);
        assertPrepareStatementEquals("MATCH(o.name, o.city_code, o.phone) AGAINST(?)",
                Collections.singletonList(testColumn.fullText(testOtherColumns, matchValue).value()), statement
        );

        // boolean 模式
        criteriaItem = testColumn.fullTextMatch(testOtherColumns, matchValue, FullTextModeEnum.BOOLEAN);
        statement = criteriaItem.getPrepareStatement(true);
        assertPrepareStatementEquals("MATCH(o.name, o.city_code, o.phone) AGAINST(? IN BOOLEAN MODE)",
                Collections.singletonList(testColumn.fullText(testOtherColumns, matchValue, FullTextModeEnum.BOOLEAN).value()), statement
        );

        // boolean 模式
        criteriaItem = testColumn.fullTextMatch(testOtherColumns, matchValue, FullTextModeEnum.NATURAL_LANGUAGE);
        statement = criteriaItem.getPrepareStatement(true);
        assertPrepareStatementEquals("MATCH(o.name, o.city_code, o.phone) AGAINST(? IN NATURAL LANGUAGE MODE)",
                Collections.singletonList(testColumn.fullText(testOtherColumns, matchValue, FullTextModeEnum.NATURAL_LANGUAGE).value()), statement
        );
    }
}