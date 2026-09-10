package com.example.workorder_scheduler_service.config;

import javax.sql.DataSource;

import javax.sql.DataSource;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.example.workorder_scheduler_service.entity.WorkOrder;
import com.example.workorder_scheduler_service.repository.idb.IntermediateDBWorkOrderRepository;
import com.example.workorder_scheduler_service.repository.production.WorkOrderRepository;

@Configuration
@EnableJpaRepositories(
	    basePackages = "com.example.workorder_scheduler_service.repository.production",
	    entityManagerFactoryRef = "productionEntityManagerFactory",
	    transactionManagerRef = "productionTransactionManager"
	)
public class ProductionDBConfig {

    @Bean
    @ConfigurationProperties("production.datasource")
    public DataSourceProperties productionDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource productionDataSource() {
        return productionDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean productionEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("productionDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(WorkOrder.class)
                .persistenceUnit("production")
                .build();
    }

    @Bean
    public JpaTransactionManager productionTransactionManager(
            @Qualifier("productionEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}