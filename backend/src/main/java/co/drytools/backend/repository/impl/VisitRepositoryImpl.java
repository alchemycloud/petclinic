package co.drytools.backend.repository.impl;

import co.drytools.backend.model.Pet;
import co.drytools.backend.model.QOwner;
import co.drytools.backend.model.QPet;
import co.drytools.backend.model.QUser;
import co.drytools.backend.model.QVet;
import co.drytools.backend.model.QVisit;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.Visit;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VisitId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.VisitRepository;
import co.drytools.backend.repository.tuple.VisitFindVetVisitsTuple;
import co.drytools.backend.repository.tuple.VisitMyVisitsTuple;
import co.drytools.backend.repository.tuple.VisitVetVisitsTuple;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class VisitRepositoryImpl extends AbstractSimpleRepositoryImpl<Visit, VisitId> implements VisitRepository {
    private static final Logger LOG = LoggerFactory.getLogger(VisitRepositoryImpl.class);

    @Override
    protected Class<Visit> getDomainClass() {
        return Visit.class;
    }

    @Override
    protected EntityPath<Visit> getEntityPathBase() {
        return QVisit.visit;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QVisit.visit.id;
    }

    @Override
    protected VisitId getId(Visit entity) {
        return entity.getId();
    }

    @Override
    public Visit create(
            Vet vet, Pet pet, Integer visitNumber, ZonedDateTime timestamp, Optional<BigDecimal> petWeight, Optional<String> description, Boolean scheduled) {
        final Visit visit = new Visit();
        visit.setVet(vet);
        visit.setPet(pet);
        visit.setVisitNumber(visitNumber);
        visit.setTimestamp(timestamp);
        visit.setPetWeight(petWeight);
        visit.setDescription(description);
        visit.setScheduled(scheduled);
        entityManager.persist(visit);
        postSave(visit);
        return visit;
    }

    @Override
    public List<Visit> findByVisitNumber(Integer visitNumber) {
        LOG.trace(".findByVisitNumber()");
        final QVisit qVisit = QVisit.visit;
        return factory.select(qVisit).from(qVisit).where(qVisit.visitNumber.eq(visitNumber)).orderBy(qVisit.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Visit> findByTimestamp(ZonedDateTime timestamp) {
        LOG.trace(".findByTimestamp()");
        final QVisit qVisit = QVisit.visit;
        return factory.select(qVisit).from(qVisit).where(qVisit.timestamp.eq(timestamp)).orderBy(qVisit.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Visit> findByPetWeight(Optional<BigDecimal> petWeight) {
        LOG.trace(".findByPetWeight()");
        final QVisit qVisit = QVisit.visit;
        return factory.select(qVisit).from(qVisit).where(petWeight.map(qVisit.petWeight::eq).orElse(null)).orderBy(qVisit.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Visit> findByPetWeightMandatory(BigDecimal petWeight) {
        LOG.trace(".findByPetWeightMandatory()");
        final QVisit qVisit = QVisit.visit;
        return factory.select(qVisit).from(qVisit).where(qVisit.petWeight.eq(petWeight)).orderBy(qVisit.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Visit> findByDescription(Optional<String> description) {
        LOG.trace(".findByDescription()");
        final QVisit qVisit = QVisit.visit;
        return factory.select(qVisit).from(qVisit).where(description.map(qVisit.description::eq).orElse(null)).orderBy(qVisit.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Visit> findByDescriptionMandatory(String description) {
        LOG.trace(".findByDescriptionMandatory()");
        final QVisit qVisit = QVisit.visit;
        return factory.select(qVisit).from(qVisit).where(qVisit.description.eq(description)).orderBy(qVisit.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Visit> findByScheduled(Boolean scheduled) {
        LOG.trace(".findByScheduled()");
        final QVisit qVisit = QVisit.visit;
        return factory.select(qVisit).from(qVisit).where(qVisit.scheduled.eq(scheduled)).orderBy(qVisit.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<VisitVetVisitsTuple> vetVisits(UserId userId) {
        LOG.trace(".vetVisits(userId: {})", userId);
        final QVisit qVisit = QVisit.visit;
        final QVet qVet = QVet.vet;
        final QPet qPet = QPet.pet;
        return factory
                .select(qVisit, qVet, qPet)
                .from(qVisit)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qVisit.pet, qPet)
                .where(qVet.user.id.eq(userId.getValue()))
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitVetVisitsTuple(t.get(qVisit), t.get(qVet), t.get(qPet)))
                .toList();
    }

    @Override
    public List<VisitFindVetVisitsTuple> findVetVisits(UserId userId, Integer drop, Integer take) {
        LOG.trace(".findVetVisits(userId: {})", userId);
        final QVisit qVisit = QVisit.visit;
        final QVet qVet = QVet.vet;
        final QPet qPet = QPet.pet;
        return factory
                .select(qVisit, qVet, qPet)
                .from(qVisit)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qVisit.pet, qPet)
                .where(qVet.user.id.eq(userId.getValue()))
                .offset(drop)
                .limit(take)
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitFindVetVisitsTuple(t.get(qVisit), t.get(qVet), t.get(qPet)))
                .toList();
    }

    @Override
    public Long countVetVisits(UserId userId) {
        LOG.trace(".countVetVisits(userId: {})", userId);
        final QVisit qVisit = QVisit.visit;
        final QVet qVet = QVet.vet;
        final QPet qPet = QPet.pet;
        return factory.select(qVisit)
                .from(qVisit)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qVisit.pet, qPet)
                .where(qVet.user.id.eq(userId.getValue()))
                .fetchCount();
    }

    @Override
    public List<Visit> scheduledVisits() {
        LOG.trace(".scheduledVisits()");
        final QVisit qVisit = QVisit.visit;
        return factory.select(qVisit)
                .from(qVisit)
                .where(new BooleanBuilder().and(qVisit.description.isNotNull()).and(qVisit.scheduled.eq(true)))
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<VisitMyVisitsTuple> myVisits(UserId userIdId) {
        LOG.trace(".myVisits(userIdId: {})", userIdId);
        final QVisit qVisit = new QVisit("visit");
        final QPet qPet = QPet.pet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qVisit, qPet, qOwner, qUser)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(qOwner.user.id.eq(userIdId.getValue()))
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitMyVisitsTuple(t.get(qVisit), t.get(qPet), t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public List<Visit> findAllById(Collection<VisitId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QVisit.visit).where(QVisit.visit.id.in(ids.stream().map(VisitId::getValue).toList())).fetch();
    }
}
