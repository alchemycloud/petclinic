package co.drytools.backend.rest;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class ServerVersionFilter extends GenericFilterBean {
    private static final Logger LOG = LoggerFactory.getLogger(ServerVersionFilter.class);

    public static final Integer MAJOR = 1;
    public static final Integer MINOR = 0;
    public static final Integer REVISION = 0;
    private static final String X_SERVER_VERSION = "x-server-version";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final Optional<String> versionToken = Optional.ofNullable(httpServletRequest.getHeader(X_SERVER_VERSION));
        if (versionToken.isPresent()) {
            final String[] expectedVersion = versionToken.get().split("\\.");
            final Integer expectedMajor = Integer.valueOf(expectedVersion[0]);
            final Integer expectedMinor = Integer.valueOf(expectedVersion[1]);
            final Integer expectedRevision = Integer.valueOf(expectedVersion[2]);

            if (!expectedMajor.equals(MAJOR) || !expectedMinor.equals(MINOR) || !expectedRevision.equals(REVISION)) {
                LOG.debug("Server versions differ. Request: {}. Current: {}.{}.{}.", versionToken.get(), MAJOR, MINOR, REVISION);
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Server version mismatch!");
            }
        } else {
            LOG.debug("Server version not present in request.");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
