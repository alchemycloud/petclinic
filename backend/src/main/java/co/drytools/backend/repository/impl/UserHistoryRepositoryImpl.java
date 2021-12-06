package co.drytools.backend.repository.impl;

import co.drytools.backend.model.QUser;
import co.drytools.backend.model.QUserHistory;
import co.drytools.backend.model.User;
import co.drytools.backend.model.UserHistory;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.UserHistoryId;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.UserHistoryRepository;
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
            Optional<ZonedDateTime> birthdate,
            Optional<Boolean> active,
            Optional<UserRole> role,
            Optional<String> email,
            Optional<String> passwordHash,
            Optional<String> emailVerificationCode,
            Optional<ZonedDateTime> emailVerificationCodeTimestamp,
            Optional<Boolean> emailVerified,
            Optional<String> resetPasswordCode,
            Optional<ZonedDateTime> resetPasswordCodeTimestamp) {
        final UserHistory userHistory = new UserHistory();
        userHistory.setCorrelationId(correlationId);
        userHistory.setEntityId(entityId);
        userHistory.setReference(reference);
        userHistory.setChangeTime(changeTime);
        userHistory.setPrevious(previous);
        userHistory.setFirstName(firstName);
        userHistory.setLastName(lastName);
        userHistory.setBirthdate(birthdate);
        userHistory.setActive(active);
        userHistory.setRole(role);
        userHistory.setEmail(email);
        userHistory.setPasswordHash(passwordHash);
        userHistory.setEmailVerificationCode(emailVerificationCode);
        userHistory.setEmailVerificationCodeTimestamp(emailVerificationCodeTimestamp);
        userHistory.setEmailVerified(emailVerified);
        userHistory.setResetPasswordCode(resetPasswordCode);
        userHistory.setResetPasswordCodeTimestamp(resetPasswordCodeTimestamp);
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
    public List<UserHistory> findByBirthdate(Optional<ZonedDateTime> birthdate) {
        LOG.trace(".findByBirthdate()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(birthdate.map(qUserHistory.birthdate::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByBirthdateMandatory(ZonedDateTime birthdate) {
        LOG.trace(".findByBirthdateMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory).from(qUserHistory).where(qUserHistory.birthdate.eq(birthdate)).orderBy(qUserHistory.id.asc().nullsLast()).fetch();
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
    public List<UserHistory> findByPasswordHash(Optional<String> passwordHash) {
        LOG.trace(".findByPasswordHash()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(passwordHash.map(qUserHistory.passwordHash::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByPasswordHashMandatory(String passwordHash) {
        LOG.trace(".findByPasswordHashMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.passwordHash.eq(passwordHash))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByEmailVerificationCode(Optional<String> emailVerificationCode) {
        LOG.trace(".findByEmailVerificationCode()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(emailVerificationCode.map(qUserHistory.emailVerificationCode::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByEmailVerificationCodeMandatory(String emailVerificationCode) {
        LOG.trace(".findByEmailVerificationCodeMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.emailVerificationCode.eq(emailVerificationCode))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByEmailVerificationCodeTimestamp(Optional<ZonedDateTime> emailVerificationCodeTimestamp) {
        LOG.trace(".findByEmailVerificationCodeTimestamp()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(emailVerificationCodeTimestamp.map(qUserHistory.emailVerificationCodeTimestamp::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByEmailVerificationCodeTimestampMandatory(ZonedDateTime emailVerificationCodeTimestamp) {
        LOG.trace(".findByEmailVerificationCodeTimestampMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.emailVerificationCodeTimestamp.eq(emailVerificationCodeTimestamp))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByEmailVerified(Optional<Boolean> emailVerified) {
        LOG.trace(".findByEmailVerified()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(emailVerified.map(qUserHistory.emailVerified::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByEmailVerifiedMandatory(Boolean emailVerified) {
        LOG.trace(".findByEmailVerifiedMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.emailVerified.eq(emailVerified))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByResetPasswordCode(Optional<String> resetPasswordCode) {
        LOG.trace(".findByResetPasswordCode()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(resetPasswordCode.map(qUserHistory.resetPasswordCode::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByResetPasswordCodeMandatory(String resetPasswordCode) {
        LOG.trace(".findByResetPasswordCodeMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.resetPasswordCode.eq(resetPasswordCode))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByResetPasswordCodeTimestamp(Optional<ZonedDateTime> resetPasswordCodeTimestamp) {
        LOG.trace(".findByResetPasswordCodeTimestamp()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(resetPasswordCodeTimestamp.map(qUserHistory.resetPasswordCodeTimestamp::eq).orElse(null))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserHistory> findByResetPasswordCodeTimestampMandatory(ZonedDateTime resetPasswordCodeTimestamp) {
        LOG.trace(".findByResetPasswordCodeTimestampMandatory()");
        final QUserHistory qUserHistory = QUserHistory.userHistory;
        return factory.select(qUserHistory)
                .from(qUserHistory)
                .where(qUserHistory.resetPasswordCodeTimestamp.eq(resetPasswordCodeTimestamp))
                .orderBy(qUserHistory.id.asc().nullsLast())
                .fetch();
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
