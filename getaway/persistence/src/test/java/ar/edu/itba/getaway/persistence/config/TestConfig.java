package ar.edu.itba.getaway.persistence.config;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan({ "ar.edu.itba.getaway.persistence" })
@EnableTransactionManagement
@Configuration
public class TestConfig {

    @Value("classpath:hsqldb.sql")
    private Resource hsqldbSql;
    @Value("classpath:schema.sql")
    private Resource schemaSql;

//    @Bean
//    public DataSource dataSource() {
//        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
//        ds.setDriverClass(JDBCDriver.class);
//        //Corre en memoria y la bd se llama getaway
//        ds.setUrl("jdbc:hsqldb:mem:getaway");
//        ds.setUsername("ha");
//        ds.setPassword("");
//        return ds;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(final DataSource ds){
//        return new DataSourceTransactionManager(ds);
//    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds){
        final DataSourceInitializer dsInit = new DataSourceInitializer();
        dsInit.setDataSource(ds);
        dsInit.setDatabasePopulator(dataSourcePopulator());
        return dsInit;
    }

    private DatabasePopulator dataSourcePopulator(){
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScripts(hsqldbSql, schemaSql);//Esto lo que hace es que setee el modo compatibilidad con postgres.
        return dbp;
    }

    @Bean
    public DataSource dataSource() {
        final SingleConnectionDataSource ds = new SingleConnectionDataSource();

        ds.setSuppressClose(true);
        ds.setDriverClassName(JDBCDriver.class.getName());
        ds.setUrl("jdbc:hsqldb:mem:getaway");
        ds.setUsername("ha");
        ds.setPassword("");

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.getaway.models");
        factoryBean.setDataSource(dataSource());
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
//        properties.setProperty("format_sql", "true");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}

