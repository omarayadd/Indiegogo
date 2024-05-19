package com.postgresql.indiegogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class MyServiceTests {

	@Autowired
    public RedisTemplate<String, String> redisTemplate;

    @Test
    public void testCacheSetAndGet() {
    	// Given
        MyService myService = new MyService(redisTemplate);
        String key = "testKey";
        String value = "testValue";

        myService.setValue(key, value);
        
        // Then
        String cachedValue = myService.getValue(key);
        assertEquals(value, cachedValue);
    }
}
