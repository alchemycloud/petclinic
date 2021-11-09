package co.drytools.backend.aspect;

import javax.inject.Inject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.env.Environment;

public abstract class AbstractProfiledAspect {

    @Inject private Environment environment;

    protected boolean isRunningInProfile(String profile) {
        for (String activeProfile : environment.getActiveProfiles()) {
            if (activeProfile.equals(profile)) {
                return true;
            }
        }
        return false;
    }

    protected String getSignature(ProceedingJoinPoint pjp) {
        return pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
    }
}
