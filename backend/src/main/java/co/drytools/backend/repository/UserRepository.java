package co.drytools.backend.repository;

import co.drytools.backend.model.User;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.tuple.UserActiveUsersVetsTuple;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends SimpleRepository<User, UserId> {

    User create(
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
            Optional<ZonedDateTime> resetPasswordCodeTimestamp);

    List<User> adminUsers();

    List<User> findByNameOrLastNameLike(String firstname, String lastname);

    List<User> filterUsers(Optional<String> email, Optional<String> first, Optional<String> last);

    List<UserActiveUsersVetsTuple> activeUsersVets();

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findByBirthdate(ZonedDateTime birthdate);

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
}
