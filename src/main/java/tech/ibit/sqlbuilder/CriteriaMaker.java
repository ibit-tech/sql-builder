package tech.ibit.sqlbuilder;

/**
 * 条件内容字串创建类
 *
 * @author IBIT程序猿
 * @version 1.0
 */
public class CriteriaMaker {

    private CriteriaMaker() {
    }

    /**
     * 构造"IS NULL"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String isNull(String column) {
        return column + " IS NULL";
    }

    /**
     * 构造"IS NOT NULL"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String isNotNull(String column) {
        return column + " IS NOT NULL";
    }


    /**
     * 构造"相等"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String eq(String column) {
        return column + " = ?";
    }

    /**
     * 构造"相等"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容字串
     */
    public static String eq(String column, String secondColumn) {
        return column + " = " + secondColumn;
    }

    /**
     * 构造"不等"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String neq(String column) {
        return column + " <> ?";
    }

    /**
     * 构造"不等"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容字串
     */
    public static String neq(String column, String secondColumn) {
        return column + " <> " + secondColumn;
    }

    /**
     * 构造"大于"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String gt(String column) {
        return column + " > ?";
    }

    /**
     * 构造"大于"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容字串
     */
    public static String gt(String column, String secondColumn) {
        return column + " > " + secondColumn;
    }

    /**
     * 构造"大于等于"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String egt(String column) {
        return column + " >= ?";
    }

    /**
     * 构造"大于等于"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容字串
     */
    public static String egt(Column column, Column secondColumn) {
        return column + " >= " + secondColumn;
    }

    /**
     * 构造"小于"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String lt(String column) {
        return column + "< ?";
    }

    /**
     * 构造"小于"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容字串
     */
    public static String lt(String column, String secondColumn) {
        return column + " < " + secondColumn;
    }

    /**
     * 构造"小于等于"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String elt(String column) {
        return column + " <= ?";
    }

    /**
     * 构造"小于等于"条件内容
     *
     * @param column       列
     * @param secondColumn 第二列
     * @return 条件内容字串
     */
    public static String elt(String column, String secondColumn) {
        return column + " <= " + secondColumn;
    }

    /**
     * 构造"IN"条件内容
     *
     * @param column 列
     * @param size   值长度
     * @return 条件内容字串
     */
    public static String in(String column, int size) {
        return column + " IN(" + getIn(size) + ")";
    }

    /**
     * 构造"NOT IN"条件内容
     *
     * @param column 列
     * @param size   值长度
     * @return 条件内容字串
     */
    public static String notIn(String column, int size) {
        return column + " NOT IN(" + getIn(size) + ")";
    }

    /**
     * 构造"?"语句
     *
     * @param size 值长度
     * @return "?"语句
     */
    public static String getIn(int size) {
        StringBuilder in = new StringBuilder();
        for (int i = 0; i < size; i++) {
            in.append(", ?");
        }
        if (in.length() > 0) {
            return in.substring(2);
        }
        return in.toString();
    }

    /**
     * 构造"BETWEEN AND"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String between(String column) {
        return column + " BETWEEN ? AND ?";
    }

    /**
     * 构造"NOT BETWEEN AND"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String notBetween(String column) {
        return column + " NOT BETWEEN ? AND ?";
    }

    /**
     * 构造"LIKE"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String like(String column) {
        return column + " LIKE ?";
    }

    /**
     * 构造"NOT LIKE"条件内容
     *
     * @param column 列
     * @return 条件内容字串
     */
    public static String notLike(String column) {
        return column + " NOT LIKE ?";
    }


    /**
     * 构造全包含标记位条件内容
     *
     * @param column 列
     * @return 标记位条件内容
     */
    public String allFlgs(String column) {
        return column + " & ? = " + column;
    }


    /**
     * 构造全不包含标记位条件内容
     *
     * @param column 列
     * @return 标记位条件内容
     */
    public String noFlgs(String column) {
        return column + "& ? = 0";
    }

    /**
     * 构造全包含任意标记位条件内容
     *
     * @param column 列
     * @return 标记位条件内容
     */
    public String anyFlgs(String column) {
        return column + " & ? <> 0";
    }
}
