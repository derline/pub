package sjj.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Object[] objects) {
        return (objects == null || objects.length == 0);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static Map<String, Object> hashMap(String keyWithComma, Object... values) {
        String[] keys = keyWithComma.split(",");
        if (keys.length != values.length) {
            throw new RuntimeException("map参数错误");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i].trim(), values[i]);
        }

        return map;
    }

}
