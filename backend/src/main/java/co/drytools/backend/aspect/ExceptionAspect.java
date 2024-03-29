package co.drytools.backend.aspect;

import co.drytools.backend.exception.BadRequestException;
import co.drytools.backend.exception.InternalServerException;
import co.drytools.backend.exception.UnauthorizedException;
import co.drytools.backend.util.AppThreadLocals;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionAspect.class);

    @Around("execution(public * co.drytools.backend.rest.*ApiResource.*(..))")
    public Object exceptionHandling(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            AppThreadLocals.forceClear();

            LOG.error(e.getMessage(), e);
            if (e.getClass().equals(BadRequestException.class) || e.getClass().equals(UnauthorizedException.class)) {
                throw e;
            }
            throw new InternalServerException(e);
        }
    }
}
