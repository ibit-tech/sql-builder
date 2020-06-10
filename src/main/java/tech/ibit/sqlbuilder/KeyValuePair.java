package tech.ibit.sqlbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 键-值对
 *
 * @author IBIT程序猿
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class KeyValuePair {

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private Object value;
}
