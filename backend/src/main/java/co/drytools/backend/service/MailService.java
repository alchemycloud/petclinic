package co.drytools.backend.service;

import co.drytools.backend.config.CustomProperties;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class MailService {
    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    @Inject private CustomProperties customProperties;

    @Inject private JavaMailSender mailSender;

    @Inject private MessageSource messageSource;

    @Inject private SpringTemplateEngine templateEngine;

    public void sendVerificationEmail(String to, String emailVerificationCode, Locale locale) {
        final Context context = new Context(locale);
        context.setVariable("emailVerificationCode", emailVerificationCode);
        context.setVariable("clientUrl", customProperties.getClientUrl());
        final String content = templateEngine.process("verificationEmail", context);
        final String subject = messageSource.getMessage("email.verification.title", null, locale);
        sendEmail(to, subject, content);
    }

    public void sendResetPasswordEmail(String to, String resetPasswordCode, Locale locale) {
        final Context context = new Context(locale);
        context.setVariable("email", to);
        context.setVariable("clientUrl", customProperties.getClientUrl());
        context.setVariable("resetPasswordCode", resetPasswordCode);
        final String content = templateEngine.process("resetPasswordEmail", context);
        final String subject = messageSource.getMessage("reset.password.title", null, locale);
        sendEmail(to, subject, content);
    }

    @Async("multiThreadExecutor")
    public void sendEmail(String to, String subject, String content) {

        LOG.debug("sendEmail(to: {}, subject: {})", to, subject);

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(customProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (Throwable e) {
            LOG.error("Email sending failed!", e);
        }
    }
}
