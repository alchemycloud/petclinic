package co.aleksa.backend.security;

import co.aleksa.backend.util.AppThreadLocals;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.util.Optional;
import java.util.TimeZone;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JWTFilter extends GenericFilterBean {
    private static final Logger LOG = LoggerFactory.getLogger(JWTFilter.class);

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    @Inject private JWTService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            final Optional<String> jwtToken = extractToken(httpServletRequest);
            jwtToken.ifPresent(
                    token -> {
                        final AuthenticationWithTenant authentication = jwtService.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication.getAuthentication());
                        AppThreadLocals.initialize(
                                Optional.empty(),
                                authentication.getTenant(),
                                Optional.ofNullable(authentication.getAuthentication().getPrincipal()).map(Object::toString));
                    });
            final Optional<TimeZone> clientTimeZone = Optional.ofNullable(httpServletRequest.getHeader("x-timezone")).map(TimeZone::getTimeZone);
            AppThreadLocals.setTimeZone(clientTimeZone);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException e) {
            LOG.debug("Security exception for user {} - {}. Expired token.", e.getClaims().getSubject(), e.getMessage());
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication token expired!");
        } catch (JwtException e) {
            LOG.debug("Authentication token is invalid. {}", e.getMessage());
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication token is invalid!");
        }
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            final String jwtToken = bearerToken.substring(BEARER.length());
            return Optional.of(jwtToken);
        }
        return Optional.empty();
    }
}
