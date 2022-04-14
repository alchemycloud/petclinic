package co.aleksa.administration.security;

import java.util.Optional;
import org.springframework.security.core.Authentication;

public class AuthenticationWithTenant {

    private final Authentication authentication;

    private final Optional<String> tenant;

    public AuthenticationWithTenant(Authentication authentication, Optional<String> tenant) {
        this.authentication = authentication;
        this.tenant = tenant;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public Optional<String> getTenant() {
        return tenant;
    }
}
