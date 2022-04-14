package co.aleksa.backend.repository.impl;

import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.QOwner;
import co.aleksa.backend.model.QPet;
import co.aleksa.backend.model.QUser;
import co.aleksa.backend.model.QVet;
import co.aleksa.backend.model.QVisit;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.Visit;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.model.id.VisitId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.VisitRepository;
import co.aleksa.backend.repository.tuple.VisitFindScheduledVisitsTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsByPetTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsByVetTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsForOwnerTuple;
import co.aleksa.backend.repository.tuple.VisitScheduledVisitsTuple;
import co.aleksa.backend.repository.tuple.VisitVisitsByPetTuple;
import co.aleksa.backend.repository.tuple.VisitVisitsByVetTuple;
import co.aleksa.backend.repository.tuple.VisitVisitsForOwnerTuple;
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
    public List<VisitVisitsByVetTuple> visitsByVet(VetId vetId) {
        LOG.trace(".visitsByVet(vetId: {})", vetId);
        final QVisit qVisit = QVisit.visit;
        final QVet qVet = QVet.vet;
        final QPet qPet = QPet.pet;
        return factory
                .select(qVisit, qVet, qPet)
                .from(qVisit)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qVisit.pet, qPet)
                .where(qVet.id.eq(vetId.getValue()))
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitVisitsByVetTuple(t.get(qVisit), t.get(qVet), t.get(qPet)))
                .toList();
    }

    @Override
    public List<VisitFindVisitsByVetTuple> findVisitsByVet(VetId vetId, Integer drop, Integer take) {
        LOG.trace(".findVisitsByVet(vetId: {})", vetId);
        final QVisit qVisit = QVisit.visit;
        final QVet qVet = QVet.vet;
        final QPet qPet = QPet.pet;
        return factory
                .select(qVisit, qVet, qPet)
                .from(qVisit)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qVisit.pet, qPet)
                .where(qVet.id.eq(vetId.getValue()))
                .offset(drop)
                .limit(take)
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitFindVisitsByVetTuple(t.get(qVisit), t.get(qVet), t.get(qPet)))
                .toList();
    }

    @Override
    public Long countVisitsByVet(VetId vetId) {
        LOG.trace(".countVisitsByVet(vetId: {})", vetId);
        final QVisit qVisit = QVisit.visit;
        final QVet qVet = QVet.vet;
        final QPet qPet = QPet.pet;
        return factory.select(qVisit).from(qVisit).innerJoin(qVisit.vet, qVet).innerJoin(qVisit.pet, qPet).where(qVet.id.eq(vetId.getValue())).fetchCount();
    }

    @Override
    public List<VisitVisitsByPetTuple> visitsByPet(PetId petId) {
        LOG.trace(".visitsByPet(petId: {})", petId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qVisit, qPet, qVet, qOwner, qUser)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(qPet.id.eq(petId.getValue()))
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitVisitsByPetTuple(t.get(qVisit), t.get(qPet), t.get(qVet), t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public List<VisitFindVisitsByPetTuple> findVisitsByPet(PetId petId, Integer drop, Integer take) {
        LOG.trace(".findVisitsByPet(petId: {})", petId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qVisit, qPet, qVet, qOwner, qUser)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(qPet.id.eq(petId.getValue()))
                .offset(drop)
                .limit(take)
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitFindVisitsByPetTuple(t.get(qVisit), t.get(qPet), t.get(qVet), t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public Long countVisitsByPet(PetId petId) {
        LOG.trace(".countVisitsByPet(petId: {})", petId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory.select(qVisit)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(qPet.id.eq(petId.getValue()))
                .fetchCount();
    }

    @Override
    public List<VisitScheduledVisitsTuple> scheduledVisits(UserId principalId) {
        LOG.trace(".scheduledVisits(principalId: {})", principalId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qVisit, qPet, qVet, qOwner, qUser)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(
                        new BooleanBuilder()
                                .and(new BooleanBuilder().and(qVet.user.id.eq(principalId.getValue())).and(qVet.user.id.eq(principalId.getValue())))
                                .and(qVisit.scheduled.eq(true)))
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitScheduledVisitsTuple(t.get(qVisit), t.get(qPet), t.get(qVet), t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public List<VisitFindScheduledVisitsTuple> findScheduledVisits(UserId principalId, Integer drop, Integer take) {
        LOG.trace(".findScheduledVisits(principalId: {})", principalId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qVisit, qPet, qVet, qOwner, qUser)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(
                        new BooleanBuilder()
                                .and(new BooleanBuilder().and(qVet.user.id.eq(principalId.getValue())).and(qVet.user.id.eq(principalId.getValue())))
                                .and(qVisit.scheduled.eq(true)))
                .offset(drop)
                .limit(take)
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitFindScheduledVisitsTuple(t.get(qVisit), t.get(qPet), t.get(qVet), t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public Long countScheduledVisits(UserId principalId) {
        LOG.trace(".countScheduledVisits(principalId: {})", principalId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory.select(qVisit)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(
                        new BooleanBuilder()
                                .and(new BooleanBuilder().and(qVet.user.id.eq(principalId.getValue())).and(qVet.user.id.eq(principalId.getValue())))
                                .and(qVisit.scheduled.eq(true)))
                .fetchCount();
    }

    @Override
    public List<VisitVisitsForOwnerTuple> visitsForOwner(UserId principalId) {
        LOG.trace(".visitsForOwner(principalId: {})", principalId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QUser qUser = QUser.user;
        final QOwner qOwner = QOwner.owner;
        return factory
                .select(qVisit, qPet, qVet, qUser, qOwner)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qVet.user, qUser)
                .innerJoin(qPet.owner, qOwner)
                .where(qOwner.user.id.eq(principalId.getValue()))
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitVisitsForOwnerTuple(t.get(qVisit), t.get(qPet), t.get(qVet), t.get(qUser), t.get(qOwner)))
                .toList();
    }

    @Override
    public List<VisitFindVisitsForOwnerTuple> findVisitsForOwner(UserId principalId, Integer drop, Integer take) {
        LOG.trace(".findVisitsForOwner(principalId: {})", principalId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QUser qUser = QUser.user;
        final QOwner qOwner = QOwner.owner;
        return factory
                .select(qVisit, qPet, qVet, qUser, qOwner)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qVet.user, qUser)
                .innerJoin(qPet.owner, qOwner)
                .where(qOwner.user.id.eq(principalId.getValue()))
                .offset(drop)
                .limit(take)
                .orderBy(qVisit.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new VisitFindVisitsForOwnerTuple(t.get(qVisit), t.get(qPet), t.get(qVet), t.get(qUser), t.get(qOwner)))
                .toList();
    }

    @Override
    public Long countVisitsForOwner(UserId principalId) {
        LOG.trace(".countVisitsForOwner(principalId: {})", principalId);
        final QVisit qVisit = QVisit.visit;
        final QPet qPet = QPet.pet;
        final QVet qVet = QVet.vet;
        final QUser qUser = QUser.user;
        final QOwner qOwner = QOwner.owner;
        return factory.select(qVisit)
                .from(qVisit)
                .innerJoin(qVisit.pet, qPet)
                .innerJoin(qVisit.vet, qVet)
                .innerJoin(qVet.user, qUser)
                .innerJoin(qPet.owner, qOwner)
                .where(qOwner.user.id.eq(principalId.getValue()))
                .fetchCount();
    }

    @Override
    public List<Visit> findAllById(Collection<VisitId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QVisit.visit).where(QVisit.visit.id.in(ids.stream().map(VisitId::getValue).toList())).fetch();
    }
}
