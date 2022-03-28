package co.aleksa.backend.repository.impl;

import co.aleksa.backend.audit.AuditType;
import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.PetHistory;
import co.aleksa.backend.model.QOwner;
import co.aleksa.backend.model.QPet;
import co.aleksa.backend.model.QUser;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.PetHistoryRepository;
import co.aleksa.backend.repository.PetRepository;
import co.aleksa.backend.repository.tuple.PetFindPetsTuple;
import co.aleksa.backend.repository.tuple.PetPetsTuple;
import co.aleksa.backend.util.AppThreadLocals;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PetRepositoryImpl extends AbstractSimpleRepositoryImpl<Pet, PetId> implements PetRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PetRepositoryImpl.class);

    @Inject private PetHistoryRepository petHistoryRepository;

    @Override
    protected Class<Pet> getDomainClass() {
        return Pet.class;
    }

    @Override
    protected EntityPath<Pet> getEntityPathBase() {
        return QPet.pet;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QPet.pet.id;
    }

    @Override
    protected PetId getId(Pet entity) {
        return entity.getId();
    }

    @Override
    public Pet create(Owner owner, String name, ZonedDateTime birthday, PetType petType, Boolean vaccinated) {
        final Pet pet = new Pet();
        pet.setOwner(owner);
        pet.setName(name);
        pet.setBirthday(birthday);
        pet.setPetType(petType);
        pet.setVaccinated(vaccinated);
        entityManager.persist(pet);
        postSave(pet);
        AppThreadLocals.getAuditContext().register(pet, AuditType.CREATE, Optional.empty(), Optional.empty());
        return pet;
    }

    @Override
    public List<Pet> findByName(String name) {
        LOG.trace(".findByName()");
        final QPet qPet = QPet.pet;
        return factory.select(qPet).from(qPet).where(qPet.name.eq(name)).orderBy(qPet.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Pet> findByBirthday(ZonedDateTime birthday) {
        LOG.trace(".findByBirthday()");
        final QPet qPet = QPet.pet;
        return factory.select(qPet).from(qPet).where(qPet.birthday.eq(birthday)).orderBy(qPet.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Pet> findByPetType(PetType petType) {
        LOG.trace(".findByPetType()");
        final QPet qPet = QPet.pet;
        return factory.select(qPet).from(qPet).where(qPet.petType.eq(petType)).orderBy(qPet.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Pet> findByVaccinated(Boolean vaccinated) {
        LOG.trace(".findByVaccinated()");
        final QPet qPet = QPet.pet;
        return factory.select(qPet).from(qPet).where(qPet.vaccinated.eq(vaccinated)).orderBy(qPet.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<PetPetsTuple> pets() {
        LOG.trace(".pets()");
        final QPet qPet = QPet.pet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qPet, qOwner, qUser)
                .from(qPet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .orderBy(qPet.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new PetPetsTuple(t.get(qPet), t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public List<PetFindPetsTuple> findPets(Integer drop, Integer take) {
        LOG.trace(".findPets()");
        final QPet qPet = QPet.pet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qPet, qOwner, qUser)
                .from(qPet)
                .innerJoin(qPet.owner, qOwner)
                .innerJoin(qOwner.user, qUser)
                .offset(drop)
                .limit(take)
                .orderBy(qPet.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new PetFindPetsTuple(t.get(qPet), t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public Long countPets() {
        LOG.trace(".countPets()");
        final QPet qPet = QPet.pet;
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory.select(qPet).from(qPet).innerJoin(qPet.owner, qOwner).innerJoin(qOwner.user, qUser).fetchCount();
    }

    @Override
    public List<Pet> petsByType(PetType petType) {
        LOG.trace(".petsByType()");
        final QPet qPet = QPet.pet;
        return factory.select(qPet).from(qPet).where(qPet.petType.eq(petType)).orderBy(qPet.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Pet> findAllById(Collection<PetId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QPet.pet).where(QPet.pet.id.in(ids.stream().map(PetId::getValue).toList())).fetch();
    }

    @Override
    protected void preDelete(Collection<Pet> entities) {
        for (Pet entity : entities) {
            entity.getOwner().removePets(entity);
        }
        List<PetHistory> histories = petHistoryRepository.findAllHistoriesOfEntities(entities.stream().map(Pet::getId).toList());
        for (PetHistory history : histories) {
            history.setReference(Optional.empty());
            petHistoryRepository.update(history);
        }
        for (Pet entity : entities) {
            AppThreadLocals.getAuditContext().register(entity, AuditType.DELETE, Optional.empty(), Optional.empty());
        }
    }
}
