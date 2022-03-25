package co.aleksa.backend.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import co.aleksa.backend.model.File;
import co.aleksa.backend.model.QUser;
import co.aleksa.backend.model.QVet;
import co.aleksa.backend.model.QVetSpecialty;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.VetRepository;
import co.aleksa.backend.repository.tuple.VetVetInfoTuple;
import co.aleksa.backend.repository.tuple.VetVetsWithSpecialtiesTuple;
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
    public Vet create(User user, File image, User user) {
        final Vet vet = new Vet();
        vet.setUser(user);
        vet.setImage(image);
        vet.setUser(user);
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
    public Optional<Vet> findByUserIdAndUserId(UserId userId) {
        LOG.trace(".findByUserIdAndUserId(userId: {})", userId);
        final QVet qVet = QVet.vet;
        return Optional.ofNullable(
                factory.select(qVet)
                        .from(qVet)
                        .where(
                                new BooleanBuilder()
                                        .and(new BooleanBuilder().and(qVet.user.id.eq(userId.getValue())).and(qVet.user.id.eq(userId.getValue())))
                                        .and(new BooleanBuilder().and(qVet.user.id.eq(userId.getValue())).and(qVet.user.id.eq(userId.getValue()))))
                        .orderBy(qVet.id.asc().nullsLast())
                        .fetchOne());
    }

    @Override
    public List<VetVetsWithSpecialtiesTuple> vetsWithSpecialties() {
        LOG.trace(".vetsWithSpecialties()");
        final QVet qVet = new QVet("vet");
        final QUser qUser = QUser.user;
        final QVetSpecialty qVetSpecialty = QVetSpecialty.vetSpecialty;
        return factory
                .select(qVet, qVetSpecialty)
                .from(qVet)
                .innerJoin(qVet.user, qUser)
                .innerJoin(qVet.specialties, qVetSpecialty)
                .groupBy(qVet.id)
                .orderBy(qVet.id.asc().nullsLast())
                .transform(groupBy(qVet.id).as(qVet, list(qVetSpecialty)))
                .values()
                .stream()
                .map(t -> new VetVetsWithSpecialtiesTuple(t.getOne(qVet), t.getList(qVetSpecialty)))
                .toList();
    }

    @Override
    public List<VetVetInfoTuple> vetInfo(VetId id) {
        LOG.trace(".vetInfo(id: {})", id);
        final QVet qVet = new QVet("vet");
        final QUser qUser = QUser.user;
        final QVetSpecialty qVetSpecialty = QVetSpecialty.vetSpecialty;
        return factory
                .select(qVet, qVetSpecialty)
                .from(qVet)
                .innerJoin(qVet.user, qUser)
                .innerJoin(qVet.specialties, qVetSpecialty)
                .where(qVet.id.eq(id.getValue()))
                .groupBy(qVet.id)
                .orderBy(qVet.id.asc().nullsLast())
                .transform(groupBy(qVet.id).as(qVet, list(qVetSpecialty)))
                .values()
                .stream()
                .map(t -> new VetVetInfoTuple(t.getOne(qVet), t.getList(qVetSpecialty)))
                .toList();
    }

    @Override
    public List<Vet> findAllById(Collection<VetId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QVet.vet).where(QVet.vet.id.in(ids.stream().map(VetId::getValue).toList())).fetch();
    }
}
