package com.dp.vvgram.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfigs {
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OpenAPI VVGram() {
        return new OpenAPI()
                .info(new Info().title("VVGram APIs")
                        .description("A user can signup, login, follow other users, unfollow other users," +
                                "Create posts, edits posts, delete posts, like posts, comment on posts," +
                                "check the feed.")
                        .contact(new Contact()
                                .name("Deviprasath")
                                .email("deviprasath17@gmail.com")
                                .url("https://github.com/dp7teen")));
    }

    @Bean
    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
