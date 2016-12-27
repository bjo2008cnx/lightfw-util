package org.lightfw.mock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class RedisMock extends ReidsClientAdapter {
    private static Map map = new ConcurrentHashMap();

    @Override
    public String get(String key) {
        return (String) map.get(key);
    }

    @Override
    public String set(String key, String value) {
        map.put(key, value);
        return value;
    }

    public Boolean exists(String key) {
        return map.keySet().contains(key);
    }

    @Override
    public Long del(String key) {
        map.remove(key);
        return 1L;
    }

    @Override
    public Long expire(String key, int seconds) {
        //TODO;
        return 0L;
    }

}