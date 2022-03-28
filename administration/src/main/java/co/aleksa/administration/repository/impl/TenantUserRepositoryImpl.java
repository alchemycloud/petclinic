package co.aleksa.administration.repository.impl;

import co.aleksa.administration.model.QTenantUser;
import co.aleksa.administration.model.Tenant;
import co.aleksa.administration.model.TenantUser;
import co.aleksa.administration.model.User;
import co.aleksa.administration.model.enumeration.TenantAccessLevel;
import co.aleksa.administration.model.id.TenantId;
import co.aleksa.administration.model.id.TenantUserId;
import co.aleksa.administration.model.id.UserId;
import co.aleksa.administration.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.administration.repository.TenantUserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TenantUserRepositoryImpl extends AbstractSimpleRepositoryImpl<TenantUser, TenantUserId> implements TenantUserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(TenantUserRepositoryImpl.class);

    @Override
    protected Class<TenantUser> getDomainClass() {
        return TenantUser.class;
    }

    @Override
    protected EntityPath<TenantUser> getEntityPathBase() {
        return QTenantUser.tenantUser;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QTenantUser.tenantUser.id;
    }

    @Override
    protected TenantUserId getId(TenantUser entity) {
        return entity.getId();
    }

    @Override
    public TenantUser create(User user, Tenant tenant, TenantAccessLevel accessLevel) {
        final TenantUser tenantUser = new TenantUser();
        tenantUser.setUser(user);
        tenantUser.setTenant(tenant);
        tenantUser.setAccessLevel(accessLevel);
        entityManager.persist(tenantUser);
        postSave(tenantUser);
        return tenantUser;
    }

    @Override
    public List<TenantUser> findByAccessLevel(TenantAccessLevel accessLevel) {
        LOG.trace(".findByAccessLevel()");
        final QTenantUser qTenantUser = QTenantUser.tenantUser;
        return factory.select(qTenantUser).from(qTenantUser).where(qTenantUser.accessLevel.eq(accessLevel)).orderBy(qTenantUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public Optional<TenantUser> findByUserIdAndTenantId(UserId userId, TenantId tenantId) {
        LOG.trace(".findByUserIdAndTenantId(userId: {}, tenantId: {})", userId, tenantId);
        final QTenantUser qTenantUser = QTenantUser.tenantUser;
        return Optional.ofNullable(
                factory.select(qTenantUser)
                        .from(qTenantUser)
                        .where(new BooleanBuilder().and(qTenantUser.user.id.eq(userId.getValue())).and(qTenantUser.tenant.id.eq(tenantId.getValue())))
                        .orderBy(qTenantUser.id.asc().nullsLast())
                        .fetchOne());
    }

    @Override
    public List<TenantUser> findAllById(Collection<TenantUserId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QTenantUser.tenantUser)
                .where(QTenantUser.tenantUser.id.in(ids.stream().map(TenantUserId::getValue).toList()))
                .fetch();
    }

    @Override
    protected void preDelete(Collection<TenantUser> entities) {
        for (TenantUser entity : entities) {
            entity.getUser().removeUserTenants(entity);
            entity.getTenant().removeTenantUsers(entity);
        }
    }
}
