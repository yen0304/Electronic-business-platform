package com.example.demo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /*
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer());
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair) // 序列化方式
                .entryTtl(Duration.ofHours(1)); // 過期時間

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(defaultCacheConfig).build();
    }

     */

    @Bean
    /*
    RedisTemplate<key, value>一般來說都是以String, String資料儲存，但如果像我們要儲存使用者資訊，簡單說
    就是物件,就可以改成<String, Object>，
    再來是redisTemplate(RedisConnectionFactory factory)，固定寫法，連結工廠
    */
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //key值序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //默認的是二進制，但是出來的值會比較長，就是下面的jdk
        //redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        //再來是json格式的序列化，但是要把object傳進去
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>());
        //所以選擇這個，他也是json數據，但是不需要把object傳進去
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //固定寫法，注入連結工廠,不寫會報錯
        //hash設定，（hash就是用來儲存key&value的地方）
        //hash類型 key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash類型，value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
