package co.aleksa.backend.repository.impl;

import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.PetHistory;
import co.aleksa.backend.model.QOwner;
import co.aleksa.backend.model.QPet;
import co.aleksa.backend.model.QPetHistory;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetHistoryId;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.PetHistoryRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PetHistoryRepositoryImpl extends AbstractSimpleRepositoryImpl<PetHistory, PetHistoryId> implements PetHistoryRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PetHistoryRepositoryImpl.class);

    @Override
    protected Class<PetHistory> getDomainClass() {
        return PetHistory.class;
    }

    @Override
    protected EntityPath<PetHistory> getEntityPathBase() {
        return QPetHistory.petHistory;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QPetHistory.petHistory.id;
    }

    @Override
    protected PetHistoryId getId(PetHistory entity) {
        return entity.getId();
    }

    @Override
    public PetHistory create(
            Optional<String> correlationId,
            Long entityId,
            Optional<Pet> reference,
            ZonedDateTime changeTime,
            Optional<PetHistory> previous,
            Optional<Long> ownerId,
            Optional<String> name,
            Optional<ZonedDateTime> birthday,
            Optional<PetType> petType,
            Optional<Boolean> vaccinated) {
        final PetHistory petHistory = new PetHistory();
        petHistory.setCorrelationId(correlationId);
        petHistory.setEntityId(entityId);
        petHistory.setReference(reference);
        petHistory.setChangeTime(changeTime);
        petHistory.setPrevious(previous);
        petHistory.setOwnerId(ownerId);
        petHistory.setName(name);
        petHistory.setBirthday(birthday);
        petHistory.setPetType(petType);
        petHistory.setVaccinated(vaccinated);
        entityManager.persist(petHistory);
        postSave(petHistory);
        return petHistory;
    }

    @Override
    public PetHistory getLastHistoryFor(PetId reference) {
        LOG.trace(".getLastHistoryFor(reference: {})", reference);

        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(qPetHistory.id.in(factory.select(qPetHistory.id.max()).from(qPetHistory).where(qPetHistory.entityId.eq(reference.getValue()))))
                .fetchOne();
    }

    @Override
    public List<Pair<Pet, Optional<PetHistory>>> findAllWithLastHistory(Set<PetId> ids) {
        LOG.trace(".findAllWithLastHistory(ids: {})", ids);
        if (ids.isEmpty()) return Collections.emptyList();
        final QPet qPet = QPet.pet;
        final QOwner qOwner = new QOwner("ownerOwner");
        final QPetHistory qLastHistory = new QPetHistory("lastHistoryPetHistory");

        return factory
                .select(qPet, qOwner, qLastHistory)
                .from(qPet)
                .innerJoin(qPet.owner, qOwner)
                .leftJoin(qPet.lastHistory, qLastHistory)
                .where(qPet.id.in(ids.stream().map(PetId::getValue).toList()))
                .fetch()
                .stream()
                .map(t -> Pair.of(t.get(qPet), Optional.ofNullable(t.get(qLastHistory))))
                .toList();
    }

    @Override
    public List<PetHistory> findAllHistoriesOfEntities(List<PetId> referenceId) {
        LOG.trace(".findAllHistoriesOfEntities(referenceId: {})", referenceId);
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(qPetHistory.reference.id.in(referenceId.stream().map(PetId::getValue).toList()))
                .orderBy(qPetHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<PetHistory> findByCorrelationId(Optional<String> correlationId) {
        LOG.trace(".findByCorrelationId()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(correlationId.map(qPetHistory.correlationId::eq).orElse(null))
                .orderBy(qPetHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<PetHistory> findByCorrelationIdMandatory(String correlationId) {
        LOG.trace(".findByCorrelationIdMandatory()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(qPetHistory.correlationId.eq(correlationId))
                .orderBy(qPetHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<PetHistory> findByEntityId(Long entityId) {
        LOG.trace(".findByEntityId()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory).from(qPetHistory).where(qPetHistory.entityId.eq(entityId)).orderBy(qPetHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<PetHistory> findByChangeTime(ZonedDateTime changeTime) {
        LOG.trace(".findByChangeTime()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory).from(qPetHistory).where(qPetHistory.changeTime.eq(changeTime)).orderBy(qPetHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<PetHistory> findByOwnerId(Optional<Long> ownerId) {
        LOG.trace(".findByOwnerId()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(ownerId.map(qPetHistory.ownerId::eq).orElse(null))
                .orderBy(qPetHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<PetHistory> findByOwnerIdMandatory(Long ownerId) {
        LOG.trace(".findByOwnerIdMandatory()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory).from(qPetHistory).where(qPetHistory.ownerId.eq(ownerId)).orderBy(qPetHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<PetHistory> findByName(Optional<String> name) {
        LOG.trace(".findByName()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(name.map(qPetHistory.name::eq).orElse(null))
                .orderBy(qPetHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<PetHistory> findByNameMandatory(String name) {
        LOG.trace(".findByNameMandatory()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory).from(qPetHistory).where(qPetHistory.name.eq(name)).orderBy(qPetHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<PetHistory> findByBirthday(Optional<ZonedDateTime> birthday) {
        LOG.trace(".findByBirthday()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(birthday.map(qPetHistory.birthday::eq).orElse(null))
                .orderBy(qPetHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<PetHistory> findByBirthdayMandatory(ZonedDateTime birthday) {
        LOG.trace(".findByBirthdayMandatory()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory).from(qPetHistory).where(qPetHistory.birthday.eq(birthday)).orderBy(qPetHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<PetHistory> findByPetType(Optional<PetType> petType) {
        LOG.trace(".findByPetType()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(petType.map(qPetHistory.petType::eq).orElse(null))
                .orderBy(qPetHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<PetHistory> findByPetTypeMandatory(PetType petType) {
        LOG.trace(".findByPetTypeMandatory()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory).from(qPetHistory).where(qPetHistory.petType.eq(petType)).orderBy(qPetHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<PetHistory> findByVaccinated(Optional<Boolean> vaccinated) {
        LOG.trace(".findByVaccinated()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory)
                .from(qPetHistory)
                .where(vaccinated.map(qPetHistory.vaccinated::eq).orElse(null))
                .orderBy(qPetHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<PetHistory> findByVaccinatedMandatory(Boolean vaccinated) {
        LOG.trace(".findByVaccinatedMandatory()");
        final QPetHistory qPetHistory = QPetHistory.petHistory;
        return factory.select(qPetHistory).from(qPetHistory).where(qPetHistory.vaccinated.eq(vaccinated)).orderBy(qPetHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<PetHistory> findAllById(Collection<PetHistoryId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QPetHistory.petHistory)
                .where(QPetHistory.petHistory.id.in(ids.stream().map(PetHistoryId::getValue).toList()))
                .fetch();
    }

    @Override
    protected void preDelete(Collection<PetHistory> entities) {
        for (PetHistory entity : entities) {
            if (entity.getReference().isPresent()) {
                entity.getReference().get().removeHistories(entity);
            }
        }
    }
}
