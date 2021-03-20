package com.nagarro.vaccnow.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.nagarro.vaccnow"})
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.nagarro.vaccnow.repo")
@EnableTransactionManagement
@EntityScan(basePackages = "com.nagarro.vaccnow.model.jpa")
public class VaccNowApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccNowApplication.class, args);
    }

}
