package com.example.workorder_scheduler_service.config;

import java.util.HashMap;

import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaConfig {

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {

        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();

        return new EntityManagerFactoryBuilder(
                vendorAdapter,
                dataSource -> new HashMap<>(),
                null
        );
    }
}