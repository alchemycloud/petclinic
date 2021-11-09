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
import co.drytools.backend.audit.AuditFacade;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(UserApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private UserApiTransactionCaller userApiTransactionCaller;

    public ReadUserResponse readUser(ReadUserRequest dto) {
        LOG.trace("readUser {}", dto.getId());

        final ReadUserResponse result = userApiTransactionCaller.readUser(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public CreateUserResponse createUser(CreateUserRequest dto) {
        LOG.trace("createUser");

        final CreateUserResponse result = userApiTransactionCaller.createUser(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UpdateUserResponse updateUser(UpdateUserRequest dto) {
        LOG.trace("updateUser {}", dto.getId());

        final UpdateUserResponse result = userApiTransactionCaller.updateUser(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void deleteUser(DeleteUserRequest dto) {
        LOG.trace("deleteUser {}", dto.getId());

        userApiTransactionCaller.deleteUser(dto);
        auditFacade.flushAfterTransaction();
    }

    public List<UsersResponse> users() {
        LOG.trace("users");

        final List<UsersResponse> result = userApiTransactionCaller.users();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<NonAdminsResponse> nonAdmins() {
        LOG.trace("nonAdmins");

        final List<NonAdminsResponse> result = userApiTransactionCaller.nonAdmins();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<AdminUsersResponse> adminUsers() {
        LOG.trace("adminUsers");

        final List<AdminUsersResponse> result = userApiTransactionCaller.adminUsers();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UserResponseDTO setUserActiveStatusSimple(UserActivationSimpleDTO dto) {
        LOG.trace("setUserActiveStatusSimple {}", dto.getId());

        final UserResponseDTO result = userApiTransactionCaller.setUserActiveStatusSimple(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UserResponseDTO getActiveUser(UserDTO dto) {
        LOG.trace("getActiveUser {}", dto.getId());

        final UserResponseDTO result = userApiTransactionCaller.getActiveUser(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }
}
