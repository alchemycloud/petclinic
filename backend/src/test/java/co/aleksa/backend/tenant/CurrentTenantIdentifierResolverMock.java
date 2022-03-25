package co.aleksa.backend.tenant;

import co.aleksa.backend.util.AppThreadLocals;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class CurrentTenantIdentifierResolverMock implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return AppThreadLocals.getTenant().get();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
