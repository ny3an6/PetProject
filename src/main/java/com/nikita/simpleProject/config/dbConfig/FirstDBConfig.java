package com.nikita.simpleProject.config.dbConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.nikita.simpleProject")
@EnableJpaRepositories(entityManagerFactoryRef = "firstEntityManagerFactory", transactionManagerRef =
        "firstTransactionManager", basePackages = {"com.nikita.simpleProject.repository.firstrepository"})
public class FirstDBConfig {
    @Autowired
    private Environment env;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.first.datasource")
    public DataSourceProperties firstDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource firstDataSource() {
        DataSourceProperties firstDataSourceProperties = firstDataSourceProperties();
        return DataSourceBuilder.create()
                .driverClassName(firstDataSourceProperties.getDriverClassName())
                .url(firstDataSourceProperties.getUrl())
                .username(firstDataSourceProperties.getUsername())
                .password(firstDataSourceProperties.getPassword())
                .build();
    }

    @Bean(name = "firstEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(firstDataSource());
        factory.setPackagesToScan(new String[]{"com.nikita.simpleProject.model.first"});
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    @Bean(name = "firstTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("firstEntityManagerFactory") EntityManagerFactory
                                                                 firstEntityManagerFactory){
        return new JpaTransactionManager(firstEntityManagerFactory);
    }

    @Bean
    public DataSourceInitializer firstDataSourceInitializer()
    {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(firstDataSource());
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
       // databasePopulator.addScript(new ClassPathResource("first-data.sql"));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        //   dataSourceInitializer.setEnabled(env.getProperty("datasource.security.initialize", Boolean.class, false));
        return dataSourceInitializer;
    }
}
