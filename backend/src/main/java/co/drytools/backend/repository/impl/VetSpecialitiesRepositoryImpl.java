package co.drytools.backend.repository.impl;

import co.drytools.backend.model.QVetSpecialities;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.VetSpecialities;
import co.drytools.backend.model.VetSpeciality;
import co.drytools.backend.model.id.VetSpecialitiesId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.VetSpecialitiesRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class VetSpecialitiesRepositoryImpl extends AbstractSimpleRepositoryImpl<VetSpecialities, VetSpecialitiesId> implements VetSpecialitiesRepository {
    private static final Logger LOG = LoggerFactory.getLogger(VetSpecialitiesRepositoryImpl.class);

    @Override
    protected Class<VetSpecialities> getDomainClass() {
        return VetSpecialities.class;
    }

    @Override
    protected EntityPath<VetSpecialities> getEntityPathBase() {
        return QVetSpecialities.vetSpecialities;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QVetSpecialities.vetSpecialities.id;
    }

    @Override
    protected VetSpecialitiesId getId(VetSpecialities entity) {
        return entity.getId();
    }

    @Override
    public VetSpecialities create(Vet vet, VetSpeciality speciality) {
        final VetSpecialities vetSpecialities = new VetSpecialities();
        vetSpecialities.setVet(vet);
        vetSpecialities.setSpeciality(speciality);
        entityManager.persist(vetSpecialities);
        postSave(vetSpecialities);
        return vetSpecialities;
    }

    @Override
    public List<VetSpecialities> findAllById(Collection<VetSpecialitiesId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QVetSpecialities.vetSpecialities)
                .where(QVetSpecialities.vetSpecialities.id.in(ids.stream().map(VetSpecialitiesId::getValue).toList()))
                .fetch();
    }
}
