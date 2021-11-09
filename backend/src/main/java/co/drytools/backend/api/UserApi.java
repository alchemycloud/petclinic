package co.drytools.backend.api;

import co.drytools.backend.api.dto.userapi.AdminUsersResponse;
import co.drytools.backend.api.dto.userapi.CreateUserRequest;
import co.drytools.backend.api.dto.userapi.CreateUserResponse;
import co.drytools.backend.api.dto.userapi.DeleteUserRequest;
import co.drytools.backend.api.dto.userapi.NonAdminsResponse;
import co.drytools.backend.api.dto.userapi.ReadUserRequest;
import co.drytools.backend.api.dto.userapi.ReadUserResponse;
import co.drytools.backend.api.dto.userapi.UpdateUserRequest;
import co.drytools.backend.api.dto.userapi.UpdateUserResponse;
import co.drytools.backend.api.dto.userapi.UserActivationSimpleDTO;
import co.drytools.backend.api.dto.userapi.UserDTO;
import co.drytools.backend.api.dto.userapi.UserResponseDTO;
import co.drytools.backend.api.dto.userapi.UsersResponse;
import co.drytools.backend.model.User;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.UserRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserApi {
    private static final Logger LOG = LoggerFactory.getLogger(UserApi.class);

    @Inject private UserRepository userRepository;

    public ReadUserResponse readUser(ReadUserRequest dto) {
        LOG.trace("readUser {}", dto.getId());
        // TODO check security constraints(id)

        final User model = userRepository.getOne(dto.getId());
        final UserId responseId = model.getId();
        final String responseFirstName = model.getFirstName();
        final String responseLastName = model.getLastName();
        final ZonedDateTime responseBirthdate = model.getBirthdate();
        final Boolean responseActive = model.getActive();
        final UserRole responseRole = model.getRole();
        final String responseEmail = model.getEmail();
        final String responsePasswordHash = model.getPasswordHash();
        final String responseEmailVerificationCode = model.getEmailVerificationCode().orElse(null);
        final ZonedDateTime responseEmailVerificationCodeTimestamp = model.getEmailVerificationCodeTimestamp().orElse(null);
        final Boolean responseEmailVerified = model.getEmailVerified();
        final String responseResetPasswordCode = model.getResetPasswordCode().orElse(null);
        final ZonedDateTime responseResetPasswordCodeTimestamp = model.getResetPasswordCodeTimestamp().orElse(null);
        return new ReadUserResponse(
                responseId,
                responseFirstName,
                responseLastName,
                responseBirthdate,
                responseActive,
                responseRole,
                responseEmail,
                responsePasswordHash,
                responseEmailVerificationCode,
                responseEmailVerificationCodeTimestamp,
                responseEmailVerified,
                responseResetPasswordCode,
                responseResetPasswordCodeTimestamp);
    }

    public CreateUserResponse createUser(CreateUserRequest dto) {
        LOG.trace("createUser");
        // TODO check security constraints

        final String firstName = dto.getFirstName();
        final String lastName = dto.getLastName();
        final ZonedDateTime birthdate = dto.getBirthdate();
        final Boolean active = dto.getActive();
        final UserRole role = dto.getRole();
        final String email = dto.getEmail();
        final String passwordHash = dto.getPasswordHash();
        final Optional<String> emailVerificationCode = Optional.ofNullable(dto.getEmailVerificationCode());
        final Optional<ZonedDateTime> emailVerificationCodeTimestamp = Optional.ofNullable(dto.getEmailVerificationCodeTimestamp());
        final Boolean emailVerified = dto.getEmailVerified();
        final Optional<String> resetPasswordCode = Optional.ofNullable(dto.getResetPasswordCode());
        final Optional<ZonedDateTime> resetPasswordCodeTimestamp = Optional.ofNullable(dto.getResetPasswordCodeTimestamp());
        final User model =
                userRepository.create(null, null, null, null, null, null, null, Optional.empty(), Optional.empty(), null, Optional.empty(), Optional.empty());

        final UserId responseId = model.getId();
        final String responseFirstName = model.getFirstName();
        final String responseLastName = model.getLastName();
        final ZonedDateTime responseBirthdate = model.getBirthdate();
        final Boolean responseActive = model.getActive();
        final UserRole responseRole = model.getRole();
        final String responseEmail = model.getEmail();
        final String responsePasswordHash = model.getPasswordHash();
        final String responseEmailVerificationCode = model.getEmailVerificationCode().orElse(null);
        final ZonedDateTime responseEmailVerificationCodeTimestamp = model.getEmailVerificationCodeTimestamp().orElse(null);
        final Boolean responseEmailVerified = model.getEmailVerified();
        final String responseResetPasswordCode = model.getResetPasswordCode().orElse(null);
        final ZonedDateTime responseResetPasswordCodeTimestamp = model.getResetPasswordCodeTimestamp().orElse(null);
        return new CreateUserResponse(
                responseId,
                responseFirstName,
                responseLastName,
                responseBirthdate,
                responseActive,
                responseRole,
                responseEmail,
                responsePasswordHash,
                responseEmailVerificationCode,
                responseEmailVerificationCodeTimestamp,
                responseEmailVerified,
                responseResetPasswordCode,
                responseResetPasswordCodeTimestamp);
    }

    public UpdateUserResponse updateUser(UpdateUserRequest dto) {
        LOG.trace("updateUser {}", dto.getId());
        // TODO check security constraints(id)

        final User model = userRepository.getOne(dto.getId());
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setBirthdate(dto.getBirthdate());
        model.setActive(dto.getActive());
        model.setRole(dto.getRole());
        model.setEmail(dto.getEmail());
        model.setPasswordHash(dto.getPasswordHash());
        model.setEmailVerificationCode(Optional.ofNullable(dto.getEmailVerificationCode()));
        model.setEmailVerificationCodeTimestamp(Optional.ofNullable(dto.getEmailVerificationCodeTimestamp()));
        model.setEmailVerified(dto.getEmailVerified());
        model.setResetPasswordCode(Optional.ofNullable(dto.getResetPasswordCode()));
        model.setResetPasswordCodeTimestamp(Optional.ofNullable(dto.getResetPasswordCodeTimestamp()));
        userRepository.update(model);

        final UserId responseId = model.getId();
        final String responseFirstName = model.getFirstName();
        final String responseLastName = model.getLastName();
        final ZonedDateTime responseBirthdate = model.getBirthdate();
        final Boolean responseActive = model.getActive();
        final UserRole responseRole = model.getRole();
        final String responseEmail = model.getEmail();
        final String responsePasswordHash = model.getPasswordHash();
        final String responseEmailVerificationCode = model.getEmailVerificationCode().orElse(null);
        final ZonedDateTime responseEmailVerificationCodeTimestamp = model.getEmailVerificationCodeTimestamp().orElse(null);
        final Boolean responseEmailVerified = model.getEmailVerified();
        final String responseResetPasswordCode = model.getResetPasswordCode().orElse(null);
        final ZonedDateTime responseResetPasswordCodeTimestamp = model.getResetPasswordCodeTimestamp().orElse(null);
        return new UpdateUserResponse(
                responseId,
                responseFirstName,
                responseLastName,
                responseBirthdate,
                responseActive,
                responseRole,
                responseEmail,
                responsePasswordHash,
                responseEmailVerificationCode,
                responseEmailVerificationCodeTimestamp,
                responseEmailVerified,
                responseResetPasswordCode,
                responseResetPasswordCodeTimestamp);
    }

    public void deleteUser(DeleteUserRequest dto) {
        LOG.trace("deleteUser {}", dto.getId());
        // TODO check security constraints(id)

        userRepository.deleteById(dto.getId());
    }

    public List<UsersResponse> users() {
        LOG.trace("users");
        // TODO check security constraints

        final List<User> models = userRepository.findAll();
        return models.stream()
                .map(
                        model -> {
                            final UserId responseId = model.getId();
                            final String responseEmail = model.getEmail();
                            final String responseFirstName = model.getFirstName();
                            final String responseLastName = model.getLastName();
                            return new UsersResponse(responseId, responseEmail, responseFirstName, responseLastName);
                        })
                .collect(Collectors.toList());
    }

    public List<NonAdminsResponse> nonAdmins() {
        LOG.trace("nonAdmins");
        // TODO check security constraints

        final List<User> models = userRepository.nonAdmins();
        return models.stream()
                .map(
                        model -> {
                            final UserId responseId = model.getId();
                            final String responseFirstName = model.getFirstName();
                            final String responseLastName = model.getLastName();
                            final ZonedDateTime responseBirthdate = model.getBirthdate();
                            final Boolean responseActive = model.getActive();
                            final UserRole responseRole = model.getRole();
                            final String responseEmail = model.getEmail();
                            final String responsePasswordHash = model.getPasswordHash();
                            final String responseEmailVerificationCode = model.getEmailVerificationCode().orElse(null);
                            final ZonedDateTime responseEmailVerificationCodeTimestamp = model.getEmailVerificationCodeTimestamp().orElse(null);
                            final Boolean responseEmailVerified = model.getEmailVerified();
                            final String responseResetPasswordCode = model.getResetPasswordCode().orElse(null);
                            final ZonedDateTime responseResetPasswordCodeTimestamp = model.getResetPasswordCodeTimestamp().orElse(null);
                            return new NonAdminsResponse(
                                    responseId,
                                    responseFirstName,
                                    responseLastName,
                                    responseBirthdate,
                                    responseActive,
                                    responseRole,
                                    responseEmail,
                                    responsePasswordHash,
                                    responseEmailVerificationCode,
                                    responseEmailVerificationCodeTimestamp,
                                    responseEmailVerified,
                                    responseResetPasswordCode,
                                    responseResetPasswordCodeTimestamp);
                        })
                .collect(Collectors.toList());
    }

    public List<AdminUsersResponse> adminUsers() {
        LOG.trace("adminUsers");
        // TODO check security constraints

        final List<User> models = userRepository.adminUsers();
        return models.stream()
                .map(
                        model -> {
                            final UserId responseId = model.getId();
                            final String responseFirstName = model.getFirstName();
                            final String responseLastName = model.getLastName();
                            final ZonedDateTime responseBirthdate = model.getBirthdate();
                            final Boolean responseActive = model.getActive();
                            final UserRole responseRole = model.getRole();
                            final String responseEmail = model.getEmail();
                            final String responsePasswordHash = model.getPasswordHash();
                            final String responseEmailVerificationCode = model.getEmailVerificationCode().orElse(null);
                            final ZonedDateTime responseEmailVerificationCodeTimestamp = model.getEmailVerificationCodeTimestamp().orElse(null);
                            final Boolean responseEmailVerified = model.getEmailVerified();
                            final String responseResetPasswordCode = model.getResetPasswordCode().orElse(null);
                            final ZonedDateTime responseResetPasswordCodeTimestamp = model.getResetPasswordCodeTimestamp().orElse(null);
                            return new AdminUsersResponse(
                                    responseId,
                                    responseFirstName,
                                    responseLastName,
                                    responseBirthdate,
                                    responseActive,
                                    responseRole,
                                    responseEmail,
                                    responsePasswordHash,
                                    responseEmailVerificationCode,
                                    responseEmailVerificationCodeTimestamp,
                                    responseEmailVerified,
                                    responseResetPasswordCode,
                                    responseResetPasswordCodeTimestamp);
                        })
                .collect(Collectors.toList());
    }

    public UserResponseDTO setUserActiveStatusSimple(UserActivationSimpleDTO dto) {
        LOG.trace("setUserActiveStatusSimple {}", dto.getId());
        // TODO check security constraints(id)

        final User model = userRepository.getOne(dto.getId());
        model.setActive(dto.getActive());
        userRepository.update(model);

        final UserId responseId = model.getId();
        final String responseEmail = model.getEmail();
        final String responseFirstName = model.getFirstName();
        final String responseLastName = model.getLastName();
        final ZonedDateTime responseBirthdate = model.getBirthdate();
        final Boolean responseActive = model.getActive();
        return new UserResponseDTO(responseId, responseEmail, responseFirstName, responseLastName, responseBirthdate, responseActive);
    }

    public UserResponseDTO getActiveUser(UserDTO dto) {
        LOG.trace("getActiveUser {}", dto.getId());
        // TODO check security constraints(id)

        throw new UnsupportedOperationException();
    }
}
