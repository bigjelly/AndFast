package com.andfast.app.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 列表集合 操作工具类
 *
 * @author: boyuma
 * @datetime: 2020/6/16
 */
public class ListUtils {
    public static <T> int getCount(List<T> list) {
        if (null == list || list.isEmpty()) {
            return 0;
        }
        return list.size();
    }


    public static <T> T getItem(List<T> list, int position) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        if (position < 0 || position >= list.size()) {
            return null;
        }
        return list.get(position);
    }

    public static <T> T getLastItem(List<T> list) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        return getItem(list, list.size() - 1);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return (getCount(list) <= 0);
    }

    public static <T> boolean isEmpty(Set<T> set) {
        return (set == null || set.isEmpty());
    }

    public static <T> boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    public static <T> boolean addAll(List<T> allList, List<T> addList) {
        if (ListUtils.isEmpty(addList) || allList == null) {
            return false;
        }
        return allList.addAll(addList);
    }

    public static <T> T remove(List<T> list, int index) {
        if (ListUtils.isEmpty(list) || list == null || index < 0 || index >= list.size()) {
            return null;
        }
        return list.remove(index);
    }
}
