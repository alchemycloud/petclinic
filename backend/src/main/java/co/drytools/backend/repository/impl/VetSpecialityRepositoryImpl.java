package co.drytools.backend.repository.impl;

import co.drytools.backend.model.QVetSpeciality;
import co.drytools.backend.model.VetSpeciality;
import co.drytools.backend.model.id.VetSpecialityId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.VetSpecialityRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class VetSpecialityRepositoryImpl extends AbstractSimpleRepositoryImpl<VetSpeciality, VetSpecialityId> implements VetSpecialityRepository {
    private static final Logger LOG = LoggerFactory.getLogger(VetSpecialityRepositoryImpl.class);

    @Override
    protected Class<VetSpeciality> getDomainClass() {
        return VetSpeciality.class;
    }

    @Override
    protected EntityPath<VetSpeciality> getEntityPathBase() {
        return QVetSpeciality.vetSpeciality;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QVetSpeciality.vetSpeciality.id;
    }

    @Override
    protected VetSpecialityId getId(VetSpeciality entity) {
        return entity.getId();
    }

    @Override
    public VetSpeciality create(String name, String description) {
        final VetSpeciality vetSpeciality = new VetSpeciality();
        vetSpeciality.setName(name);
        vetSpeciality.setDescription(description);
        entityManager.persist(vetSpeciality);
        postSave(vetSpeciality);
        return vetSpeciality;
    }

    @Override
    public Optional<VetSpeciality> findByName(String name) {
        LOG.trace(".findByName()");
        final QVetSpeciality qVetSpeciality = QVetSpeciality.vetSpeciality;
        return Optional.ofNullable(
                factory.select(qVetSpeciality)
                        .from(qVetSpeciality)
                        .where(qVetSpeciality.name.eq(name))
                        .orderBy(qVetSpeciality.id.asc().nullsLast())
                        .fetchOne());
    }

    @Override
    public List<VetSpeciality> findByDescription(String description) {
        LOG.trace(".findByDescription()");
        final QVetSpeciality qVetSpeciality = QVetSpeciality.vetSpeciality;
        return factory.select(qVetSpeciality)
                .from(qVetSpeciality)
                .where(qVetSpeciality.description.eq(description))
                .orderBy(qVetSpeciality.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<VetSpeciality> findAllById(Collection<VetSpecialityId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QVetSpeciality.vetSpeciality)
                .where(QVetSpeciality.vetSpeciality.id.in(ids.stream().map(VetSpecialityId::getValue).collect(Collectors.toList())))
                .fetch();
    }
}
