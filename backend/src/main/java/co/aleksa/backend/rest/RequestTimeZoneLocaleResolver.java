package co.aleksa.backend.rest;

import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;

public class RequestTimeZoneLocaleResolver extends AbstractLocaleContextResolver {

    @Override
    public void setLocaleContext(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable LocaleContext localeContext) {

        throw new UnsupportedOperationException("Cannot change locale - use a different locale resolution strategy");
    }

    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest request) {
        return new TimeZoneAwareLocaleContext() {
            @Override
            public Locale getLocale() {
                return determineDefaultLocale(request);
            }

            @Override
            @Nullable
            public TimeZone getTimeZone() {
                final String timeZoneId = request.getHeader("x-timezone");
                TimeZone timeZone = timeZoneId == null ? null : TimeZone.getTimeZone(timeZoneId);
                if (timeZone == null) {
                    timeZone = getDefaultTimeZone();
                }
                return timeZone;
            }
        };
    }

    private Locale determineDefaultLocale(HttpServletRequest request) {
        Locale defaultLocale = getDefaultLocale();
        if (defaultLocale == null) {
            defaultLocale = request.getLocale();
        }
        return defaultLocale;
    }
}
