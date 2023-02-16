package ar.edu.itba.getaway.webapp.config;

import ar.edu.itba.getaway.webapp.constraints.DtoConstraintValidator;
import ar.edu.itba.getaway.webapp.security.services.AuthFacade;
import ar.edu.itba.getaway.webapp.security.services.AuthFacadeImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validator;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@EnableTransactionManagement
@EnableAsync
@ComponentScan({"ar.edu.itba.getaway.webapp.controller", "ar.edu.itba.getaway.services", "ar.edu.itba.getaway.persistence"})
@Configuration
public class WebConfig {

    private static final Integer MAX_SIZE_PER_FILE = 10000000;
    private static final Integer MAX_REQUEST_SIZE = 3500000;

    private static final boolean DEV_BUILD = true;

    private static boolean isOnDevBuild() {
        return DEV_BUILD;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds((int) TimeUnit.SECONDS.toSeconds(5));
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);

        //Usuario Heroku
//        ds.setUrl("jdbc:postgresql://ec2-54-204-241-136.compute-1.amazonaws.com:5432/d38a8rs1b2dpeh");
//        ds.setUsername("adrzztklademib");
//        ds.setPassword("580c8ba69151e9ba288d107d1b28f9dfc3706838eccbfb4d4d9ca1cde2f6f86e");

        if (isOnDevBuild()) {
            ds.setUrl("jdbc:postgresql://localhost:5435/postgres");
            ds.setUsername("postgres");
            ds.setPassword("getawaydb");
        } else {
            ds.setUrl("jdbc:postgresql://10.16.1.110/paw-2022b-1");
            ds.setUsername("paw-2022b-1");
            ds.setPassword("qo16kZtyI");
       }

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
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
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        if (isOnDevBuild()) {
            properties.setProperty("hibernate.show_sql", "true");
        }
        properties.setProperty("format_sql", "true");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSizePerFile(MAX_SIZE_PER_FILE);
        multipartResolver.setMaxUploadSize(MAX_SIZE_PER_FILE * 6);
        multipartResolver.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return multipartResolver;
    }

    @Bean(name = "maxRequestSize")
    public Integer maxRequestSize() {
        return MAX_REQUEST_SIZE;
    }

    @Bean(name = "appBaseUrl")
    public URL appBaseUrl() throws MalformedURLException {
        if (isOnDevBuild()) {
            return new URL("http", "localhost", 3000, "/");
        } else {
            return new URL("http", "pawserver.it.itba.edu.ar", "/paw-2022b-1");
        }
    }

    @Bean
    public ObjectMapper objectMapper() {
        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jacksonMessageConverter.getObjectMapper();

        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new ParameterNamesModule());

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return jacksonMessageConverter.getObjectMapper();
    }

    @Bean
    public AuthFacade authFacade() {
        return new AuthFacadeImpl();
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public DtoConstraintValidator dtoConstraintValidator() { return new DtoConstraintValidator(); }
}
