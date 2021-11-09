package co.drytools.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.drytools.backend.api.dto.userapi.AdminUsersResponse;
import co.drytools.backend.api.dto.userapi.CreateUserRequest;
import co.drytools.backend.api.dto.userapi.CreateUserResponse;
import co.drytools.backend.api.dto.userapi.NonAdminsResponse;
import co.drytools.backend.api.dto.userapi.ReadUserResponse;
import co.drytools.backend.api.dto.userapi.UpdateUserRequest;
import co.drytools.backend.api.dto.userapi.UpdateUserResponse;
import co.drytools.backend.api.dto.userapi.UserActivationSimpleDTO;
import co.drytools.backend.api.dto.userapi.UserResponseDTO;
import co.drytools.backend.api.dto.userapi.UsersResponse;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.util.AppThreadLocals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

public interface UserApiMockMvc {

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    AbstractApiTest getAbstractApiTest();

    byte[] convertObjectToJsonBytes(Object object) throws IOException;

    private ReadUserResponse readUser(UserId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/users/read-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("id", id.getValue().toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<ReadUserResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), ReadUserResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default ReadUserResponse readUser(UserId id) throws Exception {
        return readUser(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private CreateUserResponse createUser(CreateUserRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/users/create-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<CreateUserResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), CreateUserResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default CreateUserResponse createUser(CreateUserRequest request) throws Exception {
        return createUser(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private UpdateUserResponse updateUser(UpdateUserRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/users/update-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<UpdateUserResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), UpdateUserResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default UpdateUserResponse updateUser(UpdateUserRequest request) throws Exception {
        return updateUser(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void deleteUser(UserId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                delete("/api" + "/users/delete-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("id", id.getValue().toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void deleteUser(UserId id) throws Exception {
        deleteUser(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<UsersResponse> users(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/users/allUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<UsersResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, UsersResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<UsersResponse> users() throws Exception {
        return users(getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<NonAdminsResponse> nonAdmins(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/users/nonAdmins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<NonAdminsResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, NonAdminsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<NonAdminsResponse> nonAdmins() throws Exception {
        return nonAdmins(getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<AdminUsersResponse> adminUsers(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/users/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<AdminUsersResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, AdminUsersResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<AdminUsersResponse> adminUsers() throws Exception {
        return adminUsers(getAbstractApiTest().getCurrentUserAccessToken());
    }

    private UserResponseDTO setUserActiveStatusSimple(UserActivationSimpleDTO request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/users/active/simple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<UserResponseDTO> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), UserResponseDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default UserResponseDTO setUserActiveStatusSimple(UserActivationSimpleDTO request) throws Exception {
        return setUserActiveStatusSimple(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private UserResponseDTO getActiveUser(
            UserId id, String email, String firstName, String lastName, ZonedDateTime birthdate, Boolean active, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("id", id.getValue().toString());
        builder = builder.param("email", email);
        builder = builder.param("firstName", firstName);
        builder = builder.param("lastName", lastName);
        builder = builder.param("birthdate", birthdate.toString());
        builder = builder.param("active", active.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<UserResponseDTO> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), UserResponseDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default UserResponseDTO getActiveUser(UserId id, String email, String firstName, String lastName, ZonedDateTime birthdate, Boolean active)
            throws Exception {
        return getActiveUser(id, email, firstName, lastName, birthdate, active, getAbstractApiTest().getCurrentUserAccessToken());
    }
}
