package ar.edu.itba.getaway.webapp.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:mail/emailConfig.properties")
public class MailConfig implements ApplicationContextAware, EnvironmentAware {

    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    private static final String JAVA_MAIL_FILE = "classpath:mail/javaMail.properties";

    private static final String HOST = "mail.server.host";
    private static final String PORT = "mail.server.port";
    private static final String PROTOCOL = "mail.server.protocol";
    private static final String USERNAME = "mail.server.username";
    private static final String PASSWORD = "mail.server.password";
    private ApplicationContext applicationContext;
    private Environment environment;


    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }


    //SPRING + JAVAMAIL: JavaMailSender instance, configured via .properties files.
    @Bean
    public JavaMailSender mailSender() throws IOException {

        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Basic mail sender configuration, based on emailconfig.properties
        mailSender.setHost(this.environment.getProperty(HOST));
        mailSender.setPort(Integer.parseInt(this.environment.getProperty(PORT)));
        mailSender.setProtocol(this.environment.getProperty(PROTOCOL));
        mailSender.setUsername(this.environment.getProperty(USERNAME));
        mailSender.setPassword(this.environment.getProperty(PASSWORD));

        // JavaMail-specific mail sender configuration, based on javamail.properties
        // https://stackoverflow.com/questions/16115453/javamail-could-not-convert-socket-to-tls-gmail
        final Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.host", "smtp.gmail.com");
        javaMailProperties.put("mail.smtp.port", "587");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.starttls.required", "true");
        javaMailProperties.put("mail.smtp.quitwait", "false");
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

//        javaMailProperties.load(this.applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;

    }

    //Message externalization/internationalization for emails.
    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mail/messages");
        return messageSource;
    }

    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(2);
//        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/mail/html/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

}
