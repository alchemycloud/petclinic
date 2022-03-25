package co.aleksa.backend.repository;

import co.aleksa.backend.model.Tenant;
import co.aleksa.backend.model.id.TenantId;
import java.util.Optional;

public interface TenantRepository extends SimpleRepository<Tenant, TenantId> {

    Tenant create(String identifier);

    Optional<Tenant> findByIdentifier(String identifier);
}
