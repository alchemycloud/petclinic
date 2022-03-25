package co.aleksa.backend.repository.impl;

import co.aleksa.backend.model.QVetSpecialty;
import co.aleksa.backend.model.VetSpecialty;
import co.aleksa.backend.model.id.VetSpecialtyId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.VetSpecialtyRepository;
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
public class VetSpecialtyRepositoryImpl extends AbstractSimpleRepositoryImpl<VetSpecialty, VetSpecialtyId> implements VetSpecialtyRepository {
    private static final Logger LOG = LoggerFactory.getLogger(VetSpecialtyRepositoryImpl.class);

    @Override
    protected Class<VetSpecialty> getDomainClass() {
        return VetSpecialty.class;
    }

    @Override
    protected EntityPath<VetSpecialty> getEntityPathBase() {
        return QVetSpecialty.vetSpecialty;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QVetSpecialty.vetSpecialty.id;
    }

    @Override
    protected VetSpecialtyId getId(VetSpecialty entity) {
        return entity.getId();
    }

    @Override
    public VetSpecialty create(String name, String description) {
        final VetSpecialty vetSpecialty = new VetSpecialty();
        vetSpecialty.setName(name);
        vetSpecialty.setDescription(description);
        entityManager.persist(vetSpecialty);
        postSave(vetSpecialty);
        return vetSpecialty;
    }

    @Override
    public Optional<VetSpecialty> findByName(String name) {
        LOG.trace(".findByName()");
        final QVetSpecialty qVetSpecialty = QVetSpecialty.vetSpecialty;
        return Optional.ofNullable(
                factory.select(qVetSpecialty).from(qVetSpecialty).where(qVetSpecialty.name.eq(name)).orderBy(qVetSpecialty.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public List<VetSpecialty> findByDescription(String description) {
        LOG.trace(".findByDescription()");
        final QVetSpecialty qVetSpecialty = QVetSpecialty.vetSpecialty;
        return factory.select(qVetSpecialty)
                .from(qVetSpecialty)
                .where(qVetSpecialty.description.eq(description))
                .orderBy(qVetSpecialty.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<VetSpecialty> findAllById(Collection<VetSpecialtyId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QVetSpecialty.vetSpecialty)
                .where(QVetSpecialty.vetSpecialty.id.in(ids.stream().map(VetSpecialtyId::getValue).toList()))
                .fetch();
    }
}
