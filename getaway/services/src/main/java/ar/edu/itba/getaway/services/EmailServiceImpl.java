package ar.edu.itba.getaway.services;

import ar.edu.itba.interfaces.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String GETAWAY_EMAIL = "getawaypaw@gmail.com";

    @Async
    @Override
    public void sendMail(String template, String subject, Map<String, Object> variables, final Locale locale) throws MessagingException {
        final Context context = new Context(locale);
        context.setVariables(variables);

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        final String to = (String) variables.get("to");
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom(GETAWAY_EMAIL);
        mimeMessageHelper.setTo(to);

        final String htmlContent = htmlTemplateEngine.process(template, context);
        mimeMessageHelper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
        LOGGER.info("Sent email with subject {} from {} to {} using template {}", subject, GETAWAY_EMAIL, to, template);
    }
}
