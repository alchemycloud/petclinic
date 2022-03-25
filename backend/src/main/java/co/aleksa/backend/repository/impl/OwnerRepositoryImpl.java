package co.aleksa.backend.repository.impl;

import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.QOwner;
import co.aleksa.backend.model.QPet;
import co.aleksa.backend.model.QUser;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.OwnerRepository;
import co.aleksa.backend.repository.tuple.OwnerFindForAddressTuple;
import co.aleksa.backend.repository.tuple.OwnerForAddressTuple;
import co.aleksa.backend.repository.tuple.OwnerMyPetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnerVetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnersPetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnersWithPetsTuple;
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
public class OwnerRepositoryImpl extends AbstractSimpleRepositoryImpl<Owner, OwnerId> implements OwnerRepository {
    private static final Logger LOG = LoggerFactory.getLogger(OwnerRepositoryImpl.class);

    @Override
    protected Class<Owner> getDomainClass() {
        return Owner.class;
    }

    @Override
    protected EntityPath<Owner> getEntityPathBase() {
        return QOwner.owner;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QOwner.owner.id;
    }

    @Override
    protected OwnerId getId(Owner entity) {
        return entity.getId();
    }

    @Override
    public Owner create(User user, Optional<String> address, Optional<String> city, Optional<String> telephone) {
        final Owner owner = new Owner();
        owner.setUser(user);
        owner.setAddress(address);
        owner.setCity(city);
        owner.setTelephone(telephone);
        entityManager.persist(owner);
        postSave(owner);
        return owner;
    }

    @Override
    public List<Owner> findByAddress(Optional<String> address) {
        LOG.trace(".findByAddress()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).where(address.map(qOwner.address::eq).orElse(null)).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Owner> findByAddressMandatory(String address) {
        LOG.trace(".findByAddressMandatory()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).where(qOwner.address.eq(address)).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Owner> findByCity(Optional<String> city) {
        LOG.trace(".findByCity()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).where(city.map(qOwner.city::eq).orElse(null)).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Owner> findByCityMandatory(String city) {
        LOG.trace(".findByCityMandatory()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).where(qOwner.city.eq(city)).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Owner> findByTelephone(Optional<String> telephone) {
        LOG.trace(".findByTelephone()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).where(telephone.map(qOwner.telephone::eq).orElse(null)).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Owner> findByTelephoneMandatory(String telephone) {
        LOG.trace(".findByTelephoneMandatory()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).where(qOwner.telephone.eq(telephone)).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public Optional<Owner> findByUserId(UserId userId) {
        LOG.trace(".findByUserId(userId: {})", userId);
        final QOwner qOwner = QOwner.owner;
        return Optional.ofNullable(
                factory.select(qOwner).from(qOwner).where(qOwner.user.id.eq(userId.getValue())).orderBy(qOwner.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public List<Owner> allOwners() {
        LOG.trace(".allOwners()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Owner> findAllOwners(Integer drop, Integer take) {
        LOG.trace(".findAllOwners()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).offset(drop).limit(take).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public Long countAllOwners() {
        LOG.trace(".countAllOwners()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).fetchCount();
    }

    @Override
    public List<OwnerForAddressTuple> forAddress(Optional<String> address) {
        LOG.trace(".forAddress()");
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qOwner, qUser)
                .from(qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(address.map(s -> qOwner.address.like("%" + s + "%")).orElse(null))
                .orderBy(qOwner.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new OwnerForAddressTuple(t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public List<OwnerFindForAddressTuple> findForAddress(Optional<String> address, Integer drop, Integer take) {
        LOG.trace(".findForAddress()");
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qOwner, qUser)
                .from(qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(address.map(s -> qOwner.address.like("%" + s + "%")).orElse(null))
                .offset(drop)
                .limit(take)
                .orderBy(qOwner.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new OwnerFindForAddressTuple(t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public Long countForAddress(Optional<String> address) {
        LOG.trace(".countForAddress()");
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory.select(qOwner)
                .from(qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(address.map(s -> qOwner.address.like("%" + s + "%")).orElse(null))
                .fetchCount();
    }

    @Override
    public List<OwnerOwnersWithPetsTuple> ownersWithPets() {
        LOG.trace(".ownersWithPets()");
        final QOwner qOwner = QOwner.owner;
        final QPet qPet = QPet.pet;
        final QUser qUser = QUser.user;
        return factory
                .select(qOwner, qPet, qUser)
                .from(qOwner)
                .innerJoin(qOwner.pets, qPet)
                .innerJoin(qOwner.user, qUser)
                .orderBy(qOwner.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new OwnerOwnersWithPetsTuple(t.get(qOwner), t.get(qPet), t.get(qUser)))
                .toList();
    }

    @Override
    public List<OwnerOwnersPetsTuple> ownersPets(OwnerId ownerId) {
        LOG.trace(".ownersPets(ownerId: {})", ownerId);
        final QOwner qOwner = QOwner.owner;
        final QPet qPet = QPet.pet;
        return factory
                .select(qOwner, qPet)
                .from(qOwner)
                .innerJoin(qOwner.pets, qPet)
                .where(qOwner.id.eq(ownerId.getValue()))
                .orderBy(qOwner.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new OwnerOwnersPetsTuple(t.get(qOwner), t.get(qPet)))
                .toList();
    }

    @Override
    public List<OwnerMyPetsTuple> myPets(UserId principalId) {
        LOG.trace(".myPets(principalId: {})", principalId);
        final QOwner qOwner = new QOwner("owner");
        final QPet qPet = QPet.pet;
        final QUser qUser = QUser.user;
        return factory
                .select(qOwner, qPet, qUser)
                .from(qOwner)
                .innerJoin(qOwner.pets, qPet)
                .innerJoin(qOwner.user, qUser)
                .where(qOwner.user.id.eq(principalId.getValue()))
                .orderBy(qOwner.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new OwnerMyPetsTuple(t.get(qOwner), t.get(qPet), t.get(qUser)))
                .toList();
    }

    @Override
    public List<OwnerOwnerVetsTuple> ownerVets() {
        LOG.trace(".ownerVets()");
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return factory
                .select(qOwner, qUser)
                .from(qOwner)
                .innerJoin(qOwner.user, qUser)
                .where(qUser.role.eq(UserRole.VET))
                .orderBy(qOwner.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new OwnerOwnerVetsTuple(t.get(qOwner), t.get(qUser)))
                .toList();
    }

    @Override
    public List<Owner> findAllById(Collection<OwnerId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QOwner.owner).where(QOwner.owner.id.in(ids.stream().map(OwnerId::getValue).toList())).fetch();
    }
}
