package co.aleksa.backend;

import co.aleksa.backend.config.CustomProperties;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@ComponentScan("co.aleksa.backend")
@EnableAutoConfiguration
@EnableConfigurationProperties({LiquibaseProperties.class, CustomProperties.class})
public class BackendApplication {

    @PostConstruct
    public void init() {
        // Setting JVM timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(BackendApplication.class);
        final SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        if (!source.containsProperty("spring.profiles.active") && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
            app.setAdditionalProfiles("dev");
        }
        app.run(args).getEnvironment();
    }
}
