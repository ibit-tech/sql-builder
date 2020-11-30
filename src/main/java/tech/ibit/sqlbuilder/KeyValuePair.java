package tech.ibit.sqlbuilder;

/**
 * 键-值对
 *
 * @author iBit程序猿
 * @version 1.0
 */
public class KeyValuePair {

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private Object value;

    /**
     * 构造函数
     *
     * @param key   键
     * @param value 值
     */
    public KeyValuePair(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the value of key
     *
     * @return the value of key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key
     * <p>You can use getKey() to get the value of key</p>
     *
     * @param key key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value
     * <p>You can use getValue() to get the value of value</p>
     *
     * @param value value
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
