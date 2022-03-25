package co.aleksa.backend.audit;

import co.aleksa.backend.util.AppThreadLocals;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AuditFacade {
    @Lazy @Inject private AuditFacade auditFacade;

    private static final Logger LOG = LoggerFactory.getLogger(AuditFacade.class);

    @Lazy @Inject private AuditRegister auditRegister;

    public void flushInTransaction() {
        final long start = System.currentTimeMillis();
        final AuditContext auditContext = AppThreadLocals.getAuditContext();
        auditRegister.flushToDatabase(auditContext);
        AppThreadLocals.cleanContext();

        final long end = System.currentTimeMillis();
        LOG.trace("Flushing in transaction took: {} ms", end - start);
    }

    public void flushAfterTransaction() {
        final String correlationId = AppThreadLocals.getCorrelationId();
        final Optional<String> tenant = AppThreadLocals.getTenant();
        final Optional<String> principalEmail = AppThreadLocals.getPrincipalEmail();
        auditFacade.flushAfterTransaction(correlationId, tenant, principalEmail);
    }

    @Async("multiThreadExecutor")
    public void flushAfterTransaction(String correlationId, Optional<String> tenant, Optional<String> principalEmail) {
        final long start = System.currentTimeMillis();
        AppThreadLocals.initialize(Optional.of(correlationId), tenant, principalEmail);

        final long end = System.currentTimeMillis();
        LOG.trace("Flushing in transaction took: {} ms", end - start);
    }
}
