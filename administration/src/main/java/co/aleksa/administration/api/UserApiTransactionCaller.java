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
import co.aleksa.administration.audit.AuditFacade;
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
    public List<NonAdminsResponse> nonAdmins() {
        LOG.trace("nonAdmins");

        final List<NonAdminsResponse> result = userApi.nonAdmins();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<AdminsResponse> admins() {
        LOG.trace("admins");

        final List<AdminsResponse> result = userApi.admins();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void activateUser(UserActivationDTO dto) {
        LOG.trace("activateUser {}", dto.getId());

        userApi.activateUser(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional(readOnly = true)
    public GetActiveUserResponse getActiveUser(GetActiveUserRequest dto) {
        LOG.trace("getActiveUser {}", dto.getUserId());

        final GetActiveUserResponse result = userApi.getActiveUser(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void createUserOnTenant(CreateUserOnTenantRequest dto) {
        LOG.trace("createUserOnTenant");

        userApi.createUserOnTenant(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional
    public void removeUserFromTenant(RemoveUserFromTenantRequest dto) {
        LOG.trace("removeUserFromTenant {}", dto.getId());

        userApi.removeUserFromTenant(dto);
        auditFacade.flushInTransaction();
    }
}
