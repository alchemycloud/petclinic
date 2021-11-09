package co.drytools.backend.repository.impl;

import co.drytools.backend.audit.AuditType;
import co.drytools.backend.model.QUser;
import co.drytools.backend.model.QVet;
import co.drytools.backend.model.User;
import co.drytools.backend.model.UserHistory;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.AbstractSimpleRepositoryImpl;
import co.drytools.backend.repository.UserHistoryRepository;
import co.drytools.backend.repository.UserRepository;
import co.drytools.backend.repository.tuple.UserActiveUsersVetsTuple;
import co.drytools.backend.util.AppThreadLocals;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.NumberPath;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
            ZonedDateTime birthdate,
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
        user.setBirthdate(birthdate);
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
    public List<User> adminUsers() {
        LOG.trace(".adminUsers()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.role.eq(UserRole.ADMIN)).orderBy(qUser.id.asc().nullsLast()).fetch();
    }

    @Override
    public List<User> findByNameOrLastNameLike(String firstname, String lastname) {
        LOG.trace(".findByNameOrLastNameLike()");
        final QUser qUser = QUser.user;
        return factory.select(qUser)
                .from(qUser)
                .where(new BooleanBuilder().and(qUser.firstName.like("%" + firstname + "%")).or(qUser.lastName.like("%" + lastname + "%")))
                .orderBy(qUser.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<User> filterUsers(Optional<String> email, Optional<String> first, Optional<String> last) {
        LOG.trace(".filterUsers()");
        final QUser qUser = QUser.user;
        return factory.select(qUser)
                .from(qUser)
                .where(
                        new BooleanBuilder()
                                .and(email.map(s -> qUser.email.like("%" + s + "%")).orElse(null))
                                .and(first.map(s -> qUser.firstName.like("%" + s + "%")).orElse(null))
                                .and(last.map(s -> qUser.lastName.like("%" + s + "%")).orElse(null)))
                .orderBy(qUser.id.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<UserActiveUsersVetsTuple> activeUsersVets() {
        LOG.trace(".activeUsersVets()");
        final QUser qUser = QUser.user;
        final QVet qVet = QVet.vet;
        return factory
                .select(qUser, qVet)
                .from(qUser)
                .innerJoin(qUser.vets, qVet)
                .where(new BooleanBuilder().and(qUser.role.eq(UserRole.VET)).and(qUser.active.eq(true)))
                .orderBy(qUser.lastName.asc().nullsLast())
                .fetch()
                .stream()
                .map(t -> new UserActiveUsersVetsTuple(t.get(qUser), t.get(qVet)))
                .collect(Collectors.toList());
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
    public List<User> findByBirthdate(ZonedDateTime birthdate) {
        LOG.trace(".findByBirthdate()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.birthdate.eq(birthdate)).orderBy(qUser.id.asc().nullsLast()).fetch();
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
    public List<User> findAllById(Collection<UserId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return factory.select(getEntityPathBase())
                .from(QUser.user)
                .where(QUser.user.id.in(ids.stream().map(UserId::getValue).collect(Collectors.toList())))
                .fetch();
    }

    @Override
    protected void preDelete(Collection<User> entities) {

        List<UserHistory> histories = userHistoryRepository.findAllHistoriesOfEntities(entities.stream().map(User::getId).collect(Collectors.toList()));
        for (UserHistory history : histories) {
            history.setReference(Optional.empty());
            userHistoryRepository.update(history);
        }
        for (User entity : entities) {
            AppThreadLocals.getAuditContext().register(entity, AuditType.DELETE, Optional.empty(), Optional.empty());
        }
    }
}
