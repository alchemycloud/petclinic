package co.aleksa.backend.repository.impl;

import co.aleksa.backend.model.QUser;
import co.aleksa.backend.model.QUserHistory;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.UserHistory;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.UserHistoryId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.UserHistoryRepository;
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
public class UserHistoryRepositoryImpl extends AbstractSimpleRepositoryImpl<UserHistory, UserHistoryId> implements UserHistoryRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserHistoryRepositoryImpl.class);

    @Override
    protected Class<UserHistory> getDomainClass() {
        return UserHistory.class;
    }

    @Override
    protected EntityPath<UserHistory> getEntityPathBase() {
        return QUserHistory.userHistory;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QUserHistory.userHistory.id;
    }

    @Override
    protected UserHistoryId getId(UserHistory entity) {
        return entity.getId();
    }

    @Override
    public UserHistory create(
            Optional<String> correlationId,
            Long entityId,
            Optional<User> reference,
            ZonedDateTime changeTime,
            Optional<UserHistory> previous,
            Optional<String> firstName,
            Optional<String> lastName,
            Optional<String> email,
            Optional<ZonedDateTime> birthday,
            Optional<Boolean> active,
            Optional<UserRole> role) {
        final UserHistory userHistory = new UserHistory();
        userHistory.setCorrelationId(correlationId);
        userHistory.setEntityId(entityId);
        userHistory.setReference(reference);
        userHistory.setChangeTime(changeTime);
        userHistory.setPrevious(previous);
        userHistory.setFirstName(firstName);
        userHistory.setLastName(lastName);
        userHistory.setEmail(email);
        userHistory.setBirthday(birthday);
        userHistory.setActive(active);
        userHistory.setRole(role);
        entityManager.persist(userHistory);
        postSave(userHistory);
        return userHistory;
    }

    @Override
    public UserHistory getLastHistoryFor(UserId reference) {
        LOG.trace(".getLastHistoryFor(reference: {})", reference);

        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.id.in(factory.select(qUserHistory.id.max()).from(qUserHistory).where(qUserHistory.entityId.eq(reference.getValue()))))
                .fetchOne();
    }

    @Override
    public List<Pair<User, Optional<UserHistory>>> findAllWithLastHistory(Set<UserId> ids) {
        LOG.trace(".findAllWithLastHistory(ids: {})", ids);
        if (ids.isEmpty()) return Collections.emptyList();
        final QUser qUser = QUser.user;
        final QUserHistory qLastHistory = new QUserHistory("lastHistoryUserHistory");

        return factory
                .select(qUser, qLastHistory)
                .from(qUser)
                .leftJoin(qUser.lastHistory, qLastHistory)
                .where(qUser.id.in(ids.stream().map(UserId::getValue).toList()))
                .fetch()
                .stream()
                .map(t -> Pair.of(t.get(qUser), Optional.ofNullable(t.get(qLastHistory))))
                .toList();
    }

    @Override
    public List<UserHistory> findAllHistoriesOfEntities(List<UserId> referenceId) {
        LOG.trace(".findAllHistoriesOfEntities(referenceId: {})", referenceId);
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.reference.id.in(referenceId.stream().map(UserId::getValue).toList()))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByCorrelationId(Optional<String> correlationId) {
        LOG.trace(".findByCorrelationId()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(correlationId.map(qUserHistory.correlationId::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByCorrelationIdMandatory(String correlationId) {
        LOG.trace(".findByCorrelationIdMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.correlationId.eq(correlationId))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByEntityId(Long entityId) {
        LOG.trace(".findByEntityId()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.entityId.eq(entityId)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<UserHistory> findByChangeTime(ZonedDateTime changeTime) {
        LOG.trace(".findByChangeTime()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.changeTime.eq(changeTime)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<UserHistory> findByFirstName(Optional<String> firstName) {
        LOG.trace(".findByFirstName()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(firstName.map(qUserHistory.firstName::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByFirstNameMandatory(String firstName) {
        LOG.trace(".findByFirstNameMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.firstName.eq(firstName)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<UserHistory> findByLastName(Optional<String> lastName) {
        LOG.trace(".findByLastName()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(lastName.map(qUserHistory.lastName::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByLastNameMandatory(String lastName) {
        LOG.trace(".findByLastNameMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.lastName.eq(lastName)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<UserHistory> findByEmail(Optional<String> email) {
        LOG.trace(".findByEmail()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(email.map(qUserHistory.email::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByEmailMandatory(String email) {
        LOG.trace(".findByEmailMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.email.eq(email)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<UserHistory> findByBirthday(Optional<ZonedDateTime> birthday) {
        LOG.trace(".findByBirthday()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(birthday.map(qUserHistory.birthday::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByBirthdayMandatory(ZonedDateTime birthday) {
        LOG.trace(".findByBirthdayMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.birthday.eq(birthday)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<UserHistory> findByActive(Optional<Boolean> active) {
        LOG.trace(".findByActive()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(active.map(qUserHistory.active::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByActiveMandatory(Boolean active) {
        LOG.trace(".findByActiveMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.active.eq(active)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<UserHistory> findByRole(Optional<UserRole> role) {
        LOG.trace(".findByRole()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(role.map(qUserHistory.role::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByRoleMandatory(UserRole role) {
        LOG.trace(".findByRoleMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.role.eq(role)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<UserHistory> findAllById(Collection<UserHistoryId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QUserHistory.userHistory)
                .where(QUserHistory.userHistory.id.in(ids.stream().map(UserHistoryId::getValue).toList()))
                .fetch();
    }

    @Override
    protected void preDelete(Collection<UserHistory> entities) {
        for (UserHistory entity : entities) {
            if (entity.getReference().isPresent()) {
                entity.getReference().get().removeHistories(entity);
            }
        }
    }
}
