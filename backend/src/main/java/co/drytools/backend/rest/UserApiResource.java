package co.drytools.backend.rest;

import co.drytools.backend.api.UserApiCaller;
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
import co.drytools.backend.model.id.UserId;
import java.time.ZonedDateTime;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UsersResponse>> users() {
        LOG.debug("GET /users/allUsers");

        final List<UsersResponse> response = userApiCaller.users();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/nonAdmins", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VET')")
    public ResponseEntity<List<NonAdminsResponse>> nonAdmins() {
        LOG.debug("GET /users/nonAdmins");

        final List<NonAdminsResponse> response = userApiCaller.nonAdmins();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/admins", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AdminUsersResponse>> adminUsers() {
        LOG.debug("GET /users/admins");

        final List<AdminUsersResponse> response = userApiCaller.adminUsers();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/active/simple", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDTO> setUserActiveStatusSimple(@Valid @RequestBody UserActivationSimpleDTO request) {
        LOG.debug("PUT /users/active/simple {}", request);

        final UserResponseDTO response = userApiCaller.setUserActiveStatusSimple(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDTO> getActiveUser(
            @RequestParam("id") Long id,
            @RequestParam("email") String email,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime birthdate,
            @RequestParam("active") Boolean active) {
        LOG.debug("GET /users/user");

        final UserDTO request = new UserDTO(new UserId(id), email, firstName, lastName, birthdate, active);
        final UserResponseDTO response = userApiCaller.getActiveUser(request);
        return ResponseEntity.ok().body(response);
    }
}
