package com.postgresql.indiegogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MyService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public MyService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    public String removeValue(String key) {
        return redisTemplate.opsForValue().getAndDelete(key);
    }

    public Map<String, String> getAllData() {
        Map<String, String> allData = new HashMap<>();
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            for (String key : keys) {
                String value = redisTemplate.opsForValue().get(key);
                allData.put(key, value);
            }
        }
        return allData;
    }
}
