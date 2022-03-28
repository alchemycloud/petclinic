package co.aleksa.backend.repository.impl;

import co.aleksa.backend.audit.AuditType;
import co.aleksa.backend.model.QUser;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.UserHistory;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.repository.AbstractSimpleRepositoryImpl;
import co.aleksa.backend.repository.UserHistoryRepository;
import co.aleksa.backend.repository.UserRepository;
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
    public User create(String firstName, String lastName, String email, ZonedDateTime birthday, Boolean active, UserRole role) {
        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setActive(active);
        user.setRole(role);
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
    public List<User> findByEmail(String email) {
        LOG.trace(".findByEmail()");
        final QUser qUser = QUser.user;
        return factory.select(qUser).from(qUser).where(qUser.email.eq(email)).orderBy(qUser.id.asc().nullsLast()).fetch();
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
