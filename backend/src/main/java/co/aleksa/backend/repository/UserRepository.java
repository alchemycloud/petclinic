package co.aleksa.backend.repository;

import co.aleksa.backend.model.User;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.UserId;
import java.time.ZonedDateTime;
import java.util.List;

public interface UserRepository extends SimpleRepository<User, UserId> {

    User create(String firstName, String lastName, String email, ZonedDateTime birthday, Boolean active, UserRole role);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findByEmail(String email);

    List<User> findByBirthday(ZonedDateTime birthday);

    List<User> findByActive(Boolean active);

    List<User> findByRole(UserRole role);
}
