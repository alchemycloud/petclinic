package co.drytools.backend.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class MailConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(MailConfiguration.class);

    @Inject private CustomProperties customProperties;

    @Bean
    public JavaMailSender mailSender() {

        LOG.debug("Initializing mail sender...");

        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(customProperties.getMail().getHost());
        mailSender.setPort(customProperties.getMail().getPort());
        mailSender.setUsername(customProperties.getMail().getUsername());
        mailSender.setPassword(customProperties.getMail().getPassword());

        final Properties properties = new Properties();
        properties.setProperty("mail.smtp.starttls.enable", customProperties.getMail().getStarttls());
        properties.setProperty("mail.smtp.auth", customProperties.getMail().getAuth());

        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver() {

        LOG.debug("Initializing template resolver...");

        final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setOrder(1);
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {

        LOG.debug("Initializing message source...");

        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        return messageSource;
    }
}
