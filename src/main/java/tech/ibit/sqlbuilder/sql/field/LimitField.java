package tech.ibit.sqlbuilder.sql.field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LimitField
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitField {

    /**
     * 开始位置
     */
    private int start = -1;

    /**
     * 条数
     */
    private int limit = -1;

    /**
     * 设置limit参数
     *
     * @param start 开始位置
     * @param limit 条数
     */
    public void limit(int start, int limit) {
        this.start = start;
        this.limit = limit;
    }

    /**
     * 设置limit参数
     *
     * @param limit 条数
     */
    public void limit(int limit) {
        limit(0, limit);
    }


}
