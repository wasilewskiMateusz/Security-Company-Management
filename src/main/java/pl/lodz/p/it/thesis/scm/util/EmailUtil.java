package pl.lodz.p.it.thesis.scm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Component
public class EmailUtil {

    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    private static final String AVAILABILITY_MESSAGE_SUBJECT = "email.availability.subject";
    private static final String RESET_PASSWORD_MESSAGE_SUBJECT = "email.reset.password.subject";
    private static final String REGISTRATION_MESSAGE_SUBJECT = "email.registration.subject";

    private static final String ENABLED_MESSAGE = "email.enable.message";
    private static final String DISABLED_MESSAGE = "email.disabled.message";
    private static final String RESET_PASSWORD_MESSAGE = "email.reset.password.message";
    private static final String REGISTRATION_MESSAGE = "email.registration.message";

    private static final String PERSONAL = "email.personal";
    private static final String EMAIL = "SCManagement.pl";


    @Autowired
    public EmailUtil(JavaMailSender mailSender, MessageSource messageSource) {
        this.mailSender = mailSender;
        this.messageSource = messageSource;
    }

    public void sendResetEmail(String recipientEmail, String link, Locale locale)
            throws MessagingException, UnsupportedEncodingException {

        String subject = messageSource.getMessage(RESET_PASSWORD_MESSAGE_SUBJECT, null, locale);

        String content = messageSource.getMessage(RESET_PASSWORD_MESSAGE, new Object[]{link}, locale);

        prepareMessage(recipientEmail, locale, subject, content);
    }

    public void sendEnabledEmail(String recipientEmail, Locale locale)
            throws MessagingException, UnsupportedEncodingException {

        String subject = messageSource.getMessage(AVAILABILITY_MESSAGE_SUBJECT, null, locale);

        String content = messageSource.getMessage(ENABLED_MESSAGE, null, locale);

        prepareMessage(recipientEmail, locale, subject, content);
    }

    public void sendDisabledEmail(String recipientEmail, Locale locale)
            throws MessagingException, UnsupportedEncodingException {

        String subject = messageSource.getMessage(AVAILABILITY_MESSAGE_SUBJECT, null, locale);
        String content = messageSource.getMessage(DISABLED_MESSAGE, null, locale);

        prepareMessage(recipientEmail, locale, subject, content);
    }

    public void sendRegistrationEmail(String recipientEmail, Locale locale)
            throws MessagingException, UnsupportedEncodingException {

        String subject = messageSource.getMessage(REGISTRATION_MESSAGE_SUBJECT, null, locale);
        String content = messageSource.getMessage(REGISTRATION_MESSAGE, null, locale);

        prepareMessage(recipientEmail, locale, subject, content);
    }

    private void prepareMessage(String recipientEmail, Locale locale, String subject, String content) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(EMAIL, messageSource.getMessage(PERSONAL, null, locale));
        helper.setTo(recipientEmail);

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
