package com.example.workorder_scheduler_service.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.example.workorder_scheduler_service.entity.IntermediateDBWorkOrder;
import com.example.workorder_scheduler_service.repository.idb.IntermediateDBWorkOrderRepository;

@Configuration
@EnableJpaRepositories(
	    basePackages = "com.example.workorder_scheduler_service.repository.idb",
	    entityManagerFactoryRef = "idbEntityManagerFactory",
	    transactionManagerRef = "idbTransactionManager"
	)
public class IDBConfig {

    @Bean
    @ConfigurationProperties("idb.datasource")
    public DataSourceProperties idbDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource idbDataSource() {
        return idbDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean idbEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("idbDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(IntermediateDBWorkOrder.class)
                .persistenceUnit("idb")
                .build();
    }

    @Bean
    public JpaTransactionManager idbTransactionManager(
            @Qualifier("idbEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}