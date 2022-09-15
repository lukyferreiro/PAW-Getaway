package ar.edu.itba.getaway.services;

import javax.mail.MessagingException;
import java.util.Locale;
import java.util.Map;

public interface EmailService {
    void sendMail (String template, String subject, Map<String, Object> variables, final Locale locale) throws MessagingException;
}
