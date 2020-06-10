package tech.ibit.sqlbuilder.sql.field;

import lombok.Data;
import tech.ibit.sqlbuilder.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义列表字段
 *
 * @author IBIT程序猿
 * @version 2.0
 */
@Data
public class ListField<T> {

    private List<T> items = new ArrayList<>();


    /**
     * 增加对象
     *
     * @param item 对象
     */
    public void addItem(T item) {
        items.add(item);
    }

    /**
     * 批量增加对象
     *
     * @param items 对象集合
     */
    public void addItems(List<? extends T> items) {
        if (CollectionUtils.isNotEmpty(items)) {
            this.items.addAll(items);
        }
    }

}
