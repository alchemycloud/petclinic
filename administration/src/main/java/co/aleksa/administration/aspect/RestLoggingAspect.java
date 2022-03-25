package co.aleksa.administration.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile(value = "!test")
public class RestLoggingAspect extends AbstractProfiledAspect {
    private static final Logger LOG = LoggerFactory.getLogger(RestLoggingAspect.class);

    private static final int WARN = 200;

    private static final int AUTHENTICATION_WARN = 1500;

    @Around(
            "(execution(public * co.aleksa.administration.rest.*ApiResource.*(..))"
                    + ")"
                    + " && !execution(public * co.aleksa.administration.rest.AuthenticationApiResource.*(..))")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        return logApiCall(pjp, WARN);
    }

    @Around("execution(public * co.aleksa.administration.rest.AuthenticationApiResource.*(..))")
    public Object doBasicProfilingAuthenticationApiResource(ProceedingJoinPoint pjp) throws Throwable {
        return logApiCall(pjp, AUTHENTICATION_WARN);
    }

    private Object logApiCall(ProceedingJoinPoint pjp, long thresholdMillis) throws Throwable {
        final long start = System.currentTimeMillis();
        final Object retVal = pjp.proceed();
        final long end = System.currentTimeMillis();
        if (retVal != null) {
            LOG.debug("Response object: {}", retVal);
        }
        final long totalDuration = end - start;

        if (totalDuration > thresholdMillis) {
            final String errorMessage = "Method " + getSignature(pjp) + " took " + (end - start) + " milliseconds!!!";
            if (isRunningInProfile("prod")) {
                LOG.warn(errorMessage);
            } else {
                LOG.error(errorMessage);
            }
        }

        return retVal;
    }
}
