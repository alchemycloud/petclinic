package co.aleksa.administration.repository;

import co.aleksa.administration.model.Tenant;
import co.aleksa.administration.model.TenantUser;
import co.aleksa.administration.model.User;
import co.aleksa.administration.model.enumeration.TenantAccessLevel;
import co.aleksa.administration.model.id.TenantId;
import co.aleksa.administration.model.id.TenantUserId;
import co.aleksa.administration.model.id.UserId;
import java.util.List;
import java.util.Optional;

public interface TenantUserRepository extends SimpleRepository<TenantUser, TenantUserId> {

    TenantUser create(User user, Tenant tenant, TenantAccessLevel accessLevel);

    List<TenantUser> findByAccessLevel(TenantAccessLevel accessLevel);

    Optional<TenantUser> findByUserIdAndTenantId(UserId userId, TenantId tenantId);
}
