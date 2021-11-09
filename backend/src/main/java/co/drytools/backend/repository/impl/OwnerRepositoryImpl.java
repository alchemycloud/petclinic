package co.drytools.backend.repository.impl;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.QOwner;
import co.drytools.backend.model.QPet;
import co.drytools.backend.model.QUser;
import co.drytools.backend.model.User;
import co.drytools.backend.model.enumeration.OwnerOrderableOwnersSortField;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.OwnerRepository;
import co.drytools.backend.repository.tuple.OwnerDeactivatedOwnerWithPetsTuple;
import co.drytools.backend.repository.tuple.OwnerFindOwnerVetsTuple;
import co.drytools.backend.repository.tuple.OwnerFindOwnersForAddressTuple;
import co.drytools.backend.repository.tuple.OwnerFindOwnerwithUserTuple;
import co.drytools.backend.repository.tuple.OwnerMyPetsTuple;
import co.drytools.backend.repository.tuple.OwnerOwnersForAddressTuple;
import co.drytools.backend.repository.tuple.OwnerOwnersPetsTuple;
import co.drytools.backend.repository.tuple.OwnerOwnersWithPetsTuple;
import co.drytools.backend.repository.util.OrderableField;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public List<Owner> allOwnersPaged(Integer param) {
        LOG.trace(".allOwnersPaged()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).offset(param).limit(20).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public Optional<OwnerFindOwnerwithUserTuple> findOwnerwithUser(OwnerId id) {
        LOG.trace(".findOwnerwithUser(id: {})", id);
        final QOwner qOwner = QOwner.owner;
        final QUser qUser = QUser.user;
        return Optional.ofNullable(
                        factory.select(qOwner, qUser)
                                .from(qOwner)
                                .innerJoin(qOwner.user, qUser)
                                .where(qOwner.id.eq(id.getValue()))
                                .orderBy(qOwner.id.asc().nullsLast())
                                .fetchOne())
                .map(t -> new OwnerFindOwnerwithUserTuple(t.get(qOwner), t.get(qUser)));
    }

    @Override
    public List<Owner> mandatoryAddress(String address) {
        LOG.trace(".mandatoryAddress()");
        final QOwner qOwner = QOwner.owner;
        return factory.select(qOwner).from(qOwner).where(qOwner.address.eq(address)).orderBy(qOwner.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<Owner> orderableOwners(List<OrderableField<OwnerOrderableOwnersSortField>> orderableFields) {
        LOG.trace(".orderableOwners()");
        final QOwner qOwner = QOwner.owner;
        final List<OrderSpecifier<?>> orderByFields = new ArrayList();
        orderableFields.forEach(
                orderable ->
                        orderByFields.add(new OrderSpecifier<>(orderable.getDirection(), Expressions.stringPath(orderable.getField().toString())).nullsLast()));
        orderByFields.add(qOwner.id.asc().nullsLast());
        return factory.select(qOwner)
                .from(qOwner)
                .where(new BooleanBuilder().and(qOwner.city.isNotNull()).or(qOwner.telephone.isNotNull()))
                .orderBy(orderByFields.toArray(OrderSpecifier<?>[]::new))
                .fetch();
    }

    @Override
    public List<OwnerDeactivatedOwnerWithPetsTuple> deactivatedOwnerWithPets() {
        LOG.trace(".deactivatedOwnerWithPets()");
        final QOwner qOwner = QOwner.owner;
        final QPet qPet = QPet.pet;
        final QUser qUser = QUser.user;
        return factory
                .select(qOwner, qPet, qUser)
                .from(qOwner)
                .innerJoin(qOwner.pets, qPet)
                .innerJoin(qOwner.user, qUser)
                .where(new BooleanBuilder().and(qUser.active.eq(false)).and(qUser.role.eq(UserRole.OWNER)))
                .orderBy(qOwner.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new OwnerDeactivatedOwnerWithPetsTuple(t.get(qOwner), t.get(qPet), t.get(qUser)))
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerFindOwnerVetsTuple> findOwnerVets() {
        LOG.trace(".findOwnerVets()");
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
                .map(t -> new OwnerFindOwnerVetsTuple(t.get(qOwner), t.get(qUser)))
                .collect(Collectors.toList());
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
    public List<OwnerOwnersForAddressTuple> ownersForAddress(Optional<String> address) {
        LOG.trace(".ownersForAddress()");
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
                .map(t -> new OwnerOwnersForAddressTuple(t.get(qOwner), t.get(qUser)))
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerFindOwnersForAddressTuple> findOwnersForAddress(Optional<String> address, Integer drop, Integer take) {
        LOG.trace(".findOwnersForAddress()");
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
                .map(t -> new OwnerFindOwnersForAddressTuple(t.get(qOwner), t.get(qUser)))
                .collect(Collectors.toList());
    }

    @Override
    public Long countOwnersForAddress(Optional<String> address) {
        LOG.trace(".countOwnersForAddress()");
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
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerOwnersPetsTuple> ownersPets(OwnerId ownerId) {
        LOG.trace(".ownersPets(ownerId: {})", ownerId);
        final QOwner qOwner = QOwner.owner;
        final QPet qPet = QPet.pet;
        final QUser qUser = QUser.user;
        return factory
                .select(qOwner, qPet, qUser)
                .from(qOwner)
                .innerJoin(qOwner.pets, qPet)
                .innerJoin(qOwner.user, qUser)
                .where(qOwner.id.eq(ownerId.getValue()))
                .orderBy(qOwner.id.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new OwnerOwnersPetsTuple(t.get(qOwner), t.get(qPet), t.get(qUser)))
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());
    }

    @Override
    public List<Owner> findAllById(Collection<OwnerId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QOwner.owner)
                .where(QOwner.owner.id.in(ids.stream().map(OwnerId::getValue).collect(Collectors.toList())))
                .fetch();
    }
}
