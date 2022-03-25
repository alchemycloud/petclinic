package co.aleksa.administration.repository;

import co.aleksa.administration.model.Tenant;
import co.aleksa.administration.model.id.TenantId;
import java.util.List;
import java.util.Optional;

public interface TenantRepository extends SimpleRepository<Tenant, TenantId> {

    Tenant create(String name, String identifier);

    List<Tenant> findByName(String name);

    Optional<Tenant> findByIdentifier(String identifier);

    Optional<Tenant> readTenant(String identifier);

    List<Tenant> searchTenants(Optional<String> name);
}
