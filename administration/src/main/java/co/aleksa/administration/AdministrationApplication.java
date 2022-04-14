package co.aleksa.administration;

import co.aleksa.administration.config.CustomProperties;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@ComponentScan("co.aleksa.administration")
@EnableAutoConfiguration
@EnableConfigurationProperties({LiquibaseProperties.class, CustomProperties.class})
public class AdministrationApplication {

    @PostConstruct
    public void init() {
        // Setting JVM timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(AdministrationApplication.class);
        final SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        if (!source.containsProperty("spring.profiles.active") && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
            app.setAdditionalProfiles("dev");
        }
        app.run(args).getEnvironment();
    }
}
