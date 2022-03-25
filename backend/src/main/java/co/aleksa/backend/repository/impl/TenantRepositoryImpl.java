package co.aleksa.backend.repository.impl;

import co.aleksa.backend.model.QTenant;
import co.aleksa.backend.model.Tenant;
import co.aleksa.backend.model.id.TenantId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.TenantRepository;
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
public class TenantRepositoryImpl extends AbstractSimpleRepositoryImpl<Tenant, TenantId> implements TenantRepository {
    private static final Logger LOG = LoggerFactory.getLogger(TenantRepositoryImpl.class);

    @Override
    protected Class<Tenant> getDomainClass() {
        return Tenant.class;
    }

    @Override
    protected EntityPath<Tenant> getEntityPathBase() {
        return QTenant.tenant;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QTenant.tenant.id;
    }

    @Override
    protected TenantId getId(Tenant entity) {
        return entity.getId();
    }

    @Override
    public Tenant create(String identifier) {
        final Tenant tenant = new Tenant();
        tenant.setIdentifier(identifier);
        entityManager.persist(tenant);
        postSave(tenant);
        return tenant;
    }

    @Override
    public Optional<Tenant> findByIdentifier(String identifier) {
        LOG.trace(".findByIdentifier()");
        final QTenant qTenant = QTenant.tenant;
        return Optional.ofNullable(
                factory.select(qTenant).from(qTenant).where(qTenant.identifier.eq(identifier)).orderBy(qTenant.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public List<Tenant> findAllById(Collection<TenantId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QTenant.tenant).where(QTenant.tenant.id.in(ids.stream().map(TenantId::getValue).toList())).fetch();
    }
}
