package co.aleksa.administration;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class WebInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        String profile = System.getProperty("spring.profiles.active");
        if (profile == null) {
            profile = System.getenv().get("SPRING_PROFILES_ACTIVE");
        }
        return application.profiles(profile == null ? "dev" : profile).sources(AdministrationApplication.class);
    }
}
