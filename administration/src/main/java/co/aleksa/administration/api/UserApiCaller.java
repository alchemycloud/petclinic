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

    public List<NonAdminsResponse> nonAdmins() {
        LOG.trace("nonAdmins");

        final List<NonAdminsResponse> result = userApiTransactionCaller.nonAdmins();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<AdminsResponse> admins() {
        LOG.trace("admins");

        final List<AdminsResponse> result = userApiTransactionCaller.admins();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void activateUser(UserActivationDTO dto) {
        LOG.trace("activateUser {}", dto.getId());

        userApiTransactionCaller.activateUser(dto);
        auditFacade.flushAfterTransaction();
    }

    public GetActiveUserResponse getActiveUser(GetActiveUserRequest dto) {
        LOG.trace("getActiveUser {}", dto.getUserId());

        final GetActiveUserResponse result = userApiTransactionCaller.getActiveUser(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void createUserOnTenant(CreateUserOnTenantRequest dto) {
        LOG.trace("createUserOnTenant");

        userApiTransactionCaller.createUserOnTenant(dto);
        auditFacade.flushAfterTransaction();
    }

    public void removeUserFromTenant(RemoveUserFromTenantRequest dto) {
        LOG.trace("removeUserFromTenant {}", dto.getId());

        userApiTransactionCaller.removeUserFromTenant(dto);
        auditFacade.flushAfterTransaction();
    }
}
