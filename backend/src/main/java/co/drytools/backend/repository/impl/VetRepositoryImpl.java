package co.drytools.backend.repository.impl;

import co.drytools.backend.model.File;
import co.drytools.backend.model.QVet;
import co.drytools.backend.model.User;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VetId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.VetRepository;
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
public class VetRepositoryImpl extends AbstractSimpleRepositoryImpl<Vet, VetId> implements VetRepository {
    private static final Logger LOG = LoggerFactory.getLogger(VetRepositoryImpl.class);

    @Override
    protected Class<Vet> getDomainClass() {
        return Vet.class;
    }

    @Override
    protected EntityPath<Vet> getEntityPathBase() {
        return QVet.vet;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QVet.vet.id;
    }

    @Override
    protected VetId getId(Vet entity) {
        return entity.getId();
    }

    @Override
    public Vet create(User user, File image) {
        final Vet vet = new Vet();
        vet.setUser(user);
        vet.setImage(image);
        entityManager.persist(vet);
        postSave(vet);
        return vet;
    }

    @Override
    public List<Vet> findByImage(String imagePath) {
        LOG.trace(".findByImage()");
        final QVet qVet = QVet.vet;
        return factory.select(qVet).from(qVet).where(qVet.image.path.eq(imagePath)).orderBy(qVet.id.asc().nullsLast()).fetch();
    }

    @Override
    public Optional<Vet> findByUserId(UserId userId) {
        LOG.trace(".findByUserId(userId: {})", userId);
        final QVet qVet = QVet.vet;
        return Optional.ofNullable(factory.select(qVet).from(qVet).where(qVet.user.id.eq(userId.getValue())).orderBy(qVet.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public List<Vet> findAllById(Collection<VetId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QVet.vet).where(QVet.vet.id.in(ids.stream().map(VetId::getValue).toList())).fetch();
    }

    @Override
    protected void preDelete(Collection<Vet> entities) {
        for (Vet entity : entities) {
            entity.getUser().removeVets(entity);
        }
    }
}
