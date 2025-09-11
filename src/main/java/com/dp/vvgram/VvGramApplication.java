package com.dp.vvgram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VvGramApplication {

    public static void main(String[] args) {
        SpringApplication.run(VvGramApplication.class, args);
    }

}
