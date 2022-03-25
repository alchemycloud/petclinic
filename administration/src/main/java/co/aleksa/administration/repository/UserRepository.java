package co.aleksa.administration.repository;

import co.aleksa.administration.model.User;
import co.aleksa.administration.model.enumeration.UserRole;
import co.aleksa.administration.model.id.UserId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends SimpleRepository<User, UserId> {

    User create(
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
            Optional<ZonedDateTime> resetPasswordCodeTimestamp);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findByBirthday(ZonedDateTime birthday);

    List<User> findByActive(Boolean active);

    List<User> findByRole(UserRole role);

    Optional<User> findByEmail(String email);

    List<User> findByPasswordHash(String passwordHash);

    List<User> findByEmailVerificationCode(Optional<String> emailVerificationCode);

    Optional<User> findByEmailVerificationCodeMandatory(String emailVerificationCode);

    List<User> findByEmailVerificationCodeTimestamp(Optional<ZonedDateTime> emailVerificationCodeTimestamp);

    List<User> findByEmailVerificationCodeTimestampMandatory(ZonedDateTime emailVerificationCodeTimestamp);

    List<User> findByEmailVerified(Boolean emailVerified);

    List<User> findByResetPasswordCode(Optional<String> resetPasswordCode);

    Optional<User> findByResetPasswordCodeMandatory(String resetPasswordCode);

    List<User> findByResetPasswordCodeTimestamp(Optional<ZonedDateTime> resetPasswordCodeTimestamp);

    List<User> findByResetPasswordCodeTimestampMandatory(ZonedDateTime resetPasswordCodeTimestamp);

    List<User> nonAdmins();

    List<User> admins();

    Optional<User> getActiveUser(UserId userId);
}
