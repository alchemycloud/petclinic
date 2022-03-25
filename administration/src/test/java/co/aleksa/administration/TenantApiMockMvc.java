package co.aleksa.administration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.aleksa.administration.api.dto.tenantapi.CreateTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsResponse;
import co.aleksa.administration.api.dto.tenantapi.UpdateTenantRequest;
import co.aleksa.administration.util.AppThreadLocals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

public interface TenantApiMockMvc {

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    AbstractApiTest getAbstractApiTest();

    byte[] convertObjectToJsonBytes(Object object) throws IOException;

    private CreateTenantResponse createTenant(CreateTenantRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/tenants/create-tenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<CreateTenantResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), CreateTenantResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default CreateTenantResponse createTenant(CreateTenantRequest request) throws Exception {
        return createTenant(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void updateTenant(UpdateTenantRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/tenants/update-tenant")
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

    default void updateTenant(UpdateTenantRequest request) throws Exception {
        updateTenant(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private ReadTenantResponse readTenant(String identifier, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/tenants/read-tenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("identifier", identifier);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<ReadTenantResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), ReadTenantResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default ReadTenantResponse readTenant(String identifier) throws Exception {
        return readTenant(identifier, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<SearchTenantsResponse> searchTenants(Optional<String> name, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/tenants/search-tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);
        if (name.isPresent()) builder = builder.param("name", name.get());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<SearchTenantsResponse>> restResponse =
                RestResponse.fromMvcResult(result, getObjectMapper(), List.class, SearchTenantsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<SearchTenantsResponse> searchTenants(Optional<String> name) throws Exception {
        return searchTenants(name, getAbstractApiTest().getCurrentUserAccessToken());
    }
}
