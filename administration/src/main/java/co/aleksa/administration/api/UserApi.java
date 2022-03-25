package co.aleksa.administration.api;

import co.aleksa.administration.api.dto.userapi.AdminsResponse;
import co.aleksa.administration.api.dto.userapi.CreateUserOnTenantRequest;
import co.aleksa.administration.api.dto.userapi.CreateUserRequest;
import co.aleksa.administration.api.dto.userapi.CreateUserResponse;
import co.aleksa.administration.api.dto.userapi.DeleteUserRequest;
import co.aleksa.administration.api.dto.userapi.GetActiveUserRequest;
import co.aleksa.administration.api.dto.userapi.GetActiveUserResponse;
import co.aleksa.administration.api.dto.userapi.NonAdminsResponse;
import co.aleksa.administration.api.dto.userapi.ReadUserRequest;
import co.aleksa.administration.api.dto.userapi.ReadUserResponse;
import co.aleksa.administration.api.dto.userapi.RemoveUserFromTenantRequest;
import co.aleksa.administration.api.dto.userapi.UpdateUserRequest;
import co.aleksa.administration.api.dto.userapi.UpdateUserResponse;
import co.aleksa.administration.api.dto.userapi.UserActivationDTO;
import co.aleksa.administration.model.TenantUser;
import co.aleksa.administration.model.User;
import co.aleksa.administration.model.enumeration.UserRole;
import co.aleksa.administration.model.id.UserId;
import co.aleksa.administration.repository.TenantUserRepository;
import co.aleksa.administration.repository.UserRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserApi {
    private static final Logger LOG = LoggerFactory.getLogger(UserApi.class);

    @Inject private UserRepository userRepository;

    @Inject private TenantUserRepository tenantUserRepository;

    public ReadUserResponse readUser(ReadUserRequest dto) {
        LOG.trace("readUser {}", dto.getId());
        // TODO check security constraints(id)

        final User model = userRepository.getOne(dto.getId());
        final UserId responseId = model.getId();
        final String responseFirstName = model.getFirstName();
        final String responseLastName = model.getLastName();
        final ZonedDateTime responseBirthday = model.getBirthday();
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
                responseBirthday,
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
        final ZonedDateTime birthday = dto.getBirthday();
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
        final ZonedDateTime responseBirthday = model.getBirthday();
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
                responseBirthday,
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
        model.setBirthday(dto.getBirthday());
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
        final ZonedDateTime responseBirthday = model.getBirthday();
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
                responseBirthday,
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
                            final ZonedDateTime responseBirthday = model.getBirthday();
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
                                    responseBirthday,
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
                .toList();
    }

    public List<AdminsResponse> admins() {
        LOG.trace("admins");
        // TODO check security constraints

        final List<User> models = userRepository.admins();
        return models.stream()
                .map(
                        model -> {
                            final UserId responseId = model.getId();
                            final String responseFirstName = model.getFirstName();
                            final String responseLastName = model.getLastName();
                            final ZonedDateTime responseBirthday = model.getBirthday();
                            final Boolean responseActive = model.getActive();
                            final UserRole responseRole = model.getRole();
                            final String responseEmail = model.getEmail();
                            final String responsePasswordHash = model.getPasswordHash();
                            final String responseEmailVerificationCode = model.getEmailVerificationCode().orElse(null);
                            final ZonedDateTime responseEmailVerificationCodeTimestamp = model.getEmailVerificationCodeTimestamp().orElse(null);
                            final Boolean responseEmailVerified = model.getEmailVerified();
                            final String responseResetPasswordCode = model.getResetPasswordCode().orElse(null);
                            final ZonedDateTime responseResetPasswordCodeTimestamp = model.getResetPasswordCodeTimestamp().orElse(null);
                            return new AdminsResponse(
                                    responseId,
                                    responseFirstName,
                                    responseLastName,
                                    responseBirthday,
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
                .toList();
    }

    public void activateUser(UserActivationDTO dto) {
        LOG.trace("activateUser {}", dto.getId());
        // TODO check security constraints(id)

        final User model = userRepository.getOne(dto.getId());
        model.setActive(dto.getActive());
        userRepository.update(model);
    }

    public GetActiveUserResponse getActiveUser(GetActiveUserRequest dto) {
        LOG.trace("getActiveUser {}", dto.getUserId());
        // TODO check security constraints(userId)

        final User model = userRepository.getActiveUser(dto.getUserId()).get();
        final UserId responseId = model.getId();
        final String responseFirstName = model.getFirstName();
        final String responseLastName = model.getLastName();
        final ZonedDateTime responseBirthday = model.getBirthday();
        final Boolean responseActive = model.getActive();
        final UserRole responseRole = model.getRole();
        final String responseEmail = model.getEmail();
        final String responsePasswordHash = model.getPasswordHash();
        final String responseEmailVerificationCode = model.getEmailVerificationCode().orElse(null);
        final ZonedDateTime responseEmailVerificationCodeTimestamp = model.getEmailVerificationCodeTimestamp().orElse(null);
        final Boolean responseEmailVerified = model.getEmailVerified();
        final String responseResetPasswordCode = model.getResetPasswordCode().orElse(null);
        final ZonedDateTime responseResetPasswordCodeTimestamp = model.getResetPasswordCodeTimestamp().orElse(null);
        return new GetActiveUserResponse(
                responseId,
                responseFirstName,
                responseLastName,
                responseBirthday,
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

    public void createUserOnTenant(CreateUserOnTenantRequest dto) {
        LOG.trace("createUserOnTenant");
        // TODO check security constraints

        // model.setTenant(); // TODO set this field manually
        // model.setEmail(); // TODO set this field manually
        // model.setRole(); // TODO set this field manually
        final TenantUser model = tenantUserRepository.create(null, null, null);
    }

    public void removeUserFromTenant(RemoveUserFromTenantRequest dto) {
        LOG.trace("removeUserFromTenant {}", dto.getId());
        // TODO check security constraints(id)

        throw new UnsupportedOperationException();
    }
}
