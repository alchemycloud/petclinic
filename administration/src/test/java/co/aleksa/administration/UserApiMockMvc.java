package co.aleksa.administration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.aleksa.administration.api.dto.userapi.AdminsResponse;
import co.aleksa.administration.api.dto.userapi.CreateUserOnTenantRequest;
import co.aleksa.administration.api.dto.userapi.CreateUserRequest;
import co.aleksa.administration.api.dto.userapi.CreateUserResponse;
import co.aleksa.administration.api.dto.userapi.GetActiveUserResponse;
import co.aleksa.administration.api.dto.userapi.NonAdminsResponse;
import co.aleksa.administration.api.dto.userapi.ReadUserResponse;
import co.aleksa.administration.api.dto.userapi.RemoveUserFromTenantRequest;
import co.aleksa.administration.api.dto.userapi.UpdateUserRequest;
import co.aleksa.administration.api.dto.userapi.UpdateUserResponse;
import co.aleksa.administration.api.dto.userapi.UserActivationDTO;
import co.aleksa.administration.model.id.UserId;
import co.aleksa.administration.util.AppThreadLocals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
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

    private List<NonAdminsResponse> nonAdmins(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/users/non-admins")
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

    private List<AdminsResponse> admins(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/users/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<AdminsResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, AdminsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<AdminsResponse> admins() throws Exception {
        return admins(getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void activateUser(UserActivationDTO request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/users/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void activateUser(UserActivationDTO request) throws Exception {
        activateUser(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private GetActiveUserResponse getActiveUser(UserId userId, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/users/active/" + userId + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<GetActiveUserResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), GetActiveUserResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default GetActiveUserResponse getActiveUser(UserId userId) throws Exception {
        return getActiveUser(userId, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void createUserOnTenant(CreateUserOnTenantRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/users/create-user-on-tenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void createUserOnTenant(CreateUserOnTenantRequest request) throws Exception {
        createUserOnTenant(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void removeUserFromTenant(RemoveUserFromTenantRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/users/remove-user-from-tenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void removeUserFromTenant(RemoveUserFromTenantRequest request) throws Exception {
        removeUserFromTenant(request, getAbstractApiTest().getCurrentUserAccessToken());
    }
}
