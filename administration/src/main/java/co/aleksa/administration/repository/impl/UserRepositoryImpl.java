package co.aleksa.administration.repository.impl;

import co.aleksa.administration.audit.AuditType;
import co.aleksa.administration.model.QUser;
import co.aleksa.administration.model.User;
import co.aleksa.administration.model.UserHistory;
import co.aleksa.administration.model.enumeration.UserRole;
import co.aleksa.administration.model.id.UserId;
import co.aleksa.administration.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.administration.repository.UserHistoryRepository;
import co.aleksa.administration.repository.UserRepository;
import co.aleksa.administration.util.AppThreadLocals;
import com.querydsl.core.BooleanBuilder;
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
public class UserRepositoryImpl extends AbstractSimpleRepositoryImpl<User, UserId> implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Inject private UserHistoryRepository userHistoryRepository;

    @Override
    protected Class<User> getDomainClass() {
        return User.class;
    }

    @Override
    protected EntityPath<User> getEntityPathBase() {
        return QUser.user;
    }

    @Override
    public NumberPath<Long> getEntityIdPathBase() {
        return QUser.user.id;
    }

    @Override
    protected UserId getId(User entity) {
        return entity.getId();
    }

    @Override
    public User create(
            String firstName,
            String lastName,
            ZonedDateTime birthday,
            Boolean active,
            UserRole role,
            String email,
            String passwordHash,
            Optional<String> emailVerificationCode,
            Optional<ZonedDateTime> emailVerificationCodeTimestamp,
            Boolean emailVerified,
            Optional<String> resetPasswordCode,
            Optional<ZonedDateTime> resetPasswordCodeTimestamp) {
        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthday);
        user.setActive(active);
        user.setRole(role);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setEmailVerificationCode(emailVerificationCode);
        user.setEmailVerificationCodeTimestamp(emailVerificationCodeTimestamp);
        user.setEmailVerified(emailVerified);
        user.setResetPasswordCode(resetPasswordCode);
        user.setResetPasswordCodeTimestamp(resetPasswordCodeTimestamp);
        entityManager.persist(user);
        postSave(user);
        AppThreadLocals.getAuditContext().register(user, AuditType.CREATE, Optional.empty(), Optional.empty());
        return user;
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        LOG.trace(".findByFirstName()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.firstName.eq(firstName)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<User> findByLastName(String lastName) {
        LOG.trace(".findByLastName()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.lastName.eq(lastName)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<User> findByBirthday(ZonedDateTime birthday) {
        LOG.trace(".findByBirthday()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.birthday.eq(birthday)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<User> findByActive(Boolean active) {
        LOG.trace(".findByActive()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.active.eq(active)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<User> findByRole(UserRole role) {
        LOG.trace(".findByRole()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.role.eq(role)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        LOG.trace(".findByEmail()");
        final QUser qUser = QUser.user;
        return Optional.ofNullable(factory.select(qUser).from(qUser).where(qUser.email.eq(email)).orderBy(qUser.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public List<User> findByPasswordHash(String passwordHash) {
        LOG.trace(".findByPasswordHash()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.passwordHash.eq(passwordHash)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<User> findByEmailVerificationCode(Optional<String> emailVerificationCode) {
        LOG.trace(".findByEmailVerificationCode()");
        final QUser qUser = QUser.user;
        return factory.select(qUser)
                .from(qUser)
                .where(emailVerificationCode.map(qUser.emailVerificationCode::eq).orElse(null))
                .orderBy(qUser.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public Optional<User> findByEmailVerificationCodeMandatory(String emailVerificationCode) {
        LOG.trace(".findByEmailVerificationCodeMandatory()");
        final QUser qUser = QUser.user;
        return Optional.ofNullable(
                factory.select(qUser).from(qUser).where(qUser.emailVerificationCode.eq(emailVerificationCode)).orderBy(qUser.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public List<User> findByEmailVerificationCodeTimestamp(Optional<ZonedDateTime> emailVerificationCodeTimestamp) {
        LOG.trace(".findByEmailVerificationCodeTimestamp()");
        final QUser qUser = QUser.user;
        return factory.select(qUser)
                .from(qUser)
                .where(emailVerificationCodeTimestamp.map(qUser.emailVerificationCodeTimestamp::eq).orElse(null))
                .orderBy(qUser.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<User> findByEmailVerificationCodeTimestampMandatory(ZonedDateTime emailVerificationCodeTimestamp) {
        LOG.trace(".findByEmailVerificationCodeTimestampMandatory()");
        final QUser qUser = QUser.user;
        return factory.select(qUser)
                .from(qUser)
                .where(qUser.emailVerificationCodeTimestamp.eq(emailVerificationCodeTimestamp))
                .orderBy(qUser.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<User> findByEmailVerified(Boolean emailVerified) {
        LOG.trace(".findByEmailVerified()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.emailVerified.eq(emailVerified)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<User> findByResetPasswordCode(Optional<String> resetPasswordCode) {
        LOG.trace(".findByResetPasswordCode()");
        final QUser qUser = QUser.user;
        return factory.select(qUser)
                .from(qUser)
                .where(resetPasswordCode.map(qUser.resetPasswordCode::eq).orElse(null))
                .orderBy(qUser.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public Optional<User> findByResetPasswordCodeMandatory(String resetPasswordCode) {
        LOG.trace(".findByResetPasswordCodeMandatory()");
        final QUser qUser = QUser.user;
        return Optional.ofNullable(
                factory.select(qUser).from(qUser).where(qUser.resetPasswordCode.eq(resetPasswordCode)).orderBy(qUser.id.asc().nullsLast()).fetchOne());
    }

    @Override
    public List<User> findByResetPasswordCodeTimestamp(Optional<ZonedDateTime> resetPasswordCodeTimestamp) {
        LOG.trace(".findByResetPasswordCodeTimestamp()");
        final QUser qUser = QUser.user;
        return factory.select(qUser)
                .from(qUser)
                .where(resetPasswordCodeTimestamp.map(qUser.resetPasswordCodeTimestamp::eq).orElse(null))
                .orderBy(qUser.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<User> findByResetPasswordCodeTimestampMandatory(ZonedDateTime resetPasswordCodeTimestamp) {
        LOG.trace(".findByResetPasswordCodeTimestampMandatory()");
        final QUser qUser = QUser.user;
        return factory.select(qUser)
                .from(qUser)
                .where(qUser.resetPasswordCodeTimestamp.eq(resetPasswordCodeTimestamp))
                .orderBy(qUser.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<User> nonAdmins() {
        LOG.trace(".nonAdmins()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.role.ne(UserRole.ADMIN)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<User> admins() {
        LOG.trace(".admins()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.role.eq(UserRole.ADMIN)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public Optional<User> getActiveUser(UserId userId) {
        LOG.trace(".getActiveUser(userId: {})", userId);
        final QUser qUser = QUser.user;
        return Optional.ofNullable(
                factory.select(qUser)
                        .from(qUser)
                        .where(new BooleanBuilder().and(qUser.active.eq(true)).and(qUser.id.eq(userId.getValue())))
                        .orderBy(qUser.id.asc().nullsLast())
                        .fetchOne());
    }

    @Override
    public List<User> findAllById(Collection<UserId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase()).from(QUser.user).where(QUser.user.id.in(ids.stream().map(UserId::getValue).toList())).fetch();
    }

    @Override
    protected void preDelete(Collection<User> entities) {

        List<UserHistory> histories = userHistoryRepository.findAllHistoriesOfEntities(entities.stream().map(User::getId).toList());
        for (UserHistory history : histories) {
            history.setReference(Optional.empty());
            userHistoryRepository.update(history);
        }
        for (User entity : entities) {
            AppThreadLocals.getAuditContext().register(entity, AuditType.DELETE, Optional.empty(), Optional.empty());
        }
    }
}
