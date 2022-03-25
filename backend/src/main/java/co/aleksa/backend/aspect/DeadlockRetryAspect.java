package co.aleksa.backend.aspect;

import co.aleksa.backend.util.AppThreadLocals;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("!test")
public class DeadlockRetryAspect {

    private static final int MAX_ATTEMPTS = 15;
    private static final long ATTEMPT_DELAY = 200L;
    private static final Logger LOG = LoggerFactory.getLogger(DeadlockRetryAspect.class);

    @Inject private EntityManager entityManager;

    @Around("execution(public * co.aleksa.backend.api.*ApiCaller.*(..))")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        int nrOfAttempts = 0;
        while (true) {
            try {
                return pjp.proceed();
            } catch (TransientDataAccessException e) {
                entityManager.clear();
                AppThreadLocals.forceClear();
                nrOfAttempts++;
                if (nrOfAttempts == MAX_ATTEMPTS) {
                    LOG.error("Reached maximum number of retry attempts for deadlock.");
                    throw e;
                }
                LOG.debug(e.getClass().getName() + " caught " + nrOfAttempts + " times while executing API call!");
                LOG.debug(e.getMessage(), e);
                Thread.sleep(ATTEMPT_DELAY * nthFibonacciTerm(nrOfAttempts));
            } catch (Throwable e) {
                entityManager.clear();
                AppThreadLocals.forceClear();
                throw e;
            }
        }
    }

    private int nthFibonacciTerm(int n) {
        if (n == 0 || n == 1) {
            return 1; // if this was 0 delay would also be 0
        }
        int n0 = 0, n1 = 1;
        int tempNthTerm;
        for (int i = 2; i <= n; i++) {
            tempNthTerm = n0 + n1;
            n0 = n1;
            n1 = tempNthTerm;
        }
        return n1;
    }
}
