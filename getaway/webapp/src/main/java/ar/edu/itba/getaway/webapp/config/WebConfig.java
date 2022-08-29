package ar.edu.itba.getaway.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;

@EnableWebMvc
@ComponentScan({"ar.edu.itba.getaway.webapp.controller", "ar.edu.itba.getaway.services",  "ar.edu.itba.getaway.persistence"})
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver(){
        final InternalResourceViewResolver vr = new InternalResourceViewResolver();

        vr.setViewClass(JstlView.class);
        vr.setPrefix("/WEB-INF/views/");
        vr.setSuffix(".jsp");

        return vr;
    }

//    @Bean
//    public DataSource dataSource(){
//        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
//
//        ds.setDriverClass(org.postgresql.Driver.class);
//        ds.setUrl("jdbc:postgresql://localhost/paw");
//        ds.setUsername("root");
//        ds.setPassword("root");
//
//        return ds;
//    }


    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    }
}
