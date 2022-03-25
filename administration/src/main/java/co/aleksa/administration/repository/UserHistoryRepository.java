package co.aleksa.administration.repository;

import co.aleksa.administration.model.User;
import co.aleksa.administration.model.UserHistory;
import co.aleksa.administration.model.enumeration.UserRole;
import co.aleksa.administration.model.id.UserHistoryId;
import co.aleksa.administration.model.id.UserId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public interface UserHistoryRepository extends SimpleRepository<UserHistory, UserHistoryId> {

    UserHistory create(
            Optional<String> correlationId,
            Long entityId,
            Optional<User> reference,
            ZonedDateTime changeTime,
            Optional<UserHistory> previous,
            Optional<String> firstName,
            Optional<String> lastName,
            Optional<ZonedDateTime> birthday,
            Optional<Boolean> active,
            Optional<UserRole> role,
            Optional<String> email,
            Optional<String> passwordHash,
            Optional<String> emailVerificationCode,
            Optional<ZonedDateTime> emailVerificationCodeTimestamp,
            Optional<Boolean> emailVerified,
            Optional<String> resetPasswordCode,
            Optional<ZonedDateTime> resetPasswordCodeTimestamp);

    UserHistory getLastHistoryFor(UserId reference);

    List<Pair<User, Optional<UserHistory>>> findAllWithLastHistory(Set<UserId> ids);

    List<UserHistory> findAllHistoriesOfEntities(List<UserId> referenceId);

    List<UserHistory> findByCorrelationId(Optional<String> correlationId);

    List<UserHistory> findByCorrelationIdMandatory(String correlationId);

    List<UserHistory> findByEntityId(Long entityId);

    List<UserHistory> findByChangeTime(ZonedDateTime changeTime);

    List<UserHistory> findByFirstName(Optional<String> firstName);

    List<UserHistory> findByFirstNameMandatory(String firstName);

    List<UserHistory> findByLastName(Optional<String> lastName);

    List<UserHistory> findByLastNameMandatory(String lastName);

    List<UserHistory> findByBirthday(Optional<ZonedDateTime> birthday);

    List<UserHistory> findByBirthdayMandatory(ZonedDateTime birthday);

    List<UserHistory> findByActive(Optional<Boolean> active);

    List<UserHistory> findByActiveMandatory(Boolean active);

    List<UserHistory> findByRole(Optional<UserRole> role);

    List<UserHistory> findByRoleMandatory(UserRole role);

    List<UserHistory> findByEmail(Optional<String> email);

    List<UserHistory> findByEmailMandatory(String email);

    List<UserHistory> findByPasswordHash(Optional<String> passwordHash);

    List<UserHistory> findByPasswordHashMandatory(String passwordHash);

    List<UserHistory> findByEmailVerificationCode(Optional<String> emailVerificationCode);

    List<UserHistory> findByEmailVerificationCodeMandatory(String emailVerificationCode);

    List<UserHistory> findByEmailVerificationCodeTimestamp(Optional<ZonedDateTime> emailVerificationCodeTimestamp);

    List<UserHistory> findByEmailVerificationCodeTimestampMandatory(ZonedDateTime emailVerificationCodeTimestamp);

    List<UserHistory> findByEmailVerified(Optional<Boolean> emailVerified);

    List<UserHistory> findByEmailVerifiedMandatory(Boolean emailVerified);

    List<UserHistory> findByResetPasswordCode(Optional<String> resetPasswordCode);

    List<UserHistory> findByResetPasswordCodeMandatory(String resetPasswordCode);

    List<UserHistory> findByResetPasswordCodeTimestamp(Optional<ZonedDateTime> resetPasswordCodeTimestamp);

    List<UserHistory> findByResetPasswordCodeTimestampMandatory(ZonedDateTime resetPasswordCodeTimestamp);
}
