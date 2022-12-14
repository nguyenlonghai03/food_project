package com.cybersoft.food_project.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;



    @Configuration // đưa lên bean
    public class RedisConfig {

        @Value("${redis.host}")
        private String hostName;

        @Value("${redis.port}") // Để sử dụng bên file yml
        private int port;

        // Mở kết nối tời server redis
        @Bean
        public LettuceConnectionFactory lettuceConnectionFactory(){
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setHostName(hostName);
            configuration.setPort(port);
            return new LettuceConnectionFactory(configuration);
        }

        // Cấu hình template để thực hiện chức năng thêm dữ liệu vào
        @Bean
        @Primary // nếu xài nhiều cái thì thằng này sẽ chạy chính
        public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory lettuceConnectionFactory) {
            RedisTemplate<Object, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(lettuceConnectionFactory);
            return template;
        }
    }

