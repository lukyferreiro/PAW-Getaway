package ar.edu.itba.getaway.webapp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@EnableTransactionManagement
@EnableAsync
@ComponentScan({"ar.edu.itba.getaway.webapp.controller", "ar.edu.itba.getaway.services", "ar.edu.itba.getaway.persistence"})
@Configuration
public class WebConfig {

    private static final int MAX_SIZE_PER_FILE = 10000000;

    @Bean(name = "appBaseUrl")
    public String appBaseUrl() {
//        return "localhost";
        return "pawserver.it.itba.edu.ar";
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds((int) TimeUnit.SECONDS.toSeconds(5));
        return messageSource;
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);

        //Usuario Local
//        ds.setUrl("jdbc:postgresql://localhost/postgres");
//        ds.setUsername("postgres");
//        ds.setPassword("getawaydb");
        //Usuario Heroku
//        ds.setUrl("jdbc:postgresql://ec2-54-204-241-136.compute-1.amazonaws.com:5432/d38a8rs1b2dpeh");
//        ds.setUsername("adrzztklademib");
//        ds.setPassword("580c8ba69151e9ba288d107d1b28f9dfc3706838eccbfb4d4d9ca1cde2f6f86e");
        //Usuario PAW
        ds.setUrl("jdbc:postgresql://10.16.1.110/paw-2022b-1");
        ds.setUsername("paw-2022b-1");
        ds.setPassword("qo16kZtyI");

        return ds;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSizePerFile(MAX_SIZE_PER_FILE);
        multipartResolver.setMaxUploadSize(MAX_SIZE_PER_FILE * 6);
        multipartResolver.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return multipartResolver;
    }

    // Cambiamos el transaction manager por uno que entienda de JPA
    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    //Para definir nuestro entity manager usando JPA, y Hibernate como implementación concreta
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.getaway.models");
        factoryBean.setDataSource(dataSource());
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        // TODO comentar esta linea para produccion
//        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("format_sql", "true");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

}
