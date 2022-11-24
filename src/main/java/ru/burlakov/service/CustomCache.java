package ru.burlakov.service;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class CustomCache {
    private Map<String, Integer> cache;

    public CustomCache() {
        this.cache = new HashMap<>();
    }

    public void putData(String key) {
        if (cache.containsKey(key)) {
            cache.put(key, cache.get(key) + 1);
        } else cache.put(key, 1);
    }

    public Integer getData(String key) {
        return cache.getOrDefault(key, null);
    }

    public void clear() {
        cache.clear();
    }

    @Override
    public java.lang.String toString() {
        return cache.entrySet().toString();
    }
}
