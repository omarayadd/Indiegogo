package com.postgresql.indiegogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/health")
    public String checkHealth() {
        try {
            // Try to perform a simple Redis operation (e.g., get a key)
            redisTemplate.opsForValue().set("Key", "Value");

            redisTemplate.opsForValue().get("health-check-key");
            return "Redis connection is healthy" + " " + redisTemplate.opsForValue().get("Key");
        } catch (Exception e) {
            // If there's an exception, return an error message
            return "Failed to connect to Redis: " + e.getMessage();
        }
    }
    
    @GetMapping("/getCache")
    public String chechCache() {
        try {
            // Try to perform a simple Redis operation (e.g., get a key)

            redisTemplate.opsForValue().get("health-check-key");
            return "Redis connection is healthy" + " " + redisTemplate.opsForValue().get("Key");
        } catch (Exception e) {
            // If there's an exception, return an error message
            return "Failed to connect to Redis: " + e.getMessage();
        }
    }
}
