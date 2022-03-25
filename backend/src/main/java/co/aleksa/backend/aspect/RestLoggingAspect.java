package co.aleksa.backend.aspect;

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

    @Around("(execution(public * co.aleksa.backend.rest.*ApiResource.*(..))" + ")")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        return logApiCall(pjp, WARN);
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
