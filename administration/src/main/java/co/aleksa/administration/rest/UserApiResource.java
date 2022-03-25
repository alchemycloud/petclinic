package co.aleksa.administration.rest;

import co.aleksa.administration.api.UserApiCaller;
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
import co.aleksa.administration.model.id.UserId;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(UserApiResource.class);

    @Inject private UserApiCaller userApiCaller;

    @GetMapping(value = "/read-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ReadUserResponse> readUser(@RequestParam("id") Long id) {
        LOG.debug("GET /users/read-user");

        final ReadUserRequest request = new ReadUserRequest(new UserId(id));
        final ReadUserResponse response = userApiCaller.readUser(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        LOG.debug("POST /users/create-user {}", request);

        final CreateUserResponse response = userApiCaller.createUser(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/update-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UpdateUserResponse> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        LOG.debug("PUT /users/update-user {}", request);

        final UpdateUserResponse response = userApiCaller.updateUser(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/delete-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@RequestParam("id") Long id) {
        LOG.debug("DELETE /users/delete-user");

        final DeleteUserRequest request = new DeleteUserRequest(new UserId(id));
        userApiCaller.deleteUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/non-admins", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NonAdminsResponse>> nonAdmins() {
        LOG.debug("GET /users/non-admins");

        final List<NonAdminsResponse> response = userApiCaller.nonAdmins();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/admins", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AdminsResponse>> admins() {
        LOG.debug("GET /users/admins");

        final List<AdminsResponse> response = userApiCaller.admins();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> activateUser(@Valid @RequestBody UserActivationDTO request) {
        LOG.debug("PUT /users/activate {}", request);

        userApiCaller.activateUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/active/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GetActiveUserResponse> getActiveUser(@PathVariable Long userId) {
        LOG.debug("GET /users/active/{}", userId);

        final GetActiveUserRequest request = new GetActiveUserRequest(new UserId(userId));
        final GetActiveUserResponse response = userApiCaller.getActiveUser(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create-user-on-tenant", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> createUserOnTenant(@Valid @RequestBody CreateUserOnTenantRequest request) {
        LOG.debug("POST /users/create-user-on-tenant {}", request);

        userApiCaller.createUserOnTenant(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/remove-user-from-tenant", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> removeUserFromTenant(@Valid @RequestBody RemoveUserFromTenantRequest request) {
        LOG.debug("PUT /users/remove-user-from-tenant {}", request);

        userApiCaller.removeUserFromTenant(request);
        return ResponseEntity.ok().build();
    }
}
