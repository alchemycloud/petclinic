package co.aleksa.administration.repository.impl;

import co.aleksa.administration.model.QTenant;
import co.aleksa.administration.model.Tenant;
import co.aleksa.administration.model.id.TenantId;
import co.aleksa.administration.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.administration.repository.TenantRepository;
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
    public Tenant create(String name, String identifier) {
        final Tenant tenant = new Tenant();
        tenant.setName(name);
        tenant.setIdentifier(identifier);
        entityManager.persist(tenant);
        postSave(tenant);
        return tenant;
    }

    @Override
    public List<Tenant> findByName(String name) {
        LOG.trace(".findByName()");
        final QTenant qTenant = QTenant.tenant;
        return factory.select(qTenant).from(qTenant).where(qTenant.name.eq(name)).orderBy(qTenant.id.asc().nullsLast()).fetch();
    }

    @Override
    public Optional<Tenant> findByIdentifier(String identifier) {
        LOG.trace(".findByIdentifier()");
        final QTenant qTenant = QTenant.tenant;
        return Optional.ofNullable(
                factory.select(qTenant).from(qTenant).where(qTenant.identifier.eq(identifier)).orderBy(qTenant.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public Optional<Tenant> readTenant(String identifier) {
        LOG.trace(".readTenant()");
        final QTenant qTenant = QTenant.tenant;
        return Optional.ofNullable(
                factory.select(qTenant).from(qTenant).where(qTenant.identifier.eq(identifier)).orderBy(qTenant.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public List<Tenant> searchTenants(Optional<String> name) {
        LOG.trace(".searchTenants()");
        final QTenant qTenant = QTenant.tenant;
        return factory.select(qTenant)
                .from(qTenant)
                .where(name.map(s -> qTenant.name.like("%" + s + "%")).orElse(null))
                .orderBy(qTenant.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<Tenant> findAllById(Collection<TenantId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QTenant.tenant).where(QTenant.tenant.id.in(ids.stream().map(TenantId::getValue).toList())).fetch();
    }
}
