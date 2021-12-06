package co.drytools.backend.repository.impl;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.Pet;
import co.drytools.backend.model.QOwner;
import co.drytools.backend.model.QPet;
import co.drytools.backend.model.QUser;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.PetRepository;
import co.drytools.backend.repository.tuple.PetFindPetsTuple;
import co.drytools.backend.repository.tuple.PetPetWithOwnerForOwnerTuple;
import co.drytools.backend.repository.tuple.PetPetsTuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PetRepositoryImpl extends AbstractSimpleRepositoryImpl<Pet, PetId> implements PetRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PetRepositoryImpl.class);

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
    public Pet create(Owner owner, String name, ZonedDateTime birthdate, PetType petType, Boolean vaccinated) {
        final Pet pet = new Pet();
        pet.setOwner(owner);
        pet.setName(name);
        pet.setBirthdate(birthdate);
        pet.setPetType(petType);
        pet.setVaccinated(vaccinated);
        entityManager.persist(pet);
        postSave(pet);
        return pet;
    }

    @Override
    public Long petWithOwnerCount() {
        LOG.trace(".petWithOwnerCount()");
        final QPet qPet = QPet.pet;
        final QOwner qOwner = QOwner.owner;
        return factory.select(qPet).from(qPet).leftJoin(qPet.owner, qOwner).fetchCount();
    }

    @Override
    public List<PetPetWithOwnerForOwnerTuple> petWithOwnerForOwner(OwnerId ownerId) {
        LOG.trace(".petWithOwnerForOwner(ownerId: {})", ownerId);
        final QPet qPet = QPet.pet;
        final QOwner qOwner = QOwner.owner;
        return factory
                .select(qPet, qOwner)
                .from(qPet)
                .leftJoin(qPet.owner, qOwner)
                .where(qPet.owner.id.eq(ownerId.getValue()))
                .orderBy(qPet.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new PetPetWithOwnerForOwnerTuple(t.get(qPet), Optional.ofNullable(t.get(qOwner))))
                .toList();
    }

    @Override
    public List<Pet> findByName(String name) {
        LOG.trace(".findByName()");
        final QPet qPet = QPet.pet;
        return factory.select(qPet).from(qPet).where(qPet.name.eq(name)).orderBy(qPet.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Pet> findByBirthdate(ZonedDateTime birthdate) {
        LOG.trace(".findByBirthdate()");
        final QPet qPet = QPet.pet;
        return factory.select(qPet).from(qPet).where(qPet.birthdate.eq(birthdate)).orderBy(qPet.id.asc().nullsLast()).fetch();
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
    public List<Pet> findPetbyType(PetType petType) {
        LOG.trace(".findPetbyType()");
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
    }
}
