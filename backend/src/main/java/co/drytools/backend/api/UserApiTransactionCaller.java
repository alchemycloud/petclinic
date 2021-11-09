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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserApiTransactionCaller {
    private static final Logger LOG = LoggerFactory.getLogger(UserApiTransactionCaller.class);

    @Inject private AuditFacade auditFacade;

    @Inject private UserApi userApi;

    @Transactional(readOnly = true)
    public ReadUserResponse readUser(ReadUserRequest dto) {
        LOG.trace("readUser {}", dto.getId());

        final ReadUserResponse result = userApi.readUser(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest dto) {
        LOG.trace("createUser");

        final CreateUserResponse result = userApi.createUser(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public UpdateUserResponse updateUser(UpdateUserRequest dto) {
        LOG.trace("updateUser {}", dto.getId());

        final UpdateUserResponse result = userApi.updateUser(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void deleteUser(DeleteUserRequest dto) {
        LOG.trace("deleteUser {}", dto.getId());

        userApi.deleteUser(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional(readOnly = true)
    public List<UsersResponse> users() {
        LOG.trace("users");

        final List<UsersResponse> result = userApi.users();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<NonAdminsResponse> nonAdmins() {
        LOG.trace("nonAdmins");

        final List<NonAdminsResponse> result = userApi.nonAdmins();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<AdminUsersResponse> adminUsers() {
        LOG.trace("adminUsers");

        final List<AdminUsersResponse> result = userApi.adminUsers();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public UserResponseDTO setUserActiveStatusSimple(UserActivationSimpleDTO dto) {
        LOG.trace("setUserActiveStatusSimple {}", dto.getId());

        final UserResponseDTO result = userApi.setUserActiveStatusSimple(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getActiveUser(UserDTO dto) {
        LOG.trace("getActiveUser {}", dto.getId());

        final UserResponseDTO result = userApi.getActiveUser(dto);
        auditFacade.flushInTransaction();
        return result;
    }
}
